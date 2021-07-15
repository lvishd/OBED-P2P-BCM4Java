package registrator;

import java.util.Set;

import interfaces.ConnectionInfo;
import interfaces.P2PAddressI;
import interfaces.PositionI;


public interface RegistrationCI {
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