package pl.jdev.oanda_rest_client.main;

import java.net.URISyntaxException;

import org.apache.http.Header;

import pl.jdev.oanda_rest_client.rest.AccountRequest;

public class JDevFXAppMain {

	public static void main(String[] args) {

		// new TaskManager();

		// CurrencyPair cp = new CurrencyPair("eur", "usd");
		// CurrencyPair cp2 = new CurrencyPair("Gbp", "USD");
		// System.out.println(cp.toString());
		//
		// AppDateTime from = null;
		// AppDateTime to = null;
		// try {
		// from = new AppDateTime(new Date());
		// Thread.sleep(1000);
		// to = new AppDateTime(new Date());
		// } catch (InterruptedException | IOException e1) {
		// // TODO Auto-generated catch block
		// e1.printStackTrace();
		// }
		//
		// InstrumentRequest ir = new InstrumentRequest();
		// try {
		// System.out.println(ir.getCandlesWithCount(cp.toString(),
		// CandlestickPrice.M, CandlestickGranularity.H1, 20));
		// System.out.println(ir.getCandlesWithTimeframe(cp2.toString(),
		// CandlestickPrice.A, CandlestickGranularity.S5, from, to));
		// } catch (URISyntaxException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

		AccountRequest accReq = new AccountRequest();
		
		try {
			accReq.getAccountsForToken();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		try {
//			System.out.println(accReq.getAccountsForToken());
//			for (Header el : accReq.getAccountsForToken().getAllHeaders()) {
//				System.out.println(el.toString());
//			}
//			System.out.println("\n");
//			System.out.println(accReq.getAccount());
//			for (Header el : accReq.getAccount().getAllHeaders()) {
//				System.out.println(el.toString());
//			}
//			System.out.println("\n");
//			System.out.println(accReq.getAccountSummary());
//			for (Header el : accReq.getAccountSummary().getAllHeaders()) {
//				System.out.println(el.toString());
//			}
//			System.out.println("\n");
//			System.out.println(accReq.getAccountInstruments());
//			for (Header el : accReq.getAccountInstruments().getAllHeaders()) {
//				System.out.println(el.toString());
//			}
//			System.out.println("\n");
//			System.out.println(accReq.getAccountChanges());
//			for (Header el : accReq.getAccountChanges().getAllHeaders()) {
//				System.out.println(el.toString());
//			}
//		} catch (URISyntaxException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

	}
}
