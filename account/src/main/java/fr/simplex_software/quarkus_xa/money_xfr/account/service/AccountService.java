package fr.simplex_software.quarkus_xa.money_xfr.account.service;

import fr.simplex_software.quarkus_xa.money_xfr.account.domain.*;
import fr.simplex_software.quarkus_xa.money_xfr.account.repository.*;
import jakarta.inject.*;

import java.math.*;

public class AccountService
{
  @Inject
  AccountRepository accountRepository;

  public Account getAccount(String accountId)
  {
    return accountRepository.getAccount(accountId);
  }

  public void createAccount(Account account)
  {
    accountRepository.createAccount(account);
  }

  public void updateAccount(Account account)
  {
    accountRepository.updateAccount(account);
  }

  public void deleteAccount(String accountId)
  {
    accountRepository.deleteAccount(accountId);
  }

  public void debit (Account account, BigDecimal amount)
  {
    accountRepository.getAccount(account.getAccountId())
      .debit(amount);
  }

  public void credit(Account account, BigDecimal amount)
  {
    accountRepository.getAccount(account.getAccountId())
      .credit(amount);
  }
}
