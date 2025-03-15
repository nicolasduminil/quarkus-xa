package fr.simplex_software.quarkus_xa.money_xfr.transfer.domain;

public enum TransferStatus
{
  PENDING,      // Initial state when transaction is created
  COMPLETED,    // Successfully completed transaction
  FAILED,       // Transfer failed during processing
  CANCELLED,    // Transfer cancelled by user or system
  REJECTED,     // Transfer rejected due to validation/business rules
  PROCESSING    // Transfer is being processed
}
