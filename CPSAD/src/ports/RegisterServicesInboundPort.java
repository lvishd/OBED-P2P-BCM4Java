package ports;

import java.util.Set;

import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.ports.AbstractInboundPort;

import components.RegisterComponent;
import interfaces.P2PAddressI;
import interfaces.PositionI;
import interfaces.RegistrationCI;
import connectors.ConnectionInfo;

/**
 * Classe permettant de créer un service pour le port entrant du composant Register (le simulateur).
 * @author OBED
 */
public class RegisterServicesInboundPort extends AbstractInboundPort implements RegistrationCI {
	/** Numéro de série pour chaque instance. */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructeur d'un port entrant du composant Register.
	 * @param component le composant qui possède ce port.
	 * @throws Exception  une exception.
	 */
	public RegisterServicesInboundPort(ComponentI component) throws Exception {
		super(RegistrationCI.class, component);
		assert(owner instanceof RegisterComponent);
	}
	
	/**
	 * Constructeur d'un port sortant du composant Register.
	 * @param uri l'URI unique du port.
	 * @param component le composant qui possède ce port.
	 * @throws Exception  une exception.
	 */
	public RegisterServicesInboundPort(String uri, ComponentI component) throws Exception {
		super(uri, RegistrationCI.class, component);
		assert(owner instanceof RegisterComponent);
	}
	
	@Override
	public Set<ConnectionInfo> registerInternal(P2PAddressI address, String communicationInboundPort,
			PositionI initialPosition, double initialRange, String routingInboundPortURI) throws Exception {
		return (Set<ConnectionInfo>) getOwner().handleRequest(c->((RegisterComponent)c).registerInternal(address, communicationInboundPort, initialPosition, initialRange));
		
	}

	@Override
	public void unregister(P2PAddressI address) throws Exception {
		// TODO Auto-generated method stub
	}


	@Override
	public Set<ConnectionInfo> registerAccessPoint(P2PAddressI address, String communicationInboundPort,
			PositionI initialPosition, double initialRange,String routingInboundPortURI) throws Exception {
			return (Set<ConnectionInfo>) getOwner().handleRequest(c->((RegisterComponent)c).registerAccessPoint(address, communicationInboundPort, initialPosition, initialRange,routingInboundPortURI));
	}
}
