package pl.jdev.oanda_rest_client.domain.account;

import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import pl.jdev.oanda_rest_client.comm.rest.json.Mappable;
import pl.jdev.oanda_rest_client.comm.rest.json.annotation.JSONArrayReference;
import pl.jdev.oanda_rest_client.comm.rest.json.annotation.JSONObjectReference;
import pl.jdev.oanda_rest_client.domain.Currency;
import pl.jdev.oanda_rest_client.domain.Position;

@Entity
@Table(name = "ACCT")
@JSONObjectReference("account")
@JSONArrayReference(value = "accounts", classReference = Account.class)
public class Account implements Mappable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ACCT_ID")
	@JSONObjectReference("id")
	private String id;

	@Column(name = "NAV")
	@JSONObjectReference("NAV")
	private Float nav;

	@Column(name = "ALIAS")
	@JSONObjectReference("alias")
	private String alias;

	@Column(name = "CURR")
	@JSONObjectReference("currency")
	private Currency currency;

	private Boolean hedging;

	@Column(name = "AV_MRGN")
	@JSONObjectReference("marginAvailable")
	private Float availMargin;

	@Column(name = "MRGN_RT")
	@JSONObjectReference("marginRate")
	private Float marginRate;

	@Column(name = "MRGN_USED")
	@JSONObjectReference("marginUsed")
	private Float usedMargin;
	private Integer openPositionCount;
	private Integer openTradeCount;
	private Integer pendingOrderCount;
	private Float profitLoss;

	@JSONObjectReference("position")
	@JSONArrayReference(value = "positions", classReference = Position.class)
	private ArrayList<Position> positions;

	public Account() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public float getNav() {
		return nav;
	}

	public void setNav(float nav) {
		this.nav = nav;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	public boolean isHedging() {
		return hedging;
	}

	public void setHedging(boolean hedging) {
		this.hedging = hedging;
	}

	public float getAvailMargin() {
		return availMargin;
	}

	public void setAvailMargin(float availMargin) {
		this.availMargin = availMargin;
	}

	public float getMarginRate() {
		return marginRate;
	}

	public void setMarginRate(float marginRate) {
		this.marginRate = marginRate;
	}

	public float getUsedMargin() {
		return usedMargin;
	}

	public void setUsedMargin(float usedMargin) {
		this.usedMargin = usedMargin;
	}

	public int getOpenPositionCount() {
		return openPositionCount;
	}

	public void setOpenPositionCount(int openPositionCount) {
		this.openPositionCount = openPositionCount;
	}

	public int getOpenTradeCount() {
		return openTradeCount;
	}

	public void setOpenTradeCount(int openTradeCount) {
		this.openTradeCount = openTradeCount;
	}

	public int getPendingOrderCount() {
		return pendingOrderCount;
	}

	public void setPendingOrderCount(int pendingOrderCount) {
		this.pendingOrderCount = pendingOrderCount;
	}

	public float getProfitLoss() {
		return profitLoss;
	}

	public void setProfitLoss(float profitLoss) {
		this.profitLoss = profitLoss;
	}

	public ArrayList<Position> getPositions() {
		return positions;
	}

	public void setPositions(ArrayList<Position> positions) {
		this.positions = positions;
	}

}
