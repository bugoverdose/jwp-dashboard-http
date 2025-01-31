package org.apache.coyote.request;

import java.util.HashMap;
import java.util.Map;

public class Parameters {

    private static final String PARAMETER_DELIMITER = "&";
    private static final String KEY_VALUE_DELIMITER = "=";
    private static final int DELIMITER_NOT_FOUND_INDEX = -1;

    private final Map<String, String> value;

    public Parameters(Map<String, String> value) {
        this.value = value;
    }

    public static Parameters of(String line) {
        Map<String, String> parameters = new HashMap<>();
        for (String parameter : line.split(PARAMETER_DELIMITER)) {
            addValidParameter(parameters, parameter);
        }
        return new Parameters(parameters);
    }

    private static void addValidParameter(Map<String, String> parameters, String parameter) {
        final var delimiterIndex = parameter.indexOf(KEY_VALUE_DELIMITER);
        if (delimiterIndex == DELIMITER_NOT_FOUND_INDEX) {
            return;
        }
        final var key = parameter.substring(0, delimiterIndex);
        final var value = parameter.substring(delimiterIndex + 1);
        parameters.put(key, value);
    }

    public String get(String parameterKey) {
        if (value.containsKey(parameterKey)) {
            return value.get(parameterKey);
        }
        return "";
    }
}
