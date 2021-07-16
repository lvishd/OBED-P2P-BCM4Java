package connectors;

import interfaces.P2PAddressI;

public class ConnectionInfo {
	
	private P2PAddressI address;
	private String communicationInboundPortURI;
	private String routingInboundPortURI;
	private boolean isRouting;
	
	
	public ConnectionInfo(P2PAddressI address, String communicationInboundPortURI, String routingInboundPortURI, boolean isRouting) {
		this.address = address;
		this.communicationInboundPortURI = communicationInboundPortURI;
		this.routingInboundPortURI = routingInboundPortURI;
		this.isRouting = isRouting;
	}

	
	public P2PAddressI getAddress() {
		return address;
	}
	
	
	public String getCommunicationInboudPort() {
		return communicationInboundPortURI;
	}

	
	public boolean isRouting() {
		return isRouting;
	}

	
	public String getRoutingInboundPortURI() {
		return routingInboundPortURI;
	}
}
