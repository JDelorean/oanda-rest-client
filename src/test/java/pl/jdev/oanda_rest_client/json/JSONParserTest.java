package pl.jdev.oanda_rest_client.json;

import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Test;

import pl.jdev.oanda_rest_client.domain.Account;

public class JSONParserTest {
	
	@Test
	public void shouldParseAccountsAccountIdMsg(){
		// given
		File jsonFile = new File("src/test/resources/v3_accounts_accountID.example");
		FileInputStream input = null;
		StringBuilder jsonMsg = new StringBuilder();
		int ch;
		try {
			input = new FileInputStream(jsonFile);
			while((ch = input.read()) != -1){
				jsonMsg.append((char)ch);
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
		jsonParser.parse(jsonMsg.toString(), Account.class);
		
		// then
		
	}

}
