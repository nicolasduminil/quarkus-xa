package fr.simplex_software.quarkus_xa.money_xfr.account.client;

import fr.simplex_software.quarkus_xa.money_xfr.account.domain.*;
import jakarta.transaction.*;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.eclipse.microprofile.rest.client.inject.*;

import java.math.*;

@RegisterRestClient(configKey = "account_client")
@Path("/accounts")
public interface AccountRestClient
{
  @POST
  @Path("/{accountId}/debit")
  @Transactional
  Response debit(@PathParam("accountId") String accountId, BigDecimal amount);

  @POST
  @Path("/{accountId}/credit")
  @Transactional
  Response credit(@PathParam("accountId") String accountId, BigDecimal amount);

  @GET
  Response getAccounts();

  @GET
  @Path("/{accountId}")
  Response getAccount(@PathParam("accountId") String accountId);

  @POST
  public Response createAccount(Account account);
}
