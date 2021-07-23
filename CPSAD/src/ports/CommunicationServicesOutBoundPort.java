package ports;

import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.ports.AbstractOutboundPort;

import interfaces.CommunicationCI;
import interfaces.MessageI;
import interfaces.P2PAddressI;

/**
 * Classe permettant de créer un service pour le port sortant du composant Communication.
 * @author OBED
 */
public class CommunicationServicesOutBoundPort extends AbstractOutboundPort implements CommunicationCI{
	/** Numéro de série pour chaque instance. */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructeur d'un port sortant du composant Communication.
	 * @param component le composant qui possède ce port.
	 * @throws Exception une exception.
	 */
	public CommunicationServicesOutBoundPort(ComponentI component) throws Exception {
		super(CommunicationCI.class, component);
	}

	/**
	 * Constructeur d'un port sortant du composant Communication.
	 * @param uri l'URI unique du port.
	 * @param component le composant qui possède ce port.
	 * @throws Exception une exception.
	 */
	public CommunicationServicesOutBoundPort(String uri, ComponentI component) throws Exception {
		super(uri,  CommunicationCI.class, component);
	}

	@Override
	public void connect(P2PAddressI address, String communicationInboundPortURI, String routingInboundPortURI)
			throws Exception {	
		System.out.println("tata");
		((CommunicationCI) this.getConnector()).connect(address, communicationInboundPortURI, routingInboundPortURI);
		System.out.println("toto");
	}

	@Override
	public void routeMessage(MessageI m) {
		try {
			((CommunicationCI)getConnector()).routeMessage(m);
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}

	@Override
	public void ping() {
		System.out.println("Ping!");
	}
}
