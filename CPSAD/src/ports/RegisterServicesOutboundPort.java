package ports;

import java.util.Set;

import interfaces.P2PAddressI;
import interfaces.PositionI;
import interfaces.RegistrationCI;

import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.ports.AbstractOutboundPort;

/**
 * Classe permettant de créer un service pour le port sortant du composant Register (le simulateur).
 * @author OBED
 */
public class RegisterServicesOutboundPort extends AbstractOutboundPort implements RegistrationCI{
	/** Numéro de série pour chaque instance. */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructeur d'un port sortant du composant Register.
	 * @param component le composant qui possède ce port.
	 * @throws Exception une exception.
	 */
	public RegisterServicesOutboundPort(ComponentI component) throws Exception {
		super(RegistrationCI.class, component);
	}

	/**
	 * Constructeur d'un port sortant du composant Register.
	 * @param uri l'URI unique du port.
	 * @param component le composant qui possède ce port.
	 * @throws Exception une exception.
	 */
	public RegisterServicesOutboundPort(String uri, ComponentI component) throws Exception {
		super(uri, RegistrationCI.class,  component);
	}

	@Override
	public Set<connectors.ConnectionInfo> registerInternal(P2PAddressI address, String communicationInboundPort,
			PositionI initialPosition, double initialRange,String routingInboundPortURI) throws Exception {
		
		Set<connectors.ConnectionInfo> registerInternal = ((RegistrationCI)getConnector()).registerInternal(address, communicationInboundPort,
				initialPosition, initialRange, routingInboundPortURI);
		
		return registerInternal;
	}

	@Override
	public Set<connectors.ConnectionInfo> registerAccessPoint(P2PAddressI address, String communicationInboundPort,
			PositionI initialPosition, double initialRange, String routingInboundPortURI) throws Exception {
		return ((RegistrationCI)getConnector()).registerAccessPoint(address, communicationInboundPort, initialPosition, initialRange,routingInboundPortURI);
	}
	
	@Override
	public void unregister(P2PAddressI address) throws Exception {
		((RegistrationCI)getConnector()).unregister(address);
	}

	
}
