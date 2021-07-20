package connectors;

import java.util.Set;

import fr.sorbonne_u.components.connectors.AbstractConnector;
import interfaces.P2PAddressI;
import interfaces.PositionI;
import interfaces.RegistrationCI;

public class RegistrationServicesConnector extends AbstractConnector implements RegistrationCI{

	@Override
	public Set<connectors.ConnectionInfo> registerInternal(P2PAddressI address, String communicationInboundPort,
			PositionI initialPosition, double initialRange, String routingInboundPortURI) throws Exception {
		return ((RegistrationCI)this.offering).registerInternal(address, communicationInboundPort, initialPosition, initialRange, routingInboundPortURI);
	}

	@Override
	public Set<connectors.ConnectionInfo> registerAccessPoint(P2PAddressI address, String communicationInboundPort,
			PositionI initialPosition, double initialRange,String routingInboundPortURI) throws Exception {
		return ((RegistrationCI)this.offering).registerAccessPoint(address, communicationInboundPort, initialPosition, initialRange, routingInboundPortURI);
	}
	@Override
	public void unregister(P2PAddressI address) throws Exception {
		((RegistrationCI)this.offering).unregister(address);
	}

}
