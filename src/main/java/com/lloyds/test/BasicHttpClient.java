package com.lloyds.test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.EntityBuilder;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Response;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.System.currentTimeMillis;
import static java.time.LocalDateTime.now;
import static java.util.Optional.ofNullable;
import static org.apache.http.entity.ContentType.APPLICATION_JSON;
import static org.apache.http.entity.ContentType.TEXT_PLAIN;

public class BasicHttpClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(BasicHttpClient.class);

    private static final String FILES_FIELD = "files";
    private static final String BOUNDARY_FIELD = "boundary";
    private static final String MULTIPART_FORM_DATA = "multipart/form-data";
    private static final String APPLICATION_FORM_URL_ENCODED = "application/x-www-form-urlencoded";
    private static final String CONTENT_TYPE = "Content-Type";
    private final ObjectMapper objectMapper;

    private Object COOKIE_JSESSIONID_VALUE;
    private CloseableHttpClient httpclient;

    public BasicHttpClient(ObjectMapper objectMapper) {
        this.httpclient = createHttpClient();
        this.objectMapper = objectMapper;
    }

    private CloseableHttpClient createHttpClient() {
        LOGGER.info("###Creating SSL Enabled Http Client for both http/https/TLS connections");

//        SSLContext sslContext = new SSLContextBuilder()
//                .loadTrustMaterial(null, (certificate, authType) -> true).build();

        CookieStore cookieStore = new BasicCookieStore();

        return HttpClients.custom()
//                .setSSLContext(sslContext)
                .setSSLHostnameVerifier(new NoopHostnameVerifier())
                .setDefaultCookieStore(cookieStore)
                .build();
    }

    CloseableHttpResponse execute(String httpUrl,
                                  String methodName,
                                  Map<String, Object> headers,
                                  Map<String, Object> queryParams,
                                  Object body) throws Exception {

        String reqBodyAsString = createRequestBody(body);
        String newHttpUrl = createUrlAndQueryParams(httpUrl, queryParams);

        RequestBuilder requestBuilder = createRequestBuilder(newHttpUrl, methodName, headers, reqBodyAsString);
        handleHeaders(requestBuilder, headers);
        addCookieToHeader(requestBuilder);

        return httpclient.execute(requestBuilder.build());
    }

    private static String createUrlAndQueryParams(String httpUrl, Map<String, Object> queryParams) throws IOException {
        String newHttpUrl = httpUrl;
        if (!queryParams.isEmpty()) {
            String qualifiedQueryParams = createQualifiedQueryParams(queryParams);
            newHttpUrl += "?" + qualifiedQueryParams;
        }
        return newHttpUrl;
    }

    private static String createQualifiedQueryParams(Map<String, Object> queryParamsMap) throws IOException {
        queryParamsMap = ofNullable(queryParamsMap).orElse(new HashMap<>());
        List<NameValuePair> nameValueList = new ArrayList<>();
        for(String key : queryParamsMap.keySet()) {
            nameValueList.add(new BasicNameValuePair(key, queryParamsMap.get(key).toString()));
        }
        HttpEntity httpEntity = new UrlEncodedFormEntity(nameValueList);
        return EntityUtils.toString(httpEntity);
    }

    private static RequestBuilder handleHeaders(RequestBuilder requestBuilder, Map<String, Object> headers) {
        if (headers != null) {
            for (Object key : ((Map) headers).keySet()) {
                if(CONTENT_TYPE.equalsIgnoreCase((String)key) && MULTIPART_FORM_DATA.equals(headers.get(key))){
                    continue;
                }
                if (requestBuilder.getFirstHeader((String) key) != null) {
                    requestBuilder.removeHeaders((String) key);
                }
                requestBuilder.addHeader((String) key, (String) headers.get(key));
                LOGGER.info("Overridden the header key:{}, with value:{}", key, headers.get(key));
            }
        }
        return requestBuilder;
    }

    private String createRequestBody(Object bodyContent) {
        if (null == bodyContent) {
            return null;
        }

        JsonNode bodyJsonNode;
        try {
            final String bodyContentAsString = objectMapper.writeValueAsString(bodyContent);
            bodyJsonNode = objectMapper.readValue(bodyContentAsString, JsonNode.class);

            if (bodyJsonNode.isValueNode()) {
                return bodyJsonNode.asText();
            }

            if (bodyJsonNode.size() == 0) {
                return null;
            }

            return bodyJsonNode.toString();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private static RequestBuilder createDefaultRequestBuilder(String httpUrl, String methodName, String reqBodyAsString) {
        RequestBuilder requestBuilder = RequestBuilder
                .create(methodName)
                .setUri(httpUrl);

        if (reqBodyAsString != null) {
            HttpEntity httpEntity = EntityBuilder.create()
                    .setContentType(APPLICATION_JSON)
                    .setText(reqBodyAsString)
                    .build();
            requestBuilder.setEntity(httpEntity);
        }
        return requestBuilder;
    }

    /**
     * This is how framework makes the KeyValue pair when "application/x-www-form-urlencoded" headers
     * is passed in the request.  In case you want to build or prepare the requests differently,
     * you can override this method via @UseHttpClient(YourCustomHttpClient.class).
     *
     * @param httpUrl
     * @param methodName
     * @param reqBodyAsString
     * @return
     * @throws IOException
     */
    private RequestBuilder createFormUrlEncodedRequestBuilder(String httpUrl, String methodName, String reqBodyAsString) throws IOException {
        RequestBuilder requestBuilder = RequestBuilder
                .create(methodName)
                .setUri(httpUrl);
        if (reqBodyAsString != null) {
            Map<String, Object> reqBodyMap = readObjectAsMap(reqBodyAsString);
            List<NameValuePair> reqBody = new ArrayList<>();
             for(String key : reqBodyMap.keySet()) {
                 reqBody.add(new BasicNameValuePair(key, reqBodyMap.get(key).toString()));
             }
             HttpEntity httpEntity = new UrlEncodedFormEntity(reqBody);
             requestBuilder.setEntity(httpEntity);
            requestBuilder.setHeader(CONTENT_TYPE, APPLICATION_FORM_URL_ENCODED);
        }
        return requestBuilder;
    }

    private Map<String, Object> readObjectAsMap(Object jsonContent) {
        try {
            return objectMapper.readValue(jsonContent.toString(), HashMap.class);
        } catch (IOException exx) {
            LOGGER.error("Exception occurred during parse to HashMap - " + exx);
            throw new RuntimeException(exx);
        }
    }

    /**
     * This is the http request builder for file uploads, using Apache Http Client. In case you want to build
     * or prepare the requests differently, you can override this method.
     *
     * Note-
     * With file uploads you can send more headers too from the testcase to the server, except "Content-Type" because
     * this is reserved for "multipart/form-data" which the client sends to server during the file uploads. You can
     * also send more request-params and "boundary" from the test cases if needed. The boundary defaults to an unique
     * string of local-date-time-stamp if not provided in the request.
     *
     * You can override this method via @UseHttpClient(YourCustomHttpClient.class)
     *
     * @param httpUrl
     * @param methodName
     * @param reqBodyAsString
     * @return
     * @throws IOException
     */
    private RequestBuilder createFileUploadRequestBuilder(String httpUrl, String methodName, String reqBodyAsString) throws IOException {

        Map<String, Object> fileFieldNameValueMap = objectMapper.readValue(reqBodyAsString, HashMap.class);
        List<String> fileFieldsList = (List<String>) fileFieldNameValueMap.get(FILES_FIELD);

        MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();

        /*
	 * Allow fileFieldsList to be null.
	 * fileFieldsList can be null if multipart/form-data is sent without any files
	 * Refer Issue #168 - Raised and fixed by santhoshTpixler
	 */
        if(fileFieldsList != null) {
        	buildAllFilesToUpload(fileFieldsList, multipartEntityBuilder);
        }

        buildOtherRequestParams(fileFieldNameValueMap, multipartEntityBuilder);
        buildMultiPartBoundary(fileFieldNameValueMap, multipartEntityBuilder);

        return createUploadRequestBuilder(httpUrl, methodName, multipartEntityBuilder);
    }

    private RequestBuilder createUploadRequestBuilder(String httpUrl, String methodName, MultipartEntityBuilder multipartEntityBuilder) {

        RequestBuilder uploadRequestBuilder = RequestBuilder
                .create(methodName)
                .setUri(httpUrl);

        HttpEntity reqEntity = multipartEntityBuilder.build();

        uploadRequestBuilder.setEntity(reqEntity);

        return uploadRequestBuilder;
    }


    private static void buildMultiPartBoundary(Map<String, Object> fileFieldNameValueMap, MultipartEntityBuilder multipartEntityBuilder) {
        String boundary = (String) fileFieldNameValueMap.get(BOUNDARY_FIELD);
        multipartEntityBuilder.setBoundary(boundary != null ? boundary : currentTimeMillis() + now().toString());
    }

    private static void buildAllFilesToUpload(List<String> fileFiledsList, MultipartEntityBuilder multipartEntityBuilder) {
        fileFiledsList.forEach(fileField -> {
            String[] fieldNameValue = fileField.split(":");
            String fieldName = fieldNameValue[0];
            String fileNameWithPath = fieldNameValue[1].trim();

            FileBody fileBody = new FileBody(new File(getAbsPath(fileNameWithPath)));
            multipartEntityBuilder.addPart(fieldName, fileBody);
        });
    }

    private static void buildOtherRequestParams(Map<String, Object> fileFieldNameValueMap, MultipartEntityBuilder multipartEntityBuilder) {
        for (Map.Entry<String, Object> entry : fileFieldNameValueMap.entrySet()) {
            if (entry.getKey().equals(FILES_FIELD) || entry.getKey().equals(BOUNDARY_FIELD)) {
                continue;
            }
            multipartEntityBuilder.addPart(entry.getKey(), new StringBody((String) entry.getValue(), TEXT_PLAIN));
        }
    }

    private static String getAbsPath(String filePath) {
        if (new File(filePath).exists()) {
            return filePath;
        }

        ClassLoader classLoader = BasicHttpClient.class.getClassLoader();
        URL resource = classLoader.getResource(filePath);
        if (resource == null) {
            throw new RuntimeException("Could not get details of file or folder - `" + filePath + "`, does this exist?");
        }
        return resource.getPath();
    }

    /**
     * This method handles the http session to be maintained between the calls.
     * In case the session is not needed or to be handled differently, then this
     * method can be overridden to do nothing or to roll your own feature.
     *
     * @param serverResponse
     * @param headerKey
     */
    private void handleHttpSession(Response serverResponse, String headerKey) {
        /** ---------------
         * Session handled
         * ----------------
         */
        if ("Set-Cookie".equals(headerKey)) {
            COOKIE_JSESSIONID_VALUE = serverResponse.getMetadata().get(headerKey);
        }
    }

    private void addCookieToHeader(RequestBuilder uploadRequestBuilder) {
        // -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-
        // Setting cookies:
        // Highly discouraged to use sessions, but in case of any server dependent upon session,
        // then it is taken care here.
        // -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-
        if (COOKIE_JSESSIONID_VALUE != null) {
            uploadRequestBuilder.addHeader("Cookie", (String) COOKIE_JSESSIONID_VALUE);
        }
    }

    private RequestBuilder createRequestBuilder(String httpUrl, String methodName, Map<String, Object> headers, String reqBodyAsString) throws IOException {
        String contentType = headers != null? (String) headers.get(CONTENT_TYPE) :null;

        if(contentType!=null){
            if(contentType.equals(MULTIPART_FORM_DATA)){
                return createFileUploadRequestBuilder(httpUrl, methodName, reqBodyAsString);
            } else if(contentType.equals(APPLICATION_FORM_URL_ENCODED)) {
                return createFormUrlEncodedRequestBuilder(httpUrl, methodName, reqBodyAsString);
            }
            // -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
            // Extention- Any other header types to be specially handled here
            // -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
            // else if(contentType.equals("OTHER-TYPES")){
            //    Handling logic
            // }
        }
        return createDefaultRequestBuilder(httpUrl, methodName, reqBodyAsString);
    }
}
