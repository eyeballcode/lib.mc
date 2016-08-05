package lib.mc.http;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

public class URLEncodedFormPayload {

    private HashMap<String, String> params = new HashMap<>();

    public void put(String key, String value) {
        params.put(key, value);
    }

    public String get(String key) {
        return params.get(key);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        int i = 0;
        for (String key : params.keySet()) {
            String val = params.get(key);
            builder.append(key).append("=");
            try {
                builder.append(URLEncoder.encode(val, "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                return null;
            }
            if (i++ != params.size())
                builder.append("&");
        }
        return builder.toString();
    }
}
