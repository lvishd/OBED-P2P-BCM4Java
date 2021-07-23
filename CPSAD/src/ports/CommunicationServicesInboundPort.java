package ports;

import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.ports.AbstractInboundPort;

import components.CommunicationComponent;
import interfaces.CommunicationCI;
import interfaces.MessageI;
import interfaces.P2PAddressI;

/**
 * Classe permettant de créer un service pour le port entrant du composant Communication.
 * @author OBED
 */
public class CommunicationServicesInboundPort extends AbstractInboundPort implements CommunicationCI{
	/** Numéro de série pour chaque instance. */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructeur d'un port entrant du composant Communication.
	 * @param component le composant qui possède ce port.
	 * @throws Exception une exception.
	 */
	public CommunicationServicesInboundPort(ComponentI component) throws Exception{
		super(CommunicationCI.class, component);
	}
	
	/**
	 * Constructeur d'un port sortant du composant Communication.
	 * @param uri l'URI unique du port.
	 * @param component le composant qui possède ce port.
	 * @throws Exception une exception.
	 */
	public CommunicationServicesInboundPort(String uri, ComponentI component) throws Exception {
		super(uri, CommunicationCI.class, component);
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
