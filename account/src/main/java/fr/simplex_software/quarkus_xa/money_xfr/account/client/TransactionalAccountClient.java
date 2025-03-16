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
  public void transferMoney(String fromAccountId, String toAccountId, BigDecimal amount)
    throws TransactionFailedException {
    try {
      System.out.println ("### TransactionalAccountClient.transferMoney(): credit from " + fromAccountId + ", to " + toAccountId + ", amount " + amount);
      accountClient.credit(toAccountId, amount);
      System.out.println ("### TransactionalAccountClient.transferMoney(): done");
      txSync(toAccountId);
      accountClient.debit(fromAccountId, amount);
      System.out.println ("### TransactionalAccountClient.transferMoney(): debit from " + fromAccountId + ", to " + toAccountId + ", amount " + amount);
      txSync(fromAccountId);
    } catch (Exception e) {
      try {
        System.out.println("### TransactionalAccountClient.transferMoney(): rollback");
        transactionManager.getTransaction().setRollbackOnly();
      } catch (SystemException se) {
        logger.error("### Could not mark transaction for rollback", se);
      }
      throw new TransactionFailedException("### Failed to process transfer", e);
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
        logger.info(">>> About to complete transaction for account {}", accountId);
      }

      @Override
      public void afterCompletion(int status)
      {
        if (status == Status.STATUS_COMMITTED)
        {
          logger.info(">>> Transaction completed successfully for account {}", accountId);
        }
        else
        {
          logger.warn("### Transaction failed for account {}", accountId);
        }
      }
    });
  }
}
