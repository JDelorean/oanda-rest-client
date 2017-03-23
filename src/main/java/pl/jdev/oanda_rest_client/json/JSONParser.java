package pl.jdev.oanda_rest_client.json;

import org.json.JSONObject;
import org.json.JSONTokener;

public class JSONParser {

	JSONTokener tokener;

	public Class<? extends Mappable> parse(String msg, Class<? extends Mappable> mappableClass) {
		tokener = new JSONTokener(msg);

		JSONObject root = new JSONObject(tokener);

		return mappableClass;
	}

}
