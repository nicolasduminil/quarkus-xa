package fr.simplex_software.quarkus_xa.money_xfr.account.client;

import jakarta.enterprise.context.*;
import jakarta.transaction.*;
import jakarta.ws.rs.*;
import org.eclipse.microprofile.rest.client.inject.*;

import java.math.*;

@ApplicationScoped
@RegisterRestClient
public interface AccountRestClient
{
  @POST
  @Path("/accounts/{accountId}/debit")
  @Transactional
  void debit(@PathParam("accountId") String accountId, BigDecimal amount);

  @POST
  @Path("/accounts/{accountId}/credit")
  @Transactional
  void credit(@PathParam("accountId") String accountId, BigDecimal amount);
}
