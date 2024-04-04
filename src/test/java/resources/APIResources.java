package resources;

public enum APIResources {

	submitOrderAPI("orders"), 
	getOrderAPI("orders/{orderId}"), 
	updateOrderAPI("orders/{orderId}"), 
	deleteOrderAPI("orders/{orderId}");

	private String resource;

	APIResources(String resource) {
		this.resource = resource;
	}

	public String getResource() {
		return resource;
	}
}
