package components;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import connectors.ConnectionInfo;
import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.annotations.OfferedInterfaces;
import fr.sorbonne_u.components.exceptions.ComponentShutdownException;
import interfaces.P2PAddressI;
import interfaces.PositionI;
import interfaces.RegistrationCI;
import node.Node;
import ports.RegisterServicesInboundPort;

@OfferedInterfaces(offered = { RegistrationCI.class })
public class RegisterComponent extends AbstractComponent {
	/** */
	protected RegisterServicesInboundPort registerPort;
	/** */
	public static String REGISTER_INBOUND_PORT_URI;
	/** */
	private Map<P2PAddressI, Node> internalNodesTable;
	/** */
	private Map<P2PAddressI, Node> accessPointsNodesTable;

	/**
	 * 
	 */
	protected RegisterComponent() {
		super(1, 0);
		try {
			registerPort = new RegisterServicesInboundPort(this);
			REGISTER_INBOUND_PORT_URI = registerPort.getPortURI();
			registerPort.publishPort();
			internalNodesTable = new HashMap<>();
			accessPointsNodesTable = new HashMap<>();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param address
	 * @param initialPosition
	 * @param initialRange
	 * @param table
	 * @return
	 */
	Set<ConnectionInfo> getNeighbours(P2PAddressI address, PositionI initialPosition, double initialRange, int table) {

		Set<ConnectionInfo> connectionInfos = new HashSet<>();
		switch (table) {
		case 0:
			System.out.println("### appel de getNeighbours par " + address.toString());
			for (P2PAddressI adr : internalNodesTable.keySet()) {
				if (address.equals(adr)) continue;
				
				Node current = internalNodesTable.get(adr);
				if (current.getInitialPosition().distance(initialPosition) <= initialRange) {
					connectionInfos.add(Node.getConnectionInfo(adr, current));
				}
			}
			return connectionInfos;
		default:
			return new HashSet<ConnectionInfo>();
		}

	}

	@Override
	public synchronized void shutdown() throws ComponentShutdownException {
		try {
			registerPort.unpublishPort();
		} catch (Exception e) {
			throw new ComponentShutdownException(e);
		}
	}

	@Override
	public synchronized void execute() throws Exception {
		super.execute();
		System.out.println("Register lancé ");
	}

	public Set<ConnectionInfo> registerInternal(P2PAddressI address, String communicationInboundPortURI,
			PositionI initialPosition, double initialRange) {

		internalNodesTable.put(address, new Node(initialPosition, communicationInboundPortURI));
		System.out.println("Register avec un nouveau noeud d'addresse IP = " + address);

		Set<ConnectionInfo> neighbors = getNeighbours(address, initialPosition, initialRange, 0);
		System.out.println("taille internalNodesTable = " + internalNodesTable.size());
		return neighbors;
	}

	public Set<ConnectionInfo> registerAccessPoint(P2PAddressI address, String communicationInboundPortURI,
			PositionI initialPosition, double initialRange, String routingInboundPortURI) {
		System.out.println("Register avec un nouvel Access Point d'addresse IP = " + address);
		Set<ConnectionInfo> neighbors = getNeighbours(address, initialPosition, Double.POSITIVE_INFINITY, 2);
		accessPointsNodesTable.put(address,
				new Node(initialPosition, communicationInboundPortURI, routingInboundPortURI));
		System.out.println("taille de la table access points courante : " + accessPointsNodesTable.size());
		return neighbors;
	}

	void unregister(P2PAddressI address) {
		internalNodesTable.remove(address);
		accessPointsNodesTable.remove(address);
		System.out.println("Un noeud avec l'addresse IP = " + address + " a été unregistered");
	}
}
