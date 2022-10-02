package org.apache.coyote.support;

import java.util.Arrays;

public enum HttpVersion {

    HTTP1("HTTP/1.0"),
    HTTP1_1("HTTP/1.1"),
    HTTP2("HTTP/2.0"),
    HTTP3("HTTP/3.0"),
    ;

    private final String value;

    HttpVersion( String value) {
        this.value = value;
    }

    public static HttpVersion of(String value) {
       return Arrays.stream(values())
                .filter(it -> it.value.equals(value))
                .findAny()
                .orElseThrow(HttpException::ofBadRequest);
    }

    public String getValue() {
        return value;
    }
}
