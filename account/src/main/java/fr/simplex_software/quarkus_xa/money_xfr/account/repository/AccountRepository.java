package fr.simplex_software.quarkus_xa.money_xfr.account.repository;

import fr.simplex_software.quarkus_xa.money_xfr.account.domain.*;
import jakarta.enterprise.context.*;

import java.util.*;

@ApplicationScoped
public class AccountRepository
{
  private static Map<String, Account> accounts = new HashMap<>();

  public void createAccount(Account account)
  {
    accounts.put(account.getAccountId(), account);
  }

  public Account getAccount(String accountId)
  {
    if (accounts.get(accountId) == null)
    {
      System.out.println ("### AccountRepository.getAccount(): Account %s not found".formatted(accountId));
      accounts.values().forEach(acc -> System.out.println ("acc: %s".formatted(acc.getAccountId())));
      throw new RuntimeException(
        "### AccountRepository.getAccount(): Account %s not found"
          .formatted(accountId));
    }
    return accounts.get(accountId);
  }

  public void updateAccount(Account account)
  {
    if (accounts.get(account.getAccountId()) == null)
      throw new RuntimeException(
        "### AccountRepository.updateAccount(): Account %s not found"
        .formatted(account.getAccountId()));
    accounts.put(account.getAccountId(), account);
  }

  public void deleteAccount(String accountId)
  {
    if (accounts.get(accountId) == null)
      throw new RuntimeException(
        "### AccountRepository.deleteAccount(): Account %s not found"
        .formatted(accountId));
    accounts.remove(accountId);
  }

  public List<Account> getAccounts()
  {
    return new ArrayList<>(accounts.values());
  }
}
