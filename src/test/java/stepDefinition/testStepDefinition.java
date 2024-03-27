package stepDefinition;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import pojo.SubmitOrder;

public class testStepDefinition {
	RequestSpecification req;
	ResponseSpecification reqResponse;
	RequestSpecification res;
	Response response;

	@Given("user add payload to request")
	public void user_add_payload_to_request() {
		RestAssured.baseURI = "https://simple-books-api.glitch.me";
		SubmitOrder order = new SubmitOrder();
		order.setBookId("1");
		order.setCustomerName("John");

		req = new RequestSpecBuilder().setContentType(ContentType.JSON).setBaseUri("https://simple-books-api.glitch.me").addHeader("Authorization", "Bearer 393b887ce0af4a161e589c394b72f520fcfb9d482eb056996ff7441562469776").build();

		reqResponse = new ResponseSpecBuilder().expectStatusCode(201).expectContentType(ContentType.JSON).build();

		res = RestAssured.given().spec(req).body(order).log().all();

		response = res.when().post("/orders").then().spec(reqResponse).extract().response();
		System.out.println(response.asPrettyString());
	}

	@When("user calls submitOrder api with post http request")
	public void user_calls_submit_order_api_with_post_http_request() {
		System.out.println("When");
	}

	@Then("api call is succesful with status code {int}")
	public void api_call_is_succesful_with_status_code(Integer int1) {
		System.out.println("then");
	}

	@Then("Status in response is OK")
	public void status_in_response_is_ok() {
		System.out.println("Then");
	}

}
