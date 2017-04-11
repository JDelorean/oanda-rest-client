package pl.jdev.oanda_rest_client.domain;

public enum Position {

	LONG("L"), SHORT("S");

	private final String posCode;
	private long profitLoss;

	private Position(String posCode) {
		this.posCode = posCode;
	}

	public String getPositionCode() {
		return posCode;
	}

	public static Position setPosition(String posCode) {
		switch (posCode) {
		case "L":
			return LONG;
		case "S":
			return SHORT;
		}
		throw new IllegalStateException("Invalid Position code: [" + posCode + "].");
	}

	public long getProfitLoss() {
		return profitLoss;
	}

	public void setProfitLoss(long profitLoss) {
		this.profitLoss = profitLoss;
	}
	
}
