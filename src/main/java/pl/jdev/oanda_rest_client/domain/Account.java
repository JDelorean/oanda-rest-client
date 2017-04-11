package pl.jdev.oanda_rest_client.domain;

import java.util.ArrayList;

import pl.jdev.oanda_rest_client.domain.instrument.Currency;
import pl.jdev.oanda_rest_client.json.Mappable;

public class Account implements Mappable{

	private String id;
	private long nav;
	private String alias;
	private Currency currency;
	private boolean hedging;
	private long availMargin;
	private int marginRate;
	private long usedMargin;
	private int openPositionCount;
	private int openTradeCount;
	private int pendingOrderCount;
	private long profitLoss;
	private ArrayList<Position> positions;
	
	public Account(String id, long nav, String alias, Currency currency, boolean hedging, long availMargin,
			int marginRate, long usedMargin, int openPositionCount, int openTradeCount, int pendingOrderCount,
			long profitLoss, ArrayList<Position> positions) {
		super();
		this.id = id;
		this.nav = nav;
		this.alias = alias;
		this.currency = currency;
		this.hedging = hedging;
		this.availMargin = availMargin;
		this.marginRate = marginRate;
		this.usedMargin = usedMargin;
		this.openPositionCount = openPositionCount;
		this.openTradeCount = openTradeCount;
		this.pendingOrderCount = pendingOrderCount;
		this.profitLoss = profitLoss;
		this.positions = positions;
	}
	
	
	
}
