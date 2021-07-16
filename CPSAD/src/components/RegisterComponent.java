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
import nodeTable.NodeTable;
import ports.RegisterServicesInboundPort;


@OfferedInterfaces(offered = { RegistrationCI.class })
public class RegisterComponent extends AbstractComponent {
	protected RegisterServicesInboundPort registerPort;
	public static String REGISTER_INBOUND_PORT_URI;

	private Map<P2PAddressI, NodeTable> internalNodesTable;
	private Map<P2PAddressI, NodeTable> accessPointsNodesTable;

	
	protected RegisterComponent() {
		super(10, 0);
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

	
	Set<ConnectionInfo> getNeighbours(P2PAddressI address, PositionI initialPosition, double initialRange, int table) {

		Set<ConnectionInfo> connectionInfos = new HashSet<>();
		switch (table) {
		case 0:
			for (P2PAddressI adr : internalNodesTable.keySet()) {
				NodeTable current = internalNodesTable.get(adr);
				if (current.getInitialPosition().distance(initialPosition) <= initialRange) {
					connectionInfos.add(NodeTable.getConnectionInfo(adr, current));
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

	
	Set<ConnectionInfo> registerInternal(P2PAddressI address, String communicationInboundPortURI,
			PositionI initialPosition, double initialRange) {
		System.out.println("Register avec un nouveau noeud d'addresse IP = " + address);
		Set<ConnectionInfo> neighbores = getNeighbours(address, initialPosition, initialRange, 1);
		neighbores.addAll(getNeighbours(address, initialPosition, initialRange, 2));
		internalNodesTable.put(address,
				new NodeTable(communicationInboundPortURI, initialPosition));
		System.out.println("taille des noeuds internes courrantes = " + internalNodesTable.size());
		return neighbores;
	}
	
	Set<ConnectionInfo> registerAccessPoint(P2PAddressI address, String communicationInboundPortURI,
			PositionI initialPosition, double initialRange, String routingInboundPortURI) {
		System.out.println("Register avec un nouvel Access Point d'addresse IP = " + address);
		Set<ConnectionInfo> neighbores = getNeighbours(address, initialPosition, Double.POSITIVE_INFINITY, 2);
		neighbores.addAll(getNeighbours(address, initialPosition, initialRange, 1));
		accessPointsNodesTable.put(address, new NodeTable(communicationInboundPortURI,
				initialPosition, routingInboundPortURI));
		System.out.println("taille de la table access points courante : " + accessPointsNodesTable.size());
		return neighbores;
	}

	

	void unregister(P2PAddressI address) {
		internalNodesTable.remove(address);
		accessPointsNodesTable.remove(address);
		System.out.println("Un noeud avec l'addresse IP = " + address + " a été unregistered");
	}
}
