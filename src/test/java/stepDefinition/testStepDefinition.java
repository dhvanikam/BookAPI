package stepDefinition;

import org.testng.Assert;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import resources.TestDataBuild;
import resources.Utils;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import java.io.FileNotFoundException;
import java.io.IOException;

public class testStepDefinition extends Utils {
	ResponseSpecification responseSpec;
	RequestSpecification request;
	Response response;

	TestDataBuild data = new TestDataBuild();

	@Given("user add payload to request with {string} and {string}")
	public void user_add_payload_to_request_with_and(String bookid, String name) throws IOException {
		request = given().spec(requestSpecification()).body(data.submitOrder(bookid, name)).log().all();
	}

	@When("user calls submitOrder api with post http request")
	public void user_calls_submit_order_api_with_post_http_request() {
		responseSpec = new ResponseSpecBuilder().expectStatusCode(201).expectContentType(ContentType.JSON).build();
		response = request.when().post("/orders").then().spec(responseSpec).extract().response();

	}

	@Then("api call is succesful with status code {int}")
	public void api_call_is_succesful_with_status_code(Integer int1) {
		Assert.assertEquals(response.getStatusCode(), int1);
	}

	@Then("Status in response is OK")
	public void status_in_response_is_ok() {
		Assert.assertEquals(response.getStatusLine(), "HTTP/1.1 201 Created");

	}

}
