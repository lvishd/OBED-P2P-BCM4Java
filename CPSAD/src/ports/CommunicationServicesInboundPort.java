package ports;

import components.CommunicationComponent;
import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.ports.AbstractInboundPort;
import interfaces.AddressI;
import interfaces.CommunicationCI;
import interfaces.MessageI;
import interfaces.P2PAddressI;

public class CommunicationServicesInboundPort extends AbstractInboundPort implements CommunicationCI{
	
	private static final long serialVersionUID = 1L;

	public CommunicationServicesInboundPort(ComponentI owner) throws Exception{
		super(CommunicationCI.class, owner);
	}
	
	
	public CommunicationServicesInboundPort(String uri, ComponentI owner) throws Exception {
		super(uri, CommunicationCI.class, owner);
		assert owner instanceof CommunicationComponent;
	}

	@Override
	public void connect(P2PAddressI address, String communicationInboudURI, String routingInboudPortURI) throws Exception {
		getOwner().handleRequest(c -> {
			((CommunicationComponent) c).connect(address, communicationInboudURI,routingInboudPortURI);
			return null;
		});
	}



	@Override
	public void routeMessage(MessageI m) throws Exception  { 
		getOwner().handleRequest(c -> {
			((CommunicationComponent) c).routeMessage(m);
			return null;
		});
	}

	@Override
	public int hasRouteFor(AddressI address) throws Exception {
		return getOwner().handleRequest(c -> ((CommunicationComponent) c).hasRouteFor(address));
	}

	@Override
	public void ping() throws Exception {
		getOwner().handleRequest(c -> {
			((CommunicationCI) c).ping();
			return null;
		});
	}


}
