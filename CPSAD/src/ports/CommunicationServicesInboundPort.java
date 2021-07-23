package ports;

import components.CommunicationComponent;
import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.ports.AbstractInboundPort;
import interfaces.CommunicationCI;
import interfaces.MessageI;
import interfaces.P2PAddressI;

public class CommunicationServicesInboundPort extends AbstractInboundPort implements CommunicationCI{
	
	private static final long serialVersionUID = 1L;

	public CommunicationServicesInboundPort(ComponentI owner) throws Exception{
		super(CommunicationCI.class, owner);
	}
	
	
	public CommunicationServicesInboundPort(String uri, ComponentI owner) throws Exception {
		super(uri, CommunicationCI.class, owner);
		assert owner instanceof CommunicationComponent;
	}

	@Override
    public void connect(P2PAddressI address, String communicationInboudURI, String routingInboudPortURI) throws Exception {
        this.getOwner().runTask(c -> {
            try {
                ((CommunicationComponent) c).connect(address, communicationInboudURI, routingInboudPortURI);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

	@Override
    public void routeMessage(MessageI m) throws Exception {
        this.getOwner().runTask(c -> {
            try {
                ((CommunicationComponent) c).routeMessage(m);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }


	@Override
	public void ping() {
		System.out.println("Ping!");
	}
}
