package fr.simplex_software.quarkus_xa.money_xfr.transfer.tests;

import fr.simplex_software.quarkus_xa.money_xfr.account.client.*;
import fr.simplex_software.quarkus_xa.money_xfr.account.domain.*;
import fr.simplex_software.quarkus_xa.money_xfr.transfer.domain.*;
import io.quarkus.test.junit.*;
import io.restassured.http.*;
import jakarta.inject.*;
import jakarta.ws.rs.core.*;
import org.apache.http.*;
import org.eclipse.microprofile.rest.client.inject.*;
import org.junit.jupiter.api.*;

import java.math.*;
import java.util.*;

import static io.restassured.RestAssured.*;
import static org.assertj.core.api.Assertions.*;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MoneyTransferIT
{
  @Inject
  TransactionalAccountClient transactionalAccountClient;
  @Inject
  @RestClient
  AccountRestClient accountRestClient;

  @Test
  @Order(10)
  public void testCreateAccount()
  {
    assertThat(accountRestClient.createAccount(new Account("ACC123", "nicolas",
      new BigDecimal(1000), "EUR")).getStatus()).isEqualTo(HttpStatus.SC_OK);
    assertThat(accountRestClient.createAccount(new Account("ACC321", "nicolas",
      new BigDecimal(1000), "EUR")).getStatus()).isEqualTo(HttpStatus.SC_OK);
  }

  @Test
  @Order(20)
  public void testGetAccounts()
  {
    Response response = accountRestClient.getAccounts();
    assertThat(response).isNotNull();
    assertThat(response.getStatus()).isEqualTo(HttpStatus.SC_OK);
    List<Account> accounts = response.readEntity(new GenericType<>()
    {
    });
    assertThat(accounts).isNotNull();
    assertThat(accounts).hasSize(2);
  }

  @Test
  @Order(30)
  public void testGetAccount()
  {
    Response response = accountRestClient.getAccount("ACC123");
    assertThat(response).isNotNull();
    assertThat(response.getStatus()).isEqualTo(HttpStatus.SC_OK);
    Account account = response.readEntity(Account.class);
    assertThat(account).isNotNull();
    assertThat(account.getAccountId()).isEqualTo("ACC123");
    assertThat(account.getBalance()).isEqualTo(new BigDecimal(1000));
    assertThat(account.getCurrency()).isEqualTo("EUR");
    assertThat(account.getCustomerName()).isEqualTo("nicolas");
  }

  @Test
  @Order(40)
  public void testTransferMoney()
  {
    given().contentType(ContentType.JSON)
      .body(new TransferRequest("ACC123", "ACC321",
        new BigDecimal(150.24), "EUR"))
      .when().post("/xfers").then().statusCode(HttpStatus.SC_OK);
  }

  @Test

  @Order(50)
  public void testGetAccountAfterTransfer()
  {
    Account account = accountRestClient.getAccount("ACC123")
      .readEntity(Account.class);
    assertThat(account.getBalance()).isEqualTo(new BigDecimal(849.76));
    account = accountRestClient.getAccount("ACC321")
      .readEntity(Account.class);
    assertThat(account.getBalance()).isEqualTo(new BigDecimal(1150.24));
  }

  @Test
  @Order(60)
  public void testTransferMoneyFails()
  {
    try
    {
      given()
        .contentType(ContentType.JSON)
        .body(new TransferRequest("ACC123", "ACC321",
          new BigDecimal(1100), "EUR"))
        .when()
        .post("/xfers")
        .then()
        .statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
    }
    catch (Exception ex)
    {}
  }

  @Test
  @Order(70)
  public void testGetAccountAfterFailedTransfer()
  {
    testGetAccountAfterTransfer();
  }
}
