package fr.simplex_software.quarkus_xa.money_xfr.account.exception;

public class TransactionFailedException extends Exception
{
  public TransactionFailedException(String message)
  {
    super(message);
  }

  public TransactionFailedException(String message, Throwable cause)
  {
    super(message, cause);
  }
}
