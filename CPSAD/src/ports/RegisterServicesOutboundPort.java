package ports;

import java.util.Set;

import interfaces.P2PAddressI;
import interfaces.PositionI;
import interfaces.RegistrationCI;

import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.ports.AbstractOutboundPort;

public class RegisterServicesOutboundPort extends AbstractOutboundPort implements RegistrationCI{
	
	private static final long serialVersionUID = 1L;
	public RegisterServicesOutboundPort(ComponentI owner) throws Exception{
		super(RegistrationCI.class, owner);
	}

	
	public RegisterServicesOutboundPort(String uri, ComponentI owner) throws Exception {
		super(uri, RegistrationCI.class,  owner);
	}

	@Override
	public Set<connectors.ConnectionInfo> registerInternal(P2PAddressI address, String communicationInboundPort,
			PositionI initialPosition, double initialRange,String routingInboundPortURI) throws Exception {
		
		Set<connectors.ConnectionInfo> res = ((RegistrationCI)getConnector()).registerInternal(address, communicationInboundPort,
				initialPosition, initialRange, routingInboundPortURI);
		
		return res;
	
	}

	@Override
	public Set<connectors.ConnectionInfo> registerAccessPoint(P2PAddressI address, String communicationInboundPort,
			PositionI initialPosition, double initialRange, String routingInboundPortURI) throws Exception {
		return ((RegistrationCI)getConnector()).registerAccessPoint(address, communicationInboundPort, initialPosition, initialRange,routingInboundPortURI);
	}
	
	@Override
	public void unregister(P2PAddressI address) throws Exception {
		((RegistrationCI)getConnector()).unregister(address);
		
	}

	
}
