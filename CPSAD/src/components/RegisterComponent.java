package components;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.annotations.OfferedInterfaces;
import fr.sorbonne_u.components.exceptions.ComponentShutdownException;

import connectors.ConnectionInfo;
import interfaces.P2PAddressI;
import interfaces.PositionI;
import interfaces.RegistrationCI;
import node.Node;
import ports.RegisterServicesInboundPort;

/**
 * Classe composant de Register (le simulateur).
 * @author OBED
 */
@OfferedInterfaces(offered = { RegistrationCI.class })
public class RegisterComponent extends AbstractComponent {
	/** Le port entrant du simulateur. */
	protected RegisterServicesInboundPort registerServicesInboundPort;
	/** L'URI unique du port entrant du simulateur. */
	public static String REGISTER_INBOUND_PORT_URI;
	/** Collection de tous les noeuds internes. */
	private Map<P2PAddressI, Node> internalNodesTable;
	/** Collection de tous les points d'accès (externes). */
	private Map<P2PAddressI, Node> accessPointsNodesTable;

	/**
	 * Constructeur d'un composant simulateur.
	 */
	protected RegisterComponent() {
		super(1, 0);
		try {
			registerServicesInboundPort = new RegisterServicesInboundPort(this);
			REGISTER_INBOUND_PORT_URI = registerServicesInboundPort.getPortURI();
			registerServicesInboundPort.publishPort();
			internalNodesTable = new HashMap<>();
			accessPointsNodesTable = new HashMap<>();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Permet d'enregistrer tous les noeuds voisins à une portée donnée.
	 * @param address l'addresse du noeud dont on veut les voisins.
	 * @param initialPosition la position initiale.
	 * @param initialRange la portée initiale.
	 * @param table
	 * @return la collection de tous les noeuds voisins à une portée donnée.
	 */
	Set<ConnectionInfo> getNeighbours(P2PAddressI address, PositionI initialPosition, double initialRange) {

		Set<ConnectionInfo> connectionInfos = new HashSet<>();
			System.out.println("### appel de getNeighbours par " + address.toString());
			for (P2PAddressI adr : internalNodesTable.keySet()) {
				if (address.equals(adr)) continue;
				
				Node current = internalNodesTable.get(adr);
				if (current.getInitialPosition().distance(initialPosition) <= initialRange) {
					connectionInfos.add(Node.getConnectionInfo(adr, current));
				}
			}
			return connectionInfos;
	}

	@Override
	public synchronized void shutdown() throws ComponentShutdownException {
		try {
			registerServicesInboundPort.unpublishPort();
		} catch (Exception e) {
			throw new ComponentShutdownException(e);
		}
	}

	@Override
	public synchronized void execute() throws Exception {
		super.execute();
		System.out.println("Register lancé ");
	}

	/**
	 * Permet d'enregistrer un noeud interne.
	 * @param address l'addresse que l'on souhaite enregistrer.
	 * @param communicationInboundPortURI le port entrant de communication.
	 * @param initialPosition la position initiale.
	 * @param initialRange la portée initiale.
	 * @return une collection de tous les nouveaux noeuds voisins qui peuvent router avec nous même.
	 */
	public Set<ConnectionInfo> registerInternal(P2PAddressI address, String communicationInboundPortURI,
			PositionI initialPosition, double initialRange) {

		internalNodesTable.put(address, new Node(initialPosition, communicationInboundPortURI));
		System.out.println("Register avec un nouveau noeud d'addresse IP = " + address);

		Set<ConnectionInfo> neighbors = getNeighbours(address, initialPosition, initialRange);
		System.out.println("taille internalNodesTable = " + internalNodesTable.size());
		return neighbors;
	}

	/**
	 * Permet d'enregistrer un point d'accès.
	 * @param address l'addresse que l'on souhaite enregistrer.
	 * @param communicationInboundPortURI entrant de communication.
	 * @param initialPosition la position initiale.
	 * @param initialRange la portée initiale.
	 * @param routingInboundPortURI l'URI du port entrant du router.
	 * @return une collection de tous les nouveaux noeuds voisins qui peuvent router avec nous même.
	 */
	public Set<ConnectionInfo> registerAccessPoint(P2PAddressI address, String communicationInboundPortURI,
			PositionI initialPosition, double initialRange, String routingInboundPortURI) {
		System.out.println("Register avec un nouvel Access Point d'addresse IP = " + address);
		Set<ConnectionInfo> neighbors = getNeighbours(address, initialPosition, Double.POSITIVE_INFINITY);
		accessPointsNodesTable.put(address,
				new Node(initialPosition, communicationInboundPortURI, routingInboundPortURI));
		System.out.println("taille de la table access points courante : " + accessPointsNodesTable.size());
		return neighbors;
	}

	/**
	 * Permet de désenregister une address dans le simulateur.
	 * @param address l'address que l'on souhaite retirer.
	 */
	public void unregister(P2PAddressI address) {
		internalNodesTable.remove(address);
		accessPointsNodesTable.remove(address);
		System.out.println("Un noeud avec l'addresse IP = " + address + " a été unregistered");
	}
}
