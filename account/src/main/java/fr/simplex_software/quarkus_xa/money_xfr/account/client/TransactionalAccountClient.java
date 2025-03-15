package fr.simplex_software.quarkus_xa.money_xfr.account.client;

import fr.simplex_software.quarkus_xa.money_xfr.account.exception.*;
import jakarta.enterprise.context.*;
import jakarta.inject.*;
import jakarta.transaction.*;
import org.eclipse.microprofile.rest.client.inject.*;
import org.slf4j.*;

import java.math.*;

@ApplicationScoped
public class TransactionalAccountClient
{
  private static final Logger logger = LoggerFactory.getLogger(TransactionalAccountClient.class);
  @Inject
  @RestClient
  AccountRestClient accountClient;
  @Inject
  TransactionManager transactionManager;

  @Transactional
  public void debit(String accountId, BigDecimal amount) throws TransactionFailedException
  {
    try
    {
      accountClient.debit(accountId, amount);
      txSync(accountId);
    }
    catch (Exception e)
    {
      try
      {
        transactionManager.getTransaction().setRollbackOnly();
      }
      catch (SystemException se)
      {
        logger.error("### Could not mark transaction for rollback", se);
      }
      throw new TransactionFailedException("### Failed to process debit", e);
    }
  }

  /**
   * Performs a credit operation within a transaction context
   */
  @Transactional
  public void credit(String accountId, BigDecimal amount) throws TransactionFailedException
  {
    try
    {
      accountClient.credit(accountId, amount);
      txSync(accountId);
    }
    catch (Exception e)
    {
      try
      {
        transactionManager.getTransaction().setRollbackOnly();
      }
      catch (SystemException se)
      {
        logger.error("### Could not mark transaction for rollback", se);
      }
      throw new TransactionFailedException("### Failed to process credit", e);
    }
  }

  private void txSync(String accountId) throws SystemException, RollbackException
  {
    Transaction tx = transactionManager.getTransaction();
    tx.registerSynchronization(new Synchronization()
    {
      @Override
      public void beforeCompletion()
      {
        logger.info(">>> About to complete debit transaction for account {}", accountId);
      }

      @Override
      public void afterCompletion(int status)
      {
        if (status == Status.STATUS_COMMITTED)
        {
          logger.info(">>> Debit transaction completed successfully for account {}", accountId);
        }
        else
        {
          logger.warn("### Debit transaction failed for account {}", accountId);
        }
      }
    });
  }
}
