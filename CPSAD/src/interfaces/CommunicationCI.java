package interfaces;

public interface CommunicationCI {
	void connect (P2PAddressI address, String communicationInboudPortURI, String routingInboudPortURI) throws Exception;
	void routeMessage(MessageI m);
	void ping();
}
