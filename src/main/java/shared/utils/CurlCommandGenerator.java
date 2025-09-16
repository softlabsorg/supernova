package shared.utils;

import io.restassured.http.Header;
import io.restassured.internal.RequestSpecificationImpl;
import io.restassured.specification.RequestSpecification;

public class CurlCommandGenerator {

    public static String generateCurlCommand(RequestSpecification requestSpec) {
        RequestSpecificationImpl reqImpl = (RequestSpecificationImpl) requestSpec;

        String method = reqImpl.getMethod();
        if (method == null) {
            method = "GET";
        }

        StringBuilder curlCmd = new StringBuilder("curl -X ")
                .append(method)
                .append(" '")
                .append(reqImpl.getURI())
                .append("'");

        if (reqImpl.getHeaders() != null) {
            for (Header header : reqImpl.getHeaders().asList()) {
                curlCmd.append(" -H '")
                        .append(header.getName())
                        .append(": ")
                        .append(header.getValue())
                        .append("'");
            }
        }

        if (reqImpl.getFormParams() != null && !reqImpl.getFormParams().isEmpty()) {
            reqImpl.getFormParams().forEach((key, value) ->
                    curlCmd.append(" --form '").append(key).append("=").append(value).append("'")
            );
        }

        if (reqImpl.getMultiPartParams() != null && !reqImpl.getMultiPartParams().isEmpty()) {
            reqImpl.getMultiPartParams().forEach(multiPart -> {
                if (multiPart.getContent() != null) {
                    curlCmd.append(" --form '")
                            .append(multiPart.getControlName())
                            .append("=")
                            .append(multiPart.getContent())
                            .append("'");
                }
            });
        }

        if (reqImpl.getQueryParams() != null && !reqImpl.getQueryParams().isEmpty()) {
            reqImpl.getQueryParams().forEach((key, value) ->
                    curlCmd.append(" --data-urlencode '").append(key).append("=").append(value).append("'")
            );
        }

        if (reqImpl.getBody() != null) {
            String bodyString = String.valueOf(reqImpl.getBody());
            curlCmd.append(" --data '").append(bodyString).append("'");
        }

        return curlCmd.toString();
    }

    public static String generateCurlCommandWithAuth(RequestSpecification requestSpec, String authToken) {
        String curlCommand = generateCurlCommand(requestSpec);

        if (authToken != null && !curlCommand.contains("Authorization:")) {
            int insertPos = curlCommand.indexOf("'", curlCommand.indexOf("curl -X")) + 1;
            curlCommand = curlCommand.substring(0, insertPos) +
                    " -H 'Authorization: Bearer " + authToken + "'" +
                    curlCommand.substring(insertPos);
        }

        return curlCommand;
    }

}