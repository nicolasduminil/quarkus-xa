package fr.simplex_software.quarkus_xa.money_xfr.account.api;

import fr.simplex_software.quarkus_xa.money_xfr.account.service.*;
import jakarta.enterprise.context.*;
import jakarta.inject.*;
import jakarta.transaction.*;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.eclipse.microprofile.openapi.annotations.*;
import org.eclipse.microprofile.openapi.annotations.responses.*;
import org.eclipse.microprofile.openapi.annotations.tags.*;

import java.math.*;

@ApplicationScoped
@Path("/acounts")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Account Management", description = "Operations for managing bank accounts")
public class AccountResource
{
  @Inject
  AccountService accountService;

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

  @Operation(
    summary = "Debit account",
    description = "Withdraws money from the specified account"
  )
  @APIResponse(
    responseCode = "200",
    description = "Amount debited successfully"
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
  @Path("/accounts/{accountId}/debit")
  @Transactional
  public Response debit(@PathParam("accountId") String accountId, BigDecimal amount)
  {
    if (amount.compareTo(BigDecimal.ZERO) <= 0)
      throw new WebApplicationException("### AccountResource.debit(): Invalid debit amount %f"
        .formatted(amount), 400);
    accountService.debit(accountId, amount);
    return Response.ok().build();
  }

  @Operation(
    summary = "Credit account",
    description = "Deposits money into the specified account"
  )
  @APIResponse(
    responseCode = "200",
    description = "Amount credited successfully"
  )
  @APIResponse(
    responseCode = "400",
    description = "Invalid credit amount"
  )
  @APIResponse(
    responseCode = "404",
    description = "Account not found"
  )  @POST
  @Path("/accounts/{accountId}/credit")
  @Transactional
  public Response credit(@PathParam("accountId") String accountId, BigDecimal amount)
  {
    if (amount.compareTo(BigDecimal.ZERO) <= 0)
      throw new WebApplicationException("### AccountResource.debit(): Invalid credit amount %f"
        .formatted(amount), 400);
    accountService.credit(accountId, amount);
    return Response.ok().build();
  }
}
