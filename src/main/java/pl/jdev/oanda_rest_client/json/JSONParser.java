package pl.jdev.oanda_rest_client.json;

import java.lang.reflect.Field;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

public class JSONParser {

	JSONTokener tokener;

	public Class<? extends Mappable> parse(String msg, Class<? extends Mappable> mappableClass) {
		
		Field[] mappableFields = mappableClass.getDeclaredFields();
		for(Field field: mappableFields){
			System.out.println(field.getName());
		}
		
		tokener = new JSONTokener(msg);

		JSONObject root = new JSONObject(tokener);
		
		JSONArray el = root.getJSONArray("NAV");
		System.out.println(el.get(0).getClass());
		
		JSONObject lastTransId = root.getJSONObject("lastTransactionID");
		System.out.println(lastTransId);

		return mappableClass;
	}

}
