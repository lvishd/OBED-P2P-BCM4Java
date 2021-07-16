package connectors;

import java.util.Set;

import interfaces.ConnectionInfo;
import interfaces.P2PAddressI;
import interfaces.PositionI;
import interfaces.RegistrationCI;

public class RegistrationServicesConnector implements RegistrationCI{

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
