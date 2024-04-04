package stepDefinition;

import org.testng.Assert;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import resources.APIResources;
import resources.TestDataBuild;
import resources.Utils;

import static io.restassured.RestAssured.*;

import java.io.IOException;

public class testStepDefinition extends Utils {
	ResponseSpecification responseSpec;
	RequestSpecification request;
	Response response;

	TestDataBuild data = new TestDataBuild();

	@Given("user add payload to request with {string} and {string}")
	public void user_add_payload_to_request_with_and(String bookid, String name) throws IOException {

		request = given().spec(requestSpecification()).body(data.submitOrder(bookid, name)).log().all();
		clearPathParam(request, "orderId");
	}

	@When("user calls {string} with {string} http request")
	public void user_calls_with_http_request(String resource, String httpMethod) {

		APIResources resourceAPI = APIResources.valueOf(resource);
		System.out.println(resourceAPI + httpMethod);

		if (httpMethod.equalsIgnoreCase("POST"))
			response = request.when().post(resourceAPI.getResource());
		else if (httpMethod.equalsIgnoreCase("GET"))
			response = request.when().get(resourceAPI.getResource());
		else if (httpMethod.equalsIgnoreCase("UPDATE"))
			response = request.when().patch(resourceAPI.getResource());
		else if (httpMethod.equalsIgnoreCase("DELETE"))
			response = request.when().delete(resourceAPI.getResource());

	}

	@Then("api call is succesful with status code {int}")
	public void api_call_is_succesful_with_status_code(Integer int1) {
		responseSpec = new ResponseSpecBuilder().expectStatusCode(int1).expectContentType(ContentType.JSON).build();
		Assert.assertEquals(response.getStatusCode(), int1);
	}

	@Then("{string} in response is {string}")
	public void in_response_is(String keyvalue, String expectedResult) {
		String actualResult = getJsonPath(response, keyvalue);
		Assert.assertEquals(actualResult, expectedResult);

	}

	@Then("verify order_Id created maps to {string} using {string}")
	public void verify_order_id_created_maps_to_using(String expectedResultName, String resource) throws IOException {
		String orderId = getJsonPath(response, "orderId");
		System.out.println(orderId);
		request = given().spec(requestSpecification().pathParam("orderId", orderId));

		user_calls_with_http_request(resource, "GET");
		String actualResultName = getJsonPath(response, "customerName");
		Assert.assertEquals(actualResultName, expectedResultName);

	}

}
