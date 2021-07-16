package ports;

import java.util.Set;

import components.RegisterComponent;
import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.ports.AbstractInboundPort;
import interfaces.ConnectionInfo;
import interfaces.P2PAddressI;
import interfaces.PositionI;
import interfaces.RegistrationCI;

public class RegisterServicesInboundPort extends AbstractInboundPort implements RegistrationCI {


	private static final long serialVersionUID = 1L;
	public RegisterServicesInboundPort( ComponentI owner)
			throws Exception {
		super(RegistrationCI.class, owner);
		// TODO Auto-generated constructor stub
	}
	@Override
	public Set<ConnectionInfo> registerInternal(P2PAddressI address, String communicationInboundPort,
			PositionI initialPosition, double initialRange, String routingInboundPortURI) throws Exception {
		
	}
	@Override
	public Set<ConnectionInfo> registerAccessPoint(P2PAddressI address, String communicationInboundPort,
			PositionI initialPosition, double initialRange) throws Exception {
		// TODO Auto-generated method stub
		return getOwner().handleRequest(c->((RegisterComponent) c).registerAccessPoint(address, communicationInboundPort, initialPosition, initialRange));
	}
	@Override
	public void unregister(P2PAddressI address) throws Exception {
		// TODO Auto-generated method stub
		((RegisterComponent)getOwner()).unregister(address);
	}

}
