package components;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.annotations.OfferedInterfaces;
import fr.sorbonne_u.components.annotations.RequiredInterfaces;
import fr.sorbonne_u.components.exceptions.ComponentShutdownException;

import connectors.CommunicationServicesConnector;
import connectors.ConnectionInfo;
import connectors.RegistrationServicesConnector;
import P2PAddress.P2PAddress;
import interfaces.CommunicationCI;
import interfaces.MessageI;
import interfaces.RegistrationCI;
import interfaces.RoutingManagementCI;
import interfaces.P2PAddressI;
import interfaces.PositionI;
import message.Message;
import ports.CommunicationServicesInboundPort;
import ports.CommunicationServicesOutBoundPort;
import ports.RegisterServicesOutboundPort;

@RequiredInterfaces(required = { RegistrationCI.class, CommunicationCI.class, RoutingManagementCI.class })
@OfferedInterfaces(offered = { CommunicationCI.class, RoutingManagementCI.class })
public class CommunicationComponent extends AbstractComponent {

	/**
	 * Constructeur du composant de communication appellant le constructeur d'AbstractComponent.
	 * @param nbThreads, le nombre de threads que l'on veut lancer parallèlement.
	 * @param nbSchedulableThreads, le nombre de threads ordonnançables.
	 */
	protected CommunicationComponent(int nbThreads, int nbSchedulableThreads) {
		super(nbThreads, nbSchedulableThreads);
	}

	/** Le port entrant de communication. */
	protected CommunicationServicesInboundPort communicationServicesInboundPort;
	/** Le port sortant du simulateur. */
	protected RegisterServicesOutboundPort registerServicesOutboundPort;
	/** L'addresse. */
	private P2PAddressI address;
	/** La position initiale.  */
	private PositionI initialPosition;
	/** La portée initiale.  */
	private double initialRange;
	/** La collection d'addresse voisines (et de leurs ports sortants) souhaitant communiquer en P2P. */
	private Map<P2PAddressI, CommunicationServicesOutBoundPort> communicationConnections;

	/**
	 * Créer et initialise un composant de communication servant de noeud.
	 * @param address l'addresse du composant.
	 * @param initialPosition la position initial du composant (non variable d'après le cahier des charges).
	 * @param initialRange la portée initiale du composant (non variable d'après le cahier des charges).
	 */
	protected CommunicationComponent(P2PAddressI address, PositionI initialPosition, double initialRange) {
		super(1, 0);
		this.address = address;
		this.initialPosition = initialPosition;
		this.initialRange = initialRange;
		this.communicationConnections = new HashMap<>();

		try {
			this.registerServicesOutboundPort = new RegisterServicesOutboundPort(this);
			this.communicationServicesInboundPort = new CommunicationServicesInboundPort(this);
			this.registerServicesOutboundPort.publishPort();
			this.communicationServicesInboundPort.publishPort();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
	
	/**
	 * Connecte le 
	 * @param address
	 * @param communicationInboundURI
	 * @param routingInboundPortURI
	 */
	public void connect(P2PAddressI address, String communicationInboundURI, String routingInboundPortURI) {
		try {
			if (this.address.equals(address)) {
				System.out.println("### connexion à moi-meme!");
				return;
			}

			if (communicationConnections.containsKey(address))
				return;

			CommunicationServicesOutBoundPort port = new CommunicationServicesOutBoundPort(this);
			port.publishPort();

			doPortConnection(port.getPortURI(), communicationInboundURI,
					CommunicationServicesConnector.class.getCanonicalName());
			communicationConnections.put(address, port);

			System.out.println("nouvelle connexion établie : " + this.address + " -> " + address);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void connect(P2PAddressI address1, P2PAddressI address2, String communicationInboundURI,
			String routingInboundPortURI) {
		try {

			if (communicationConnections.containsKey(address2))
				return;

			CommunicationServicesOutBoundPort port = new CommunicationServicesOutBoundPort(this);
			port.publishPort();

			doPortConnection(port.getPortURI(), communicationInboundURI,
					CommunicationServicesConnector.class.getCanonicalName());
			communicationConnections.put(address2, port);

			System.out.println("nouvelle connexion établie : " + address1 + " -> " + address2);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param m le message que l'on souhaite envoyer.
	 */
	public void routeMessage(MessageI m) {
		int N = 2;
		try {
			if (this.address.equals(m.getAddress())) {
				System.out.println("Un message reçu");
				System.out.println("Message = '" + m.getContent() + "'");
				return;
			}
			for (CommunicationServicesOutBoundPort port : communicationConnections.values()) {
				if (N == 0)
					break;
				port.routeMessage(m);
				N--;
			}
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public synchronized void execute() throws Exception {

		// Connexion au register
		doPortConnection(registerServicesOutboundPort.getPortURI(), RegisterComponent.REGISTER_INBOUND_PORT_URI,
				RegistrationServicesConnector.class.getCanonicalName());

		// Fetch les voisins et leurs infos de connexion
		Set<ConnectionInfo> connectionInfos = registerServicesOutboundPort.registerInternal(address,
				communicationServicesInboundPort.getPortURI(), initialPosition, initialRange,
				communicationServicesInboundPort.getPortURI());

		System.out.println("connexion d'un noeud de taille = " + connectionInfos.size());

		for (ConnectionInfo connectionInfo : connectionInfos) {
			// connexion aux voisins
			connect(this.address, connectionInfo.getAddress(), connectionInfo.getCommunicationInboudPort(),
					connectionInfo.getRoutingInboundPortURI());
			// les voisins se connectent à moi
			connect(connectionInfo.getAddress(), this.address, connectionInfo.getCommunicationInboudPort(),
					connectionInfo.getRoutingInboundPortURI());
		}

		// Envoi d'un message
		if (this.address.equals(new P2PAddress("192.168.25.1"))) {
			Thread.sleep(4000L);
			System.out.println("### routeMessage()");
			Message message = new Message(new P2PAddress("192.168.25.2"), "Hello", 55);
			routeMessage(message);
		}

		super.execute();
	}

	@Override
	public synchronized void finalise() throws Exception {
		Thread.sleep(4000L);
		doPortDisconnection(registerServicesOutboundPort.getPortURI());
		for (CommunicationServicesOutBoundPort port : communicationConnections.values()) {
			doPortDisconnection(port.getPortURI());
		}
		super.finalise();
	}

	@Override
	public synchronized void shutdown() throws ComponentShutdownException {
		try {
			Thread.sleep(1000L);
			registerServicesOutboundPort.unpublishPort();
			communicationServicesInboundPort.unpublishPort();
			for (CommunicationServicesOutBoundPort port : communicationConnections.values())
				port.unpublishPort();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ComponentShutdownException(e);
		}
		super.shutdown();
	}
}
