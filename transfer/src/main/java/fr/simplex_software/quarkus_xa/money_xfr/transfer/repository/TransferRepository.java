package fr.simplex_software.quarkus_xa.money_xfr.transfer.repository;

import fr.simplex_software.quarkus_xa.money_xfr.transfer.domain.*;
import jakarta.enterprise.context.*;

import java.util.*;

@ApplicationScoped
public class TransferRepository
{
  private Map<String, Transfer> transfers = new HashMap<>();

  public void createTransfer(Transfer transfer)
  {
    transfers.put(transfer.getTransferId(), transfer);
  }

  public Transfer getTransfer(String transferId)
  {
    if (transfers.get(transferId) == null)
      throw new RuntimeException(
        "### TransferRepository.getTransfer(): Transfer %s not found"
          .formatted(transferId));
    return transfers.get(transferId);
  }

  public void updateTransfer(Transfer transfer)
  {
    if (transfers.get(transfer.getTransferId()) == null)
      throw new RuntimeException(
        "### TransferRepository.updateTransfer(): Transfer %s not found"
          .formatted(transfer.getTransferId()));
    transfers.put(transfer.getTransferId(), transfer);
  }

  public void deleteTransfer(String transferId)
  {
    if (transfers.get(transferId) == null)
      throw new RuntimeException(
        "### TransferRepository.deleteTransfer(): Transfer %s not found"
          .formatted(transferId));
    transfers.remove(transferId);
  }
}
