package resources;

import pojo.SubmitOrder;

public class TestDataBuild {
public SubmitOrder submitOrder(String bookid, String name) {
	SubmitOrder order = new SubmitOrder();
	order.setBookId(bookid);
	order.setCustomerName(name);
	return order;
}
}
