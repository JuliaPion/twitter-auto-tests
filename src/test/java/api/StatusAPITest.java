package api;


import org.testng.annotations.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import static com.jayway.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;

public class StatusAPITest extends AbstractAPITest {
    private final String TIMELINE_PATH = "statuses/home_timeline.json";
    private final String UDATE_PATH = "statuses/update.json";
    private final String DELETE_PATH = "statuses/destroy/{tweetId}.json";

    private static final String LOCAL_MONTH_AND_DAY = LocalDate.now().format(DateTimeFormatter.ofPattern("MMM dd"));

    private String createTweet(String tweetText) {
        return given()
                .queryParam("status", tweetText)
                .expect()
                .statusCode(200)
                .when()
                .post(UDATE_PATH)
                .thenReturn()
                .path("id_str");
    }

    /**
     * Verify that "created_at", "retweet_count", "text" fields have right values
     */
    @Test
    public void testTweetCreation() throws Exception {
        String tweetText = getUniqueText();
        createTweet(tweetText);
        when()
                .get(TIMELINE_PATH)
                .then()
                .statusCode(200)
                .body("created_at[0]", containsString(LOCAL_MONTH_AND_DAY))
                .body("retweet_count[0]", equalTo(0))
                .body("text[0]", equalTo(tweetText));
    }

    @Test
    public void testTweetRemove() {
        String tweetText = getUniqueText();
        String tweetId = createTweet(tweetText);
        given()
                .post(DELETE_PATH, tweetId)
                .then()
                .statusCode(200);
    }

    @Test
    public void testDuplicationTweets() {
        String tweetText = getUniqueText();
        createTweet(tweetText);
        given()
                .queryParam("status", tweetText)
                .when()
                .post(UDATE_PATH)
                .then()
                .statusCode(403);
    }

    private String getUniqueText() {
        return UUID.randomUUID().toString();
    }
}
