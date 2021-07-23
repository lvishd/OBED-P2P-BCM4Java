package connectors;

import fr.sorbonne_u.components.connectors.AbstractConnector;

import interfaces.CommunicationCI;
import interfaces.MessageI;
import interfaces.P2PAddressI;

/**
 * Classe permettant d'instancier un connecteur pour le composant de type communication.
 * @author OBED
 */
public class CommunicationServicesConnector extends AbstractConnector implements CommunicationCI {
	
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
