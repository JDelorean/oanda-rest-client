package pl.jdev.oanda_rest_client.domain.order;

import java.text.SimpleDateFormat;
import java.util.Date;

import pl.jdev.oanda_rest_client.comm.rest.json.Mappable;
import pl.jdev.oanda_rest_client.comm.rest.json.annotation.JSONArrayReference;
import pl.jdev.oanda_rest_client.comm.rest.json.annotation.JSONObjectReference;
import pl.jdev.oanda_rest_client.domain.instrument.Instrument;

@JSONObjectReference("order")
@JSONArrayReference(value = "orders", classReference = Order.class)
public class Order implements Mappable {

	@JSONObjectReference("id")
	private final String id;

	@JSONObjectReference("createTime")
	private final String createTimestamp;

	@JSONObjectReference("price")
	private Float openPrice;
	private String openTimestamp;

	private Float closePrice;
	private String closeTimestamp;

	@JSONObjectReference("instrument")
	private Instrument instrument;

	@JSONObjectReference("units")
	private Integer units;

	@JSONObjectReference("side")
	private Side side;

	@JSONObjectReference("type")
	private Type type;

	@JSONObjectReference("state")
	private OrderStatus orderStatus;

	public Order(Instrument instrument, Side side) {
		this.createTimestamp = new SimpleDateFormat("YYYY-MM-DDTHH:MM:SS.nnnnnnnnnZ").format(new Date());
		this.orderStatus = OrderStatus.NEW;
		this.id = orderStatus + createTimestamp;
		this.instrument = instrument;
		this.side = side;
	}

	public float getOpenPrice() {
		return openPrice;
	}

	public float getClosePrice() {
		return closePrice;
	}

	public void setLotSize(int size) {
		units = size;
	}

	public int getLotSize() {
		return units;
	}

	public OrderStatus getStatus() {
		return orderStatus;
	}

	public String getOpenTimestamp() {
		return openTimestamp;
	}

	public void setOpenTimestamp(String openTimestamp) {
		this.openTimestamp = openTimestamp;
	}

	public String getCloseTimestamp() {
		return closeTimestamp;
	}

	public void setCloseTimestamp(String closeTimestamp) {
		this.closeTimestamp = closeTimestamp;
	}

	public int getUnits() {
		return units;
	}

	public void setUnits(int units) {
		this.units = units;
	}

	public Side getSide() {
		return side;
	}

	public void setSide(Side side) {
		this.side = side;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public String getId() {
		return id;
	}

	public String getCreateTimestamp() {
		return createTimestamp;
	}

	public Instrument getInstrument() {
		return instrument;
	}

	public void setOpenPrice(float openPrice) {
		this.openPrice = openPrice;
	}

	public void setClosePrice(float closePrice) {
		this.closePrice = closePrice;
	}

	public void setStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}

}
