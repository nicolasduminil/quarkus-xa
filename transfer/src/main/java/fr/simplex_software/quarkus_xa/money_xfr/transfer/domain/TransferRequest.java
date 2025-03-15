package fr.simplex_software.quarkus_xa.money_xfr.transfer.domain;

import java.math.*;

public class TransferRequest
{
  private String sourceAccountId;
  private String destinationAccountId;
  private BigDecimal amount;
  private String currency;

  public TransferRequest()
  {
  }

  public TransferRequest(String sourceAccountId, String destinationAccountId, BigDecimal amount, String currency)
  {
    this.sourceAccountId = sourceAccountId;
    this.destinationAccountId = destinationAccountId;
    this.amount = amount;
    this.currency = currency;
  }

  public String getSourceAccountId()
  {
    return sourceAccountId;
  }

  public void setSourceAccountId(String sourceAccountId)
  {
    this.sourceAccountId = sourceAccountId;
  }

  public String getDestinationAccountId()
  {
    return destinationAccountId;
  }

  public void setDestinationAccountId(String destinationAccountId)
  {
    this.destinationAccountId = destinationAccountId;
  }

  public BigDecimal getAmount()
  {
    return amount;
  }

  public void setAmount(BigDecimal amount)
  {
    this.amount = amount;
  }

  public String getCurrency()
  {
    return currency;
  }

  public void setCurrency(String currency)
  {
    this.currency = currency;
  }
}
