package ports;

import java.util.Set;

import components.RegisterComponent;
import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.ports.AbstractInboundPort;
import interfaces.ConnectionInfo;
import interfaces.P2PAddressI;
import interfaces.PositionI;
import interfaces.RegistrationCI;

public class RegisterServicesInboundPort extends AbstractInboundPort implements RegistrationCI 
{

	
	private static final long serialVersionUID = 1L;

	
	public RegisterServicesInboundPort(ComponentI owner) throws Exception
	{
		super(RegistrationCI.class, owner);
		assert(owner instanceof RegisterComponent);
	}
	
	
	public RegisterServicesInboundPort(String uri, ComponentI owner) throws Exception
	{
		super(uri, RegistrationCI.class, owner);
		assert(owner instanceof RegisterComponent);
	}
	
	

	@Override
	public Set<ConnectionInfo> registerInternal(P2PAddressI address, String communicationInboundPort,
			PositionI initialPosition, double initialRange, String routingInboundPortURI) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<ConnectionInfo> registerAccessPoint(P2PAddressI address, String communicationInboundPort,
			PositionI initialPosition, double initialRange) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void unregister(P2PAddressI address) throws Exception {
		// TODO Auto-generated method stub
		
	}
}
