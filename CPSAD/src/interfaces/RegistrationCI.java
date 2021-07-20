package interfaces;

import java.util.Set;

import fr.sorbonne_u.components.interfaces.OfferedCI;
import fr.sorbonne_u.components.interfaces.RequiredCI;


public interface RegistrationCI extends OfferedCI,RequiredCI{
	Set<connectors.ConnectionInfo> registerInternal(P2PAddressI address, 
			String communicationInboundPort, 
			PositionI initialPosition, 
			double initialRange,String routingInboundPortURI) throws Exception;


	Set<connectors.ConnectionInfo> registerAccessPoint(P2PAddressI address, 
			String communicationInboundPort, 
			PositionI initialPosition, 
			double initialRange,  String routingInboundPortURI) throws Exception;

	void unregister(P2PAddressI address) throws Exception;


}