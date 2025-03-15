package fr.simplex_software.quarkus_xa.money_xfr.transfer.api;

import fr.simplex_software.quarkus_xa.money_xfr.transfer.domain.*;
import fr.simplex_software.quarkus_xa.money_xfr.transfer.service.*;
import jakarta.inject.*;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

@Path("/xfers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TransferResource
{
  @Inject
  TransferService transferService;

  @POST
  public Response createTransfer(TransferRequest request) {
    Transfer transfer = transferService.processTransaction(request);
    return Response.ok(TransferResponse.fromTransfer(transfer)).build();
  }
}
