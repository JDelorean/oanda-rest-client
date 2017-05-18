package pl.jdev.oanda_rest_client.json;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.jdev.oanda_rest_client.domain.Currency;
import pl.jdev.oanda_rest_client.domain.instrument.CurrencyPair;
import pl.jdev.oanda_rest_client.domain.instrument.InstrumentType;
import pl.jdev.oanda_rest_client.domain.order.Side;
import pl.jdev.oanda_rest_client.domain.order.Status;
import pl.jdev.oanda_rest_client.domain.order.Type;
import pl.jdev.oanda_rest_client.json.annotation.JSONArrayReference;
import pl.jdev.oanda_rest_client.json.annotation.JSONObjectReference;

public class JSONParser {

	private final static Logger logger = LoggerFactory.getLogger(JSONParser.class);

	Map<String, Object> jsonObjectElements;
	Map<String, Object> parsedElements;

	public Map<String, Object> parse(String jsonMsg, Class<? extends Mappable> mappableClass) {

		logger.debug(String.format("Parsing JSON:\n%s", jsonMsg));
		logger.debug(String.format("Mapping to [%s].", mappableClass));

		JSONObject root = new JSONObject(jsonMsg);
		JSONObject jsonObject = null;
		JSONArray jsonArray = null;
		String jsonObjectRef = null;

		if (mappableClass.isAnnotationPresent(JSONArrayReference.class)
				&& root.has(mappableClass.getAnnotation(JSONArrayReference.class).value())) {
			jsonObjectRef = mappableClass.getAnnotation(JSONArrayReference.class).value();
			jsonArray = root.getJSONArray(jsonObjectRef);
			// System.out.println("array: " + jsonArray.toString());
			int jsonArrayLenght = jsonArray.length();
			for (int i = 0; i < jsonArrayLenght; i++) {
				logger.debug(String.format("Parsing array element [%s] of [%s].", i + 1, jsonArrayLenght));
				// System.out.println(jsonArray.getJSONObject(i).toString());
				// System.out.println(mappableClass.getAnnotation(JSONArrayReference.class).classReference());
				parse(jsonArray.getJSONObject(i).toString(), mappableClass.getAnnotation(JSONArrayReference.class).classReference());
			}
		} else {
			if (mappableClass.isAnnotationPresent(JSONObjectReference.class)
					&& root.has(mappableClass.getAnnotation(JSONObjectReference.class).value())) {
				jsonObjectRef = mappableClass.getAnnotation(JSONObjectReference.class).value();
				jsonObject = root.getJSONObject(jsonObjectRef);
				// System.out.println("object: " + jsonObjectRef);
				// System.out.println("jsonObject Map: " + jsonObject.toMap());
				jsonObjectElements = jsonObject.toMap();
			} else {
				jsonObject = root;
				// System.out.println("no array & no object: " + jsonObjectRef);
				// System.out.println("jsonObject Map: " + jsonObject.toMap());
				jsonObjectElements = jsonObject.toMap();
			}

			parsedElements = new HashMap<String, Object>();

			List<Field> mappableFieldsList = Arrays.asList(mappableClass.getDeclaredFields());
			System.out.println(mappableFieldsList);

			String mappableFields = "";
			for (Field field : mappableFieldsList) {
				mappableFields += "\n" + field.getName();
			}
			logger.debug(String.format("Mappable fields:%s", mappableFields));

			for (Field field : mappableFieldsList) {
				boolean isAnnotated = false;
				isAnnotated = isAnnotated(field);
				if (isAnnotated) {
					process(field);
				}
			}

			if (parsedElements.size() > 0) {
				logger.debug(String.format("Parsed parameters [%s].", parsedElements));
			} else {
				logger.warn(String.format("No parameters parsed."));
			}

		}

		return parsedElements;
	}

	private boolean isArrayElement(Field field) {
		String fieldName = field.getName();
		if (field.getAnnotation(JSONArrayReference.class) == null) {
			return false;
		} else {
			// logger.debug(String.format("[%s] is an array element.",
			// fieldName));
			return true;
		}
	}

	private boolean isObject(Field field) {
		String fieldName = field.getName();
		if (field.getAnnotation(JSONObjectReference.class) == null) {
			return false;
		} else {
			// logger.debug(String.format("[%s] is an object.", fieldName));
			return true;
		}
	}

	/**
	 * Checks whether a Field is annotated and annotation is not empty.
	 */
	private boolean isAnnotated(Field field) {
		String fieldName = field.getName();
		String fieldJSONRef = null;

		logger.debug(String.format("Evaluating field [%s]...", fieldName));
		if (isObject(field)) {
			fieldJSONRef = field.getAnnotation(JSONObjectReference.class).value();
		} else if (isArrayElement(field)) {
			fieldJSONRef = field.getAnnotation(JSONArrayReference.class).value();
		} else {
			logger.debug(String.format("... omitted - no annotation present."));
			return false;
		}

		if (!fieldJSONRef.equals("")) {
			logger.debug(String.format("... parsable - processing."));
			return true;
		} else {
			logger.warn(String.format("... omitted - annotation is empty."));
			return false;
		}
	}

	private boolean hasCorrespondingJSONElement(Field field, String jsonMsg) {
		String annotation = null;
		if (isObject(field)) {
			annotation = field.getAnnotation(JSONObjectReference.class).value();
		} else if (isArrayElement(field)) {
			annotation = field.getAnnotation(JSONArrayReference.class).value();
		}
		return new JSONObject(jsonMsg).has(annotation);
	}

	private void process(Field field) {
		String fieldName = field.getName();
		String fieldJSONRef = field.getAnnotation(JSONObjectReference.class).value();
		// Verify JSON message's object contains element equal to
		// field's JSON reference.
		if (jsonObjectElements.containsKey(fieldJSONRef)) {
			Object jsonElementValue = null;
			String value = (String) jsonObjectElements.get(fieldJSONRef);
			Class<?> fieldType = field.getType();
			if (fieldType.isPrimitive()) {
				logger.warn(String.format("Field [%s] omitted - unhandled primitive type.", fieldName));
				return;
			} else if (fieldType.isAssignableFrom(Mappable.class)) {
				logger.debug(String.format("Field [%s] type implements 'Mappable' interface.", fieldName));
				// not implemented
			} else {
				logger.debug(String.format("Assiging value [%s] to field [%s]...", value, fieldName));
				switch (fieldType.getSimpleName()) {
				case "String":
					jsonElementValue = value;
					break;
				case "Float":
					jsonElementValue = Float.valueOf(value);
					break;
				case "Integer":
					jsonElementValue = Integer.valueOf(value);
					break;
				case "Boolean":
					jsonElementValue = Boolean.valueOf(value);
					break;
				case "Currency":
					jsonElementValue = new Currency(value);
					break;
				case "InstrumentType":
					jsonElementValue = InstrumentType.valueOf(value);
					break;
				case "Instrument":
					jsonElementValue = new CurrencyPair(value);
					break;
				case "Type":
					jsonElementValue = Type.valueOf(value);
					break;
				case "Status":
					jsonElementValue = Status.valueOf(value);
					break;
				case "Side":
					jsonElementValue = Side.valueOf(value);
					break;
				default:
					jsonElementValue = null;
					logger.warn(String.format("[%s] type is undefined in JSONParser.", fieldType.getName()));
					break;
				}
			}
			logger.debug(String.format("Field [%s] = type [%s] value [%s].", fieldName, jsonElementValue.getClass().getSimpleName(),
					jsonElementValue));
			parsedElements.put(fieldJSONRef, jsonElementValue);
		}
	}

	public int extractLastTransactionId(String msg) {
		int lastTransactionId = new JSONObject(msg).getInt("lastTransactionID");
		logger.debug(String.format("Last transaction ID [%s].", lastTransactionId));
		return lastTransactionId;
	}

}
