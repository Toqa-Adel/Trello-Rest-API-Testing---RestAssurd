import io.restassured.RestAssured;

public class GetMemberData {

    public static void main(String[] args) {

        RestAssured
                .given()
                .baseUri(sharedData.baseUrl +"/1/members/me")
                .queryParam("token", sharedData.token)
                .queryParam("key", sharedData.key)
                .when()
                .get().prettyPrint();

    }

}
