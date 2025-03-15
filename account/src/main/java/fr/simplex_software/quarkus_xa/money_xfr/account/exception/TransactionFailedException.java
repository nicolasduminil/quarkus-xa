package fr.simplex_software.quarkus_xa.money_xfr.account.exception;

public class TransactionFailedException extends Exception
{
  public TransactionFailedException()
  {
  }

  public TransactionFailedException(String message, Throwable cause)
  {
    super(message, cause);
  }
}
