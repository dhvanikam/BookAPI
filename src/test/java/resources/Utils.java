package resources;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
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
							"Bearer c2da758fddc1c8fc785937d185d91139ae37bacc6623125ddc9ff8d1cc6d6857")
					.build();

			return requestSpec;
		}
		return requestSpec;
	}

	public void clearQueryParam(RequestSpecification requestSpecification) {

		FilterableRequestSpecification filterableRequestSpecification = (FilterableRequestSpecification) requestSpecification;

		Map<String, String> params = new HashMap<String, String>();
		params = filterableRequestSpecification.getQueryParams();

		if (!params.isEmpty()) {
			for (Map.Entry<String, String> e : params.entrySet()) {
				filterableRequestSpecification.removeQueryParam(e.getKey());
			}
		}
	}

	public void clearPathParam(RequestSpecification requestSpecification) {

		FilterableRequestSpecification filterableRequestSpecification = (FilterableRequestSpecification) requestSpecification;

		Map<String, String> params = new HashMap<String, String>();
		params = filterableRequestSpecification.getPathParams();

		if (!params.isEmpty()) {
			for (Map.Entry<String, String> e : params.entrySet()) {
				filterableRequestSpecification.removePathParam(e.getKey());
			}
		}

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
