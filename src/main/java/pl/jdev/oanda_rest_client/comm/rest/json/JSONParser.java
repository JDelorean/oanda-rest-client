package pl.jdev.oanda_rest_client.comm.rest.json;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import pl.jdev.oanda_rest_client.comm.rest.json.annotation.JSONArrayReference;
import pl.jdev.oanda_rest_client.comm.rest.json.annotation.JSONObjectReference;
import pl.jdev.oanda_rest_client.domain.Currency;
import pl.jdev.oanda_rest_client.domain.instrument.CurrencyPair;
import pl.jdev.oanda_rest_client.domain.instrument.InstrumentType;
import pl.jdev.oanda_rest_client.domain.order.OrderStatus;
import pl.jdev.oanda_rest_client.domain.order.Side;
import pl.jdev.oanda_rest_client.domain.order.Type;
import pl.jdev.oanda_rest_client.domain.trade.TradeStatus;

public class JSONParser {

	private final static Logger LOGGER = LogManager.getLogger(JSONParser.class);

	Map<String, Object> jsonObjectElements;
	Map<String, Object> parsedElements;

	public Map<String, Object> parse(String jsonMsg, Class<? extends Mappable> mappableClass) {

		LOGGER.debug("Parsing JSON:\n{}", jsonMsg);
		LOGGER.debug("Mapping to {}.", mappableClass);

		JSONObject root = new JSONObject(jsonMsg);
		JSONObject jsonObject = null;
		JSONArray jsonArray = null;
		String jsonObjectRef = null;

		// Determine if JSON message should be parsed as an array or an object.
		if (mappableClass.isAnnotationPresent(JSONArrayReference.class)
				&& root.has(mappableClass.getAnnotation(JSONArrayReference.class).value())) {
			jsonObjectRef = mappableClass.getAnnotation(JSONArrayReference.class).value();
			jsonArray = root.getJSONArray(jsonObjectRef);

			int jsonArrayLenght = jsonArray.length();
			for (int i = 0; i < jsonArrayLenght; i++) {
				LOGGER.debug("Parsing array element {} of {}.", i + 1, jsonArrayLenght);
				parse(jsonArray.getJSONObject(i).toString(), mappableClass.getAnnotation(JSONArrayReference.class).classReference());
			}
		} else {
			if (mappableClass.isAnnotationPresent(JSONObjectReference.class)
					&& root.has(mappableClass.getAnnotation(JSONObjectReference.class).value())) {
				jsonObjectRef = mappableClass.getAnnotation(JSONObjectReference.class).value();
				jsonObject = root.getJSONObject(jsonObjectRef);
				jsonObjectElements = jsonObject.toMap();
			} else {
				jsonObject = root;
				jsonObjectElements = jsonObject.toMap();
			}

			parsedElements = new HashMap<String, Object>();

			List<Field> mappableFieldsList = Arrays.asList(mappableClass.getDeclaredFields());

			// Log all mappable fields.
			String mappableFields = "";
			for (Field field : mappableFieldsList) {
				mappableFields += "\n" + field.getName();
			}
			LOGGER.debug("Mappable fields:{}", mappableFields);

			// Process all mappable fields.
			for (Field field : mappableFieldsList) {
				boolean isAnnotated = false;
				isAnnotated = isAnnotated(field);
				if (isAnnotated) {
					process(field);
				}
			}

			if (parsedElements.size() > 0) {
				LOGGER.debug("Parsed parameters {}.", parsedElements);
			} else {
				LOGGER.warn("No parameters parsed.");
			}
		}

		return parsedElements;
	}

	private boolean isArrayElement(Field field) {
		String fieldName = field.getName();
		if (field.getAnnotation(JSONArrayReference.class) == null) {
			return false;
		} else {
			// logger.debug("{} is an array element.",
			// fieldName));
			return true;
		}
	}

	private boolean isObject(Field field) {
		String fieldName = field.getName();
		if (field.getAnnotation(JSONObjectReference.class) == null) {
			return false;
		} else {
			// logger.debug("{} is an object.", fieldName));
			return true;
		}
	}

	/**
	 * Checks whether a Field is annotated and annotation is not empty.
	 */
	private boolean isAnnotated(Field field) {
		String fieldName = field.getName();
		String fieldJSONRef = null;

		LOGGER.debug("Evaluating field {}...", fieldName);
		if (isObject(field)) {
			fieldJSONRef = field.getAnnotation(JSONObjectReference.class).value();
		} else if (isArrayElement(field)) {
			fieldJSONRef = field.getAnnotation(JSONArrayReference.class).value();
		} else {
			LOGGER.debug("... omitted - no annotation present.");
			return false;
		}

		if (!fieldJSONRef.equals("")) {
			LOGGER.debug("... parsable - processing.");
			return true;
		} else {
			LOGGER.warn("... omitted - annotation is empty.");
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
				// Primitive types are not handled.
				LOGGER.warn("Field {} omitted - unhandled primitive type.", fieldName);
				return;
			} else if (fieldType.isAssignableFrom(Mappable.class)) {
				LOGGER.debug("Field {} type implements 'Mappable' interface.", fieldName);
				// not implemented
			} else {
				LOGGER.debug("Assiging value {} to field {}...", value, fieldName);

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
				case "OrderStatus":
					jsonElementValue = OrderStatus.valueOf(value);
					break;
				case "Side":
					jsonElementValue = Side.valueOf(value);
					break;
				case "TradeStatus":
					jsonElementValue = TradeStatus.valueOf(value);
					break;
				default:
					jsonElementValue = null;
					LOGGER.warn("{} type is undefined in JSONParser.", fieldType.getName());
					break;
				}
			}
			LOGGER.debug("Field {} = type {} value {}.", fieldName, jsonElementValue.getClass().getSimpleName(),
					jsonElementValue.toString());
			parsedElements.put(fieldJSONRef, jsonElementValue);
		}
	}

	public int extractLastTransactionId(String msg) {
		int lastTransactionId = new JSONObject(msg).getInt("lastTransactionID");
		LOGGER.debug("Last transaction ID {}.", lastTransactionId);
		return lastTransactionId;
	}

}
