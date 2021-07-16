package components;

import java.util.Set;

import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.annotations.OfferedInterfaces;
import fr.sorbonne_u.components.exceptions.ComponentShutdownException;
import interfaces.ConnectionInfo;
import interfaces.P2PAddressI;
import interfaces.PositionI;
import interfaces.RegistrationCI;
import ports.RegisterServicesInboundPort;

@OfferedInterfaces(offered = {RegistrationCI.class})
public class RegisterComponent extends AbstractComponent{
	protected RegisterServicesInboundPort registerPort;
	
	protected RegisterComponent() {
		super(1, 0);
		try {
			registerPort  = new RegisterServicesInboundPort(this);
			registerPort.publishPort();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}


	@Override
	public synchronized void shutdown() throws ComponentShutdownException {
		// TODO Auto-generated method stub
		try {
			registerPort.unpublishPort();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new ComponentShutdownException(e);
		}
	}

	Set<ConnectionInfo> register(P2PAddressI address, 
			String communicationInboudPort, 
			PositionI init, 
			double initRange, boolean isRouting) {
		return null;
	}


	public Set<ConnectionInfo> registerAccessPoint(P2PAddressI address, 
			String communicationInboudPort, 
			PositionI init, 
			double initRange) {
		return null;
	}

	void unregister(P2PAddressI address) {

	}
}
