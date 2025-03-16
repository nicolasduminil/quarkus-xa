package fr.simplex_software.quarkus_xa.money_xfr.transfer.api;

import fr.simplex_software.quarkus_xa.money_xfr.account.exception.*;
import fr.simplex_software.quarkus_xa.money_xfr.account.service.*;
import fr.simplex_software.quarkus_xa.money_xfr.transfer.domain.*;
import fr.simplex_software.quarkus_xa.money_xfr.transfer.service.*;
import jakarta.inject.*;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.eclipse.microprofile.openapi.annotations.*;
import org.eclipse.microprofile.openapi.annotations.media.*;
import org.eclipse.microprofile.openapi.annotations.responses.*;
import org.eclipse.microprofile.openapi.annotations.tags.*;

@Path("/xfers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Money Transfer Management", description = "Operations for managing money transfer")
public class TransferResource
{
  @Inject
  TransferService transferService;

  @Operation(
    summary = "Create a money transfer",
    description = "Process a money transfer transaction"
  )
  @APIResponse(
    responseCode = "200",
    description = "Transaction processed successfully",
    content = @Content(
      mediaType = MediaType.APPLICATION_JSON,
      schema = @Schema(implementation = TransferResponse.class)
    )
  )
  @APIResponse(
    responseCode = "400",
    description = "Invalid debit amount"
  )
  @APIResponse(
    responseCode = "404",
    description = "Account not found"
  )
  @POST
  public Response createTransfer(TransferRequest request) throws TransactionFailedException
  {
    Transfer transfer = transferService.processTransfer(request);
    return Response.ok(TransferResponse.fromTransfer(transfer)).build();
  }
}
