package fr.simplex_software.quarkus_xa.money_xfr.transfer.service;

import fr.simplex_software.quarkus_xa.money_xfr.account.client.*;
import fr.simplex_software.quarkus_xa.money_xfr.account.domain.*;
import fr.simplex_software.quarkus_xa.money_xfr.account.exception.*;
import fr.simplex_software.quarkus_xa.money_xfr.account.service.*;
import fr.simplex_software.quarkus_xa.money_xfr.common.*;
import fr.simplex_software.quarkus_xa.money_xfr.transfer.domain.*;
import fr.simplex_software.quarkus_xa.money_xfr.transfer.repository.*;
import jakarta.enterprise.context.*;
import jakarta.inject.*;
import jakarta.transaction.*;
import jakarta.ws.rs.core.*;
import org.eclipse.microprofile.rest.client.inject.*;

import java.time.*;
import java.util.*;

@ApplicationScoped
public class TransferService
{
  @Inject
  TransferRepository transferRepository;
  @Inject
  TransactionalAccountClient accountClient;

  @Transactional
  public Transfer processTransfer(TransferRequest transferRequest) throws TransactionFailedException
  {
    Transfer transfer = new Transfer(Common.getRandomId(), transferRequest.getSourceAccountId(),
      transferRequest.getDestinationAccountId(), transferRequest.getAmount(),
      transferRequest.getCurrency(), TransferStatus.PROCESSING, LocalDateTime.now());
    /*accountClient.credit(transferRequest.getDestinationAccountId(),
      transferRequest.getAmount());
    accountClient.debit(transferRequest.getSourceAccountId(),
      transferRequest.getAmount());*/
    accountClient.transferMoney(transferRequest.getSourceAccountId(),
      transferRequest.getDestinationAccountId(), transferRequest.getAmount());
    return transfer;
  }
}
