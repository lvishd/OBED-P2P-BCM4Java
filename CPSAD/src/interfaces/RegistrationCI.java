package interfaces;

import java.util.Set;

import fr.sorbonne_u.components.interfaces.OfferedCI;


public interface RegistrationCI extends OfferedCI{
	Set<ConnectionInfo> registerInternal(P2PAddressI address, 
			String communicationInboundPort, 
			PositionI initialPosition, 
			double initialRange,String routingInboundPortURI) throws Exception;


	Set<ConnectionInfo> registerAccessPoint(P2PAddressI address, 
			String communicationInboundPort, 
			PositionI initialPosition, 
			double initialRange) throws Exception;

	void unregister(P2PAddressI address) throws Exception;
}