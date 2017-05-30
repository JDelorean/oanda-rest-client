package pl.jdev.oanda_rest_client.comm.rest.json;

import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

import org.junit.Test;

import pl.jdev.oanda_rest_client.comm.rest.json.JSONParser;
import pl.jdev.oanda_rest_client.domain.account.Account;
import pl.jdev.oanda_rest_client.domain.instrument.CurrencyPair;
import pl.jdev.oanda_rest_client.domain.order.Order;
import pl.jdev.oanda_rest_client.domain.trade.Trade;

public class JSONParserTest {

	@Test
	public void shouldParseAccountsMsg() {
		// given
		File jsonFile = new File("src/test/resources/v3_accounts.example");
		FileInputStream input = null;
		StringBuilder jsonMsg = new StringBuilder();
		int ch;
		try {
			input = new FileInputStream(jsonFile);
			while ((ch = input.read()) != -1) {
				jsonMsg.append((char) ch);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			fail();
		} catch (IOException e) {
			e.printStackTrace();
			fail();
		}
		JSONParser jsonParser = new JSONParser();

		// when
		Map<String, Object> accountsAttributes = jsonParser.parse(jsonMsg.toString(), Account.class);

		// then
	}

	@Test
	public void shouldParseAccountsAccountIdMsg() {
		// given
		File jsonFile = new File("src/test/resources/v3_accounts_accountID.example");
		FileInputStream input = null;
		StringBuilder jsonMsg = new StringBuilder();
		int ch;
		try {
			input = new FileInputStream(jsonFile);
			while ((ch = input.read()) != -1) {
				jsonMsg.append((char) ch);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			fail();
		} catch (IOException e) {
			e.printStackTrace();
			fail();
		}
		JSONParser jsonParser = new JSONParser();

		// when
		Map<String, Object> accountAttributes = jsonParser.parse(jsonMsg.toString(), Account.class);

		// then

	}

	@Test
	public void shouldParseAccountsAccountIdInstrumentsMsg() {
		// given
		File jsonFile = new File("src/test/resources/v3_accounts_accountID_instruments.example");
		FileInputStream input = null;
		StringBuilder jsonMsg = new StringBuilder();
		int ch;
		try {
			input = new FileInputStream(jsonFile);
			while ((ch = input.read()) != -1) {
				jsonMsg.append((char) ch);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			fail();
		} catch (IOException e) {
			e.printStackTrace();
			fail();
		}
		JSONParser jsonParser = new JSONParser();

		// when
		Map<String, Object> currencyPairAttributes = jsonParser.parse(jsonMsg.toString(), CurrencyPair.class);

		// then

	}

	@Test
	public void shouldParseAccountsAccountIdOrdersMsg() {
		// given
		File jsonFile = new File("src/test/resources/v3_accounts_accountID_orders.example");
		FileInputStream input = null;
		StringBuilder jsonMsg = new StringBuilder();
		int ch;
		try {
			input = new FileInputStream(jsonFile);
			while ((ch = input.read()) != -1) {
				jsonMsg.append((char) ch);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			fail();
		} catch (IOException e) {
			e.printStackTrace();
			fail();
		}
		JSONParser jsonParser = new JSONParser();

		// when
		Map<String, Object> orderAttributes = jsonParser.parse(jsonMsg.toString(), Order.class);

		// then
	}

	@Test
	public void shouldParseAccountsAccountIdTradesMsg() {
		// given
		File jsonFile = new File("src/test/resources/v3_accounts_accountID_trades.example");
		FileInputStream input = null;
		StringBuilder jsonMsg = new StringBuilder();
		int ch;
		try {
			input = new FileInputStream(jsonFile);
			while ((ch = input.read()) != -1) {
				jsonMsg.append((char) ch);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			fail();
		} catch (IOException e) {
			e.printStackTrace();
			fail();
		}
		JSONParser jsonParser = new JSONParser();

		// when
		Map<String, Object> tradeAttributes = jsonParser.parse(jsonMsg.toString(), Trade.class);

		// then
	}

}
