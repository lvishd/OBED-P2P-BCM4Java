package nodeTable;

import interfaces.P2PAddressI;
import interfaces.PositionI;
import connectors.ConnectionInfo;

public class NodeTable {
	private String communicationInboundPortURI;
	private PositionI initialPosition;
	private String routingInboundPortURI;
	
	
	public NodeTable(String communicationInboundPortURI,
			PositionI initialPosition, String routingInboundPortURI) {
		super();
		this.communicationInboundPortURI = communicationInboundPortURI;
		this.initialPosition = initialPosition;
		this.routingInboundPortURI = routingInboundPortURI;
	}
	
	public NodeTable(String communicationInboundPortURI) {
		super();
		this.communicationInboundPortURI = communicationInboundPortURI;
	}

	
	public NodeTable(String communicationInboundPortURI,PositionI initialPosition) {
		super();
		this.communicationInboundPortURI = communicationInboundPortURI;
		this.initialPosition = initialPosition;
		this.routingInboundPortURI = null;
	}
	
	
	public NodeTable(String communicationInboundPortURI, String routingInboundPortURI){
		super();
		this.communicationInboundPortURI = communicationInboundPortURI;
		this.routingInboundPortURI = routingInboundPortURI;
	}
	
	public PositionI getInitialPosition() {
		return initialPosition;
	}

	
	public static ConnectionInfo getConnectionInfo(P2PAddressI address, NodeTable wrapper) {
		
		return new ConnectionInfo(address, wrapper.communicationInboundPortURI, 
				wrapper.routingInboundPortURI == null? null : wrapper.routingInboundPortURI,
						wrapper.routingInboundPortURI == null? false : true);
	}
}
