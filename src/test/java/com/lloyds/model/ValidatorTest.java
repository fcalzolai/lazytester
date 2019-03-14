package com.lloyds.model;

import io.vavr.collection.Seq;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicHttpResponse;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;

public class ValidatorTest {

    private static final int CODE_201 = 201;
    private static final int CODE_400 = 400;
    private static final String REASON = "reason";
    private static final String VALID_BODY = "valid";
    private static final String INVALID_BODY = "INVALID";
    private static final String HEADER_NAME = "headerName";
    private static final String HEADER_VALUE = "headerValue";
    private static final Header[] HEADERS = {new BasicHeader(HEADER_NAME, HEADER_VALUE)};
    private static final Step STEP = Step
            .getStepBuilder()
            .setOperation(HttpGet.METHOD_NAME)
            .setUrl("")
            .build();

    @Test
    public void valid_StatusCode() {
        Assertions assertions = getAssertions(CODE_201);
        HttpResponse response = getHttpResponse(CODE_201, VALID_BODY);
        Validator validator = new Validator(assertions, response);
        ValidatedAssertions validate = validator.validate();
        Assert.assertTrue(validate.isValid());
    }

    @Test
    public void invalid_StatusCode() {
        Assertions assertions = getAssertions(CODE_201);
        HttpResponse response = getHttpResponse(CODE_400, VALID_BODY);

        Validator validator = new Validator(assertions, response);
        ValidatedAssertions validate = validator.validate();
        Assert.assertTrue(validate.isInvalid());
        Assert.assertEquals(1, (int)validate.fold(Seq::length, Assertions::hashCode));
    }

    @Test
    public void valid_Body() {
        Assertions assertions = getAssertions(null, "description", VALID_BODY);
        HttpResponse response = getHttpResponse(CODE_201, VALID_BODY);

        Validator validator = new Validator(assertions, response);
        ValidatedAssertions validate = validator.validate();
        Assert.assertTrue(validate.isValid());
    }

    @Test
    public void valid_empty_BodyAssertion() {
        Assertions assertions = getAssertions(null);
        HttpResponse response = getHttpResponse(CODE_201, VALID_BODY);

        Validator validator = new Validator(assertions, response);
        ValidatedAssertions validate = validator.validate();
        Assert.assertTrue(validate.isValid());
    }

    @Test
    public void valid_Header() {
        Assertions assertions = getHeadersAssertions(HEADER_NAME, HEADER_VALUE);
        HttpResponse response = getHttpResponse(CODE_201, VALID_BODY, HEADERS);

        Validator validator = new Validator(assertions, response);
        ValidatedAssertions validate = validator.validate();
        Assert.assertTrue(validate.isValid());
    }

    @Test
    public void invalid_Header() {
        Assertions assertions = getHeadersAssertions(HEADER_NAME, "different header value");
        HttpResponse response = getHttpResponse(CODE_201, VALID_BODY, HEADERS);

        Validator validator = new Validator(assertions, response);
        ValidatedAssertions validate = validator.validate();
        Assert.assertTrue(validate.isInvalid());
    }

    @Test
    public void invalid_Body_value() {
        Assertions assertions = getAssertions(null, "description", VALID_BODY);
        HttpResponse response = getHttpResponse(CODE_201, INVALID_BODY);

        Validator validator = new Validator(assertions, response);
        ValidatedAssertions validate = validator.validate();
        Assert.assertTrue(validate.isInvalid());
        Assert.assertEquals(1, (int)validate.fold(Seq::length, Assertions::hashCode));
    }

    @Test
    public void valid_StatusCode_And_Body() {
        Assertions assertions = getAssertions(CODE_201, "description", VALID_BODY);

        HttpResponse response = getHttpResponse(CODE_201, VALID_BODY);

        Validator validator = new Validator(assertions, response);
        ValidatedAssertions validate = validator.validate();
        Assert.assertTrue(validate.isValid());
    }

    @Test
    public void invalid_StatusCode_And_Body() {
        Assertions assertions = getAssertions(400, "description", VALID_BODY);
        HttpResponse response = getHttpResponse(CODE_201, INVALID_BODY);

        Validator validator = new Validator(assertions, response);
        ValidatedAssertions validate = validator.validate();
        Assert.assertTrue(validate.isInvalid());
        Assert.assertEquals(2, (int)validate.fold(Seq::length, Assertions::hashCode));
    }

    @Test
    public void invalid_StatusCode_Headers_And_Body() {
        Assertions assertions = Assertions.getBuilder()
                .setStep(STEP)
                .setStatus(CODE_201)
                .putBody("description", VALID_BODY)
                .putHeader(HEADER_NAME, HEADER_VALUE)
                .build();

        Header[] headers = {new BasicHeader("InvalidHeaderName", "InvalidHeaderValue")};
        HttpResponse response = getHttpResponse(CODE_400, INVALID_BODY, headers);

        Validator validator = new Validator(assertions, response);
        ValidatedAssertions validate = validator.validate();
        Assert.assertTrue(validate.isInvalid());
        Assert.assertEquals(3, (int)validate.fold(Seq::length, Assertions::hashCode));
    }

    private Assertions getAssertions(Integer statusCode) {
        return getAssertions(statusCode, "", "");
    }

    private Assertions getAssertions(Integer statusCode, String bodyKey, String bodyVal) {
        return Assertions.getBuilder()
                .setStep(STEP)
                .setStatus(statusCode)
                .putBody(bodyKey, bodyVal)
                .build();
    }

    private Assertions getHeadersAssertions(String headerKey, String headerVal) {
        return Assertions.getBuilder()
                .setStep(STEP)
                .putHeader(headerKey, headerVal)
                .build();
    }

    private HttpResponse getHttpResponse(int statusCode, String body) {
        return getHttpResponse(statusCode, body, null);
    }

    private HttpResponse getHttpResponse(int statusCode, String body, Header[] headers) {
        HttpResponse response = new BasicHttpResponse(HttpVersion.HTTP_1_1, statusCode, REASON);
        HttpEntity entity = new InputStreamEntity(new ByteArrayInputStream(body.getBytes()));
        response.setEntity(entity);
        response.setHeaders(headers);
        return response;
    }
}