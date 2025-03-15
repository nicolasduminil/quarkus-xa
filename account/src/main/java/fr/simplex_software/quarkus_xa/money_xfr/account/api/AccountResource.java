package fr.simplex_software.quarkus_xa.money_xfr.account.api;

import jakarta.enterprise.context.*;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

@ApplicationScoped
@Path("/acounts")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AccountResource
{
  @GET
  @Path("/{accountId}")
  public Response getAccount(@PathParam("accountId") String accountId)
  {
    return Response.ok().build();
  }

  @POST
  public Response createAccount()
  {
    return Response.ok().build();
  }

  @PUT
  @Path("/{accountId}")
  public Response updateAccount(@PathParam("accountId") String accountId)
  {
    return Response.ok().build();
  }

  @DELETE
  @Path("/{accountId}")
  public Response deleteAccount(@PathParam("accountId") String accountId)
  {
    return Response.ok().build();
  }
}
