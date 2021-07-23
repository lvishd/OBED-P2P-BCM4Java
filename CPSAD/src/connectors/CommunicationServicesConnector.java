package connectors;

import fr.sorbonne_u.components.connectors.AbstractConnector;
import interfaces.CommunicationCI;
import interfaces.MessageI;
import interfaces.P2PAddressI;

public class CommunicationServicesConnector extends AbstractConnector implements CommunicationCI {
	
	public CommunicationServicesConnector() {
		super();

	}
	
	@Override
	public void connect(P2PAddressI address, String communicationInboudPortURI, String routingInboudPortURI)
			throws Exception {
			((CommunicationCI)offering).connect(address, communicationInboudPortURI, routingInboudPortURI);
			
	}

	@Override
	public void routeMessage(MessageI m) throws Exception {
		((CommunicationCI)offering).routeMessage(m);
		
	}

	@Override
	public void ping() {
		System.out.println("Ping!");
	}

}
