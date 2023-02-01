import io.restassured.RestAssured;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.hamcrest.Matchers;

public class Trello_APIs {
    public static String OID;
    public static String bID;
    public static String lID;

    public static void main(String[] args) {

// 01 - Create New Organization

        Response response = RestAssured.given().baseUri(sharedData.baseUrl +"/1/organizations")
                .queryParam("displayName", "RestAssured ORG")
                .queryParam("token", sharedData.token)
                .queryParam("key", sharedData.key)
                .header("Content-Type","application/json")
                .body("{    \"name\": \"api org\"}")
                .when()
                .post();

        response.prettyPrint();
        response.then().statusCode(200);

        response.then().body("displayName", Matchers.equalTo("RestAssured ORG"));
        JsonPath path = response.jsonPath();
        OID = path.getString("id");
        System.out.println("** organization ID is: "+ OID);
//***********************//

// 02 - Create Board Inside Organization

         response = RestAssured.given().baseUri(sharedData.baseUrl + "/1/boards/")
                .queryParam("idOrganization", OID)
                .queryParam("name", "Rest Board")
                .queryParam("token", sharedData.token)
                .queryParam("key", sharedData.key)
                .header("Content-Type","application/json")
                .when()
                .post();

        response.prettyPrint();
        response.then().statusCode(200);

        response.then().body("name", Matchers.equalTo("Rest Board"));

        JsonPath path1 = response.jsonPath();
        bID = path1.getString("id");
        System.out.println("** board ID is: "+bID);

//***********************//

// 03 - Create New List
        response = RestAssured.given().baseUri(sharedData.baseUrl + "/1/lists/")
                .queryParam("idBoard", bID)
                .queryParam("name", "Rest List")
                .queryParam("token", sharedData.token)
                .queryParam("key", sharedData.key)
                .header("Content-Type","application/json")
                .when()
                .post();

        response.prettyPrint();
        response.then().statusCode(200);

        response.then().body("name", Matchers.equalTo("Rest List"));

        JsonPath path2 = response.jsonPath();
        lID = path2.getString("id");
        System.out.println("** list ID is: "+lID);

//********************************** Get Requests **************************************//
        // 04 - Get Member Organizations
         response = RestAssured.given()
                .baseUri(sharedData.baseUrl +"/1/members/"+ sharedData.memberID+"/organizations")
                .queryParam("token", sharedData.token)
                .queryParam("key", sharedData.key)
                .when()
                .get();
        System.out.println("member Organizations");
        response.prettyPrint();
        response.then().statusCode(200);

//*******************//

// 05 - Get Boards In Organization

        response = RestAssured.given()
                .baseUri(sharedData.baseUrl +"/1/organizations/"+ OID+"/boards")
                .queryParam("token", sharedData.token)
                .queryParam("key", sharedData.key)
                .when()
                .get();
        System.out.println("Boards In Organization");
        response.prettyPrint();
        response.then().statusCode(200);

//************************//

// 06 - Get All Lists On Board

        response = RestAssured
                .given()
                .baseUri(sharedData.baseUrl +"/1/boards/"+ bID+"/lists")
                .queryParam("token", sharedData.token)
                .queryParam("key", sharedData.key)
                .when()
                .get();
        System.out.println("All Lists On Board");
        response.prettyPrint();
        response.then().statusCode(200);

//********************************* Delete Request *************************//

// 07 - Archive List
        response = RestAssured
                .given().baseUri(sharedData.baseUrl + "/1/lists/"+ lID)
                .queryParam("value", "true")
                .queryParam("token", sharedData.token)
                .queryParam("key", sharedData.key)
                .header("Content-Type","application/json")
                .when()
                .put();

        response.prettyPrint();
        response.then().statusCode(200);
        System.out.println("List Archived");
//******************//

// 08 - Delete Board
        response = RestAssured
                .given()
                .baseUri(sharedData.baseUrl + "/1/boards/"+ bID)
                .queryParam("token", sharedData.token)
                .queryParam("key", sharedData.key)
                .when()
                .delete();

        response.prettyPrint();
        response.then().statusCode(200);
        System.out.println("board deleted");
//******************//

// 09 - Delete Organization

        response = RestAssured
                .given()
                .baseUri(sharedData.baseUrl + "/1/organizations/"+ OID)
                .queryParam("token", sharedData.token)
                .queryParam("key", sharedData.key)
                .when()
                .delete();

        response.prettyPrint();
        response.then().statusCode(200);
        System.out.println("organization deleted");

    }


}
