package lib.mc.player;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

public class UserData {

    private HashMap<String, String> values = new HashMap<>();
    private JSONArray data;

    public UserData(JSONObject userDataJSON) {
        if (!userDataJSON.has("properties")) return;
        JSONArray props = userDataJSON.getJSONArray("properties");
        for (Object _ : props) {
            JSONObject propertySet = (JSONObject) _;
            String value = propertySet.getString("value"),
                    key = propertySet.getString("name");
            values.put(key, value);
        }
        data = props;
    }

    public JSONArray getData() {
        return data;
    }

    public HashMap<String, String> getValues() {
        return values;
    }
}
