package restUtils;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.QueryableRequestSpecification;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.SpecificationQuerier;
import reporting.ExtentReportManager;
import utils.RestReqFilter;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class RestUtils {

    public static RestReqFilter restReqFilter;

    public static RequestSpecification getRequestSpecificationForPost(String endPoint, Object requestPayload, Map<String, String> headers) {
        // Validate endpoint
        if (endPoint == null) {
            throw new IllegalArgumentException("Endpoint cannot be null");
        }

        // Ensure immutability of headers
        headers = (headers != null) ? Map.copyOf(headers) : Map.of();

        // Build RequestSpecification
        RequestSpecification requestSpec = given()
                .baseUri(endPoint)
                .headers(headers)
                .contentType(ContentType.JSON);

        // Add request payload if not null
        if (requestPayload != null) {
            requestSpec.body(requestPayload);
        }

        return requestSpec;
    }

    public static RequestSpecification getRequestSpecificationForGet(String endPoint,
                                                                     Map<String, String> pathParams,
                                                                     Map<String, String> queryParams,
                                                                     Map<String, String> headers) {
        try {
            if (endPoint == null) {
                throw new IllegalArgumentException("Endpoint cannot be null");
            }
            // Ensure immutability of maps
            pathParams = pathParams != null ? Map.copyOf(pathParams) : Map.of();
            queryParams = queryParams != null ? Map.copyOf(queryParams) : Map.of();
            headers = headers != null ? Map.copyOf(headers) : Map.of();

            return given()
                    .baseUri(endPoint)
                    .headers(headers)
                    .contentType(ContentType.JSON)
                    .pathParams(pathParams)
                    .queryParams(queryParams);
        } catch (Exception e) {
            // Handle any exceptions
            e.printStackTrace();
            return null;
        }
    }

    public static void printRequestLogInReport(RequestSpecification requestSpecification) {
        QueryableRequestSpecification queryableRequestSpecification = SpecificationQuerier.query(requestSpecification);
        ExtentReportManager.logInfoDetails("Endpoint is " + queryableRequestSpecification.getBaseUri());
        ExtentReportManager.logInfoDetails("Method is " + queryableRequestSpecification.getMethod());
        ExtentReportManager.logInfoDetails("Headers are ");
        ExtentReportManager.logHeaders(queryableRequestSpecification.getHeaders().asList());
        ExtentReportManager.logInfoDetails("Request body is ");
        ExtentReportManager.logJson(queryableRequestSpecification.getBody());
    }

    public static void printResponseLogInReport(Response response) {
        ExtentReportManager.logInfoDetails("Response status is " + response.getStatusCode());
        ExtentReportManager.logInfoDetails("Response Headers are ");
        ExtentReportManager.logHeaders(response.getHeaders().asList());
        ExtentReportManager.logInfoDetails("Response body is ");
        ExtentReportManager.logJson(response.getBody().prettyPrint());
    }

    public static void printCurlLogInReport(String capturedCurl) {
        ExtentReportManager.logInfoDetails("Captured Curl is ");
        ExtentReportManager.logCurl(capturedCurl);
    }

    public static Response performPost(String endPoint, String requestPayload, Map<String,String>headers) {

        restReqFilter = new RestReqFilter();

        RequestSpecification requestSpecification = getRequestSpecificationForPost(endPoint, requestPayload, headers);
        Response response = requestSpecification.filter(restReqFilter).post();
        printRequestLogInReport(requestSpecification);
        printResponseLogInReport(response);
        printCurlLogInReport(restReqFilter.getCapturedCurl());
        return response;
    }

    public static Response performPost(String endPoint, Map<String, Object> requestPayload, Map<String,String>headers) {

        restReqFilter = new RestReqFilter();

        RequestSpecification requestSpecification = getRequestSpecificationForPost(endPoint, requestPayload, headers);
        Response response = requestSpecification.filter(restReqFilter).post();

        printRequestLogInReport(requestSpecification);
        printResponseLogInReport(response);
        printCurlLogInReport(restReqFilter.getCapturedCurl());

        return response;
    }

    public static Response performPost(String endPoint, Object requestPayloadAsPojo, Map<String,String>headers) {

        restReqFilter = new RestReqFilter();

        RequestSpecification requestSpecification = getRequestSpecificationForPost(endPoint, requestPayloadAsPojo, headers);
        Response response = requestSpecification.filter(restReqFilter).post();

        printRequestLogInReport(requestSpecification);
        printResponseLogInReport(response);
        printCurlLogInReport(restReqFilter.getCapturedCurl());

        return response;
    }

    public static Response performPostWithRest(String endPoint, String requestPayload, Map<String, String> headers) {

        restReqFilter = new RestReqFilter();

        return given().log().all()
            .baseUri(endPoint)
            .headers(headers).filter(restReqFilter)
            .body(requestPayload)
            .post()
            .then().log().all().extract().response();
    }

    public static Response performPostWithoutLogs(String endPoint, Object requestPayloadAsPojo, Map<String,String>headers) {
        RequestSpecification requestSpecification = getRequestSpecificationForPost(endPoint, requestPayloadAsPojo, headers);
        Response response = requestSpecification.post();
        return response;
    }

    public static Response performGet(String endPoint, Map<String,String>pathParams, Map<String,String>queryParams, Map<String,String>headers) {

        restReqFilter = new RestReqFilter();

        RequestSpecification requestSpecification = getRequestSpecificationForGet(endPoint, pathParams,queryParams, headers);
        Response response = requestSpecification.filter(restReqFilter).get();

        printRequestLogInReport(requestSpecification);
        printResponseLogInReport(response);
        printCurlLogInReport(restReqFilter.getCapturedCurl());

        return response;
    }

    public static Response performDelete(String endPoint, Map<String,String>pathParams, Map<String,String>queryParams, Map<String,String>headers) {

        restReqFilter = new RestReqFilter();

        RequestSpecification requestSpecification = getRequestSpecificationForGet(endPoint, pathParams,queryParams, headers);
        Response response = requestSpecification.filter(restReqFilter).delete();

        printRequestLogInReport(requestSpecification);
        printResponseLogInReport(response);
        printCurlLogInReport(restReqFilter.getCapturedCurl());

        return response;
    }

    public static Response performPut(String endPoint, Object requestPayloadAsPojo, Map<String,String>headers) {

        restReqFilter = new RestReqFilter();

        RequestSpecification requestSpecification = getRequestSpecificationForPost(endPoint, requestPayloadAsPojo, headers);
        Response response = requestSpecification.filter(restReqFilter).put();

        printRequestLogInReport(requestSpecification);
        printResponseLogInReport(response);
        printCurlLogInReport(restReqFilter.getCapturedCurl());

        return response;
    }
}
