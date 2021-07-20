package interfaces;

import fr.sorbonne_u.components.interfaces.OfferedCI;
import fr.sorbonne_u.components.interfaces.RequiredCI;

public interface CommunicationCI extends OfferedCI,RequiredCI{
	void connect (P2PAddressI address, String communicationInboudPortURI, String routingInboudPortURI) throws Exception;
	void routeMessage(MessageI m) throws Exception;
	void ping() throws Exception;
	int hasRouteFor(AddressI address) throws Exception;
}
