package api;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.authentication.AuthenticationScheme;
import com.jayway.restassured.filter.log.LogDetail;
import org.testng.annotations.BeforeSuite;
import com.jayway.restassured.builder.RequestSpecBuilder;

import static com.jayway.restassured.http.ContentType.JSON;

public class AbstractAPITest {
    @BeforeSuite
    public void setUpSuite() throws Exception {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        AuthenticationScheme authenticationScheme = RestAssured.oauth("",
                "",
                "",
                "");
        RestAssured.requestSpecification = new RequestSpecBuilder()
                .log(LogDetail.ALL)
                .setContentType(JSON)
                .setAuth(authenticationScheme)
                .setBaseUri("https://api.twitter.com/1.1/")
                .build();
    }
}
