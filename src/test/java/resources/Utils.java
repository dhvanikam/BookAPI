package resources;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Properties;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.RequestSpecification;

public class Utils {
	public static RequestSpecification requestSpec;

	public RequestSpecification requestSpecification() throws IOException {
		if (requestSpec == null) {
			PrintStream stream = new PrintStream(new FileOutputStream("logging.txt"));

			requestSpec = new RequestSpecBuilder().addFilter(RequestLoggingFilter.logRequestTo(stream))
					.addFilter(ResponseLoggingFilter.logResponseTo(stream)).setContentType(ContentType.JSON)
					.setBaseUri(getGlobalValue("baseurl")).addHeader("Authorization",
							"Bearer 3a84ad46d1de26eb7fb8da7a227615d0696bafe8353384568d8acbb12d53aea0")
					.build();

			return requestSpec;
		}
		return requestSpec;
	}

	public void clearQueryParam(RequestSpecification requestSpecification) {

		FilterableRequestSpecification filterableRequestSpecification = (FilterableRequestSpecification) requestSpecification;

		filterableRequestSpecification.removeQueryParam("codes");
	}

	public void clearPathParam(RequestSpecification requestSpecification, String param) {

		FilterableRequestSpecification filterableRequestSpecification = (FilterableRequestSpecification) requestSpecification;

		filterableRequestSpecification.removePathParam(param);
	}

	public static String getGlobalValue(String key) throws IOException {
		Properties prop = new Properties();
		FileInputStream fis = new FileInputStream("./src/test/java/resources/global.properties");
		prop.load(fis);
		return prop.getProperty(key);
	}

	public String getJsonPath(Response response, String key) {
		String resp = response.asString();
		JsonPath js = new JsonPath(resp);
		return js.get(key).toString();
	}
}
