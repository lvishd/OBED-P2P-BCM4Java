package ports;

import interfaces.AddressI;
import interfaces.CommunicationCI;
import interfaces.MessageI;
import interfaces.P2PAddressI;

import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.ports.AbstractOutboundPort;


public class CommunicationServicesOutBoundPort extends AbstractOutboundPort implements CommunicationCI{

	private static final long serialVersionUID = 1L;
	

	public CommunicationServicesOutBoundPort(ComponentI owner) throws Exception {
		super(CommunicationCI.class, owner);
	}


	public CommunicationServicesOutBoundPort(String uri, ComponentI owner) throws Exception {
		super(uri,  CommunicationCI.class, owner);
	}

	


	@Override
	public int hasRouteFor(AddressI address) throws Exception {
		return ((CommunicationCI)getConnector()).hasRouteFor(address);
	}

	@Override
	public void ping() {
		try {
			((CommunicationCI)getConnector()).ping();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	@Override
	public void connect(P2PAddressI address, String communicationInboundPortURI, String routingInboundPortURI)
			throws Exception {	
		System.out.println("tata");
		((CommunicationCI) this.getConnector()).connect(address, communicationInboundPortURI, routingInboundPortURI);
		System.out.println("toto");
	}


	@Override
	public void routeMessage(MessageI m) {
		// TODO Auto-generated method stub
		try {
			((CommunicationCI)getConnector()).routeMessage(m);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
