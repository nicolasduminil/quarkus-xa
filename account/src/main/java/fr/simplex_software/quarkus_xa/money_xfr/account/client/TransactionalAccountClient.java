package fr.simplex_software.quarkus_xa.money_xfr.account.client;

import jakarta.enterprise.context.*;
import jakarta.inject.*;
import jakarta.transaction.*;
import org.eclipse.microprofile.rest.client.inject.*;

import java.math.*;

@ApplicationScoped
public class TransactionalAccountClient
{
  @Inject
  @RestClient
  AccountRestClient accountClient;
  @Inject
  TransactionManager transactionManager;

  @Transactional
  public void debit(String accountId, BigDecimal amount) {
    try {
      // Call the actual REST client
      accountClient.debit(accountId, amount);

      // Add transaction synchronization for cleanup/recovery if needed
      Transaction tx = transactionManager.getTransaction();
      tx.registerSynchronization(new Synchronization() {
        @Override
        public void beforeCompletion() {
          // Logic before transaction commits
          logger.info("About to complete debit transaction for account {}", accountId);
        }

        @Override
        public void afterCompletion(int status) {
          // Logic after transaction completes
          if (status == Status.STATUS_COMMITTED) {
            logger.info("Debit transaction completed successfully for account {}", accountId);
          } else {
            logger.warn("Debit transaction failed for account {}", accountId);
          }
        }
      });
    } catch (Exception e) {
      logger.error("Error during debit operation", e);
      throw new TransactionFailedException("Failed to process debit", e);
    }
  }

  /**
   * Performs a credit operation within a transaction context
   */
  @Transactional
  public void credit(String accountId, BigDecimal amount) {
    try {
      accountClient.credit(accountId, amount);
      // Similar transaction handling as debit
    } catch (Exception e) {
      logger.error("Error during credit operation", e);
      throw new TransactionFailedException("Failed to process credit", e);
    }
  }
}
