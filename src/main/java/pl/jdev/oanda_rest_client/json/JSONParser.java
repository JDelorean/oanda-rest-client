package pl.jdev.oanda_rest_client.json;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.jdev.oanda_rest_client.domain.Currency;
import pl.jdev.oanda_rest_client.domain.instrument.Instrument;

public class JSONParser {

	private final static Logger logger = LoggerFactory.getLogger(JSONParser.class);

	Map<String, Object> jsonObjectElements;
	Map<String, Object> parsedElements;

	public Map<String, Object> parse(String jsonMsg, Class<? extends Mappable> mappableClass) {

		logger.debug(String.format("Parsing JSON:\n%s", jsonMsg));
		logger.debug(String.format("Mapping to [%s].", mappableClass));

		JSONObject root = new JSONObject(jsonMsg);
		JSONObject jsonObject = null;

		JSONReference ref = mappableClass.getAnnotation(JSONReference.class);
		String jsonObjectRef = ref.value();
		boolean isArrayElement = ref.isArrayElement();

		if (isArrayElement) {
			jsonObject = root.getJSONArray(jsonObjectRef).getJSONObject(0);
		} else {
			jsonObject = root.getJSONObject(jsonObjectRef);
		}

		jsonObjectElements = jsonObject.toMap();
		parsedElements = new HashMap<String, Object>();

		System.out.println(jsonObjectElements);

		List<Field> mappableFields = Arrays.asList(mappableClass.getDeclaredFields());
		System.out.println(mappableFields);
		for (Field field : mappableFields) {
			boolean isParsable = false;
			isParsable = evaluate(field);
			if (isParsable) {
				process(field);
			}
		}

		if (parsedElements.size() > 0) {
			logger.debug(String.format("Parsed parameters [%s].", parsedElements));
		} else {
			logger.warn(String.format("No parameters parsed."));
		}
		return parsedElements;
	}

	private boolean evaluate(Field field) {
		String fieldName = field.getName();
		String fieldJSONRef = null;
		logger.debug(String.format("Evaluating field [%s]...", fieldName));
		// Verify field is annotated.
		if (field.isAnnotationPresent(JSONReference.class)) {
			fieldJSONRef = field.getAnnotation(JSONReference.class).value();
		} else if (field.getType().isAssignableFrom(ArrayList.class)) {
			System.out.println(field.getType());
			// fieldJSONRef =
			// field.get.getComponentType().getAnnotation(JSONReference.class).value();
			logger.debug("isArray");
		} else {
			logger.debug(String.format("... omitted - no annotation present."));
			return false;
		}
		// Verify annotated field has assigned JSON reference.
		if (!fieldJSONRef.equals("")) {
			logger.debug(String.format("... parsable - processing."));
			return true;
		} else {
			logger.warn(String.format("... omitted - annotation value is empty."));
			return false;
		}
	}

	private void process(Field field) {
		String fieldName = field.getName();
		String fieldJSONRef = field.getAnnotation(JSONReference.class).value();
		// Verify JSON message's object contains element equal to
		// field's JSON reference.
		if (jsonObjectElements.containsKey(fieldJSONRef)) {
			Object jsonElementValue = null;
			String value = (String) jsonObjectElements.get(fieldJSONRef);
			Class<?> fieldType = field.getType();
			if (fieldType.isPrimitive()) {
				logger.warn(String.format("Field [%s] omitted - unhandled primitive type.", fieldName));
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
					jsonElementValue = Instrument.InstrumentType.valueOf(value);
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

}
