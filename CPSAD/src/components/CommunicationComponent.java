package components;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import connectors.CommunicationServicesConnector;
import connectors.ConnectionInfo;
import connectors.RegistrationServicesConnector;

import java.util.Map.Entry;


import P2PAddress.P2PAddress;
import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.annotations.OfferedInterfaces;
import fr.sorbonne_u.components.annotations.RequiredInterfaces;
import fr.sorbonne_u.components.exceptions.ComponentShutdownException;
import interfaces.AddressI;
import interfaces.CommunicationCI;
import interfaces.MessageI;
import interfaces.RegistrationCI;
import message.Message;
import interfaces.P2PAddressI;
import interfaces.PositionI;
import ports.CommunicationServicesInboundPort;
import ports.CommunicationServicesOutBoundPort;
import ports.RegisterServicesOutboundPort;
import position.Position;

@RequiredInterfaces(required = { RegistrationCI.class, CommunicationCI.class })
@OfferedInterfaces(offered = { CommunicationCI.class })
public class CommunicationComponent extends AbstractComponent{

	protected CommunicationComponent(int nbThreads, int nbSchedulableThreads) {
		super(nbThreads, nbSchedulableThreads);
		// TODO Auto-generated constructor stub
	}
	protected CommunicationServicesInboundPort communicationServicesInboundPort;
	protected RegisterServicesOutboundPort registerServicesOutboundPort;
	private P2PAddressI address;
	private PositionI initialPosition;
	private double initialRange;
	private Map<P2PAddressI, CommunicationServicesOutBoundPort> communicationConnections;

	private P2PAddressI sendingAddressI = null;

	
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

	
	protected CommunicationComponent() {
		super(10, 0);

		this.address = new P2PAddress("Some IP");
		this.initialPosition = new Position(1, 2);
		this.initialRange = 200;
		this.communicationConnections = new HashMap<>();
		try {
			this.registerServicesOutboundPort = new RegisterServicesOutboundPort(this);
			this.communicationServicesInboundPort = new CommunicationServicesInboundPort(this);
			this.registerServicesOutboundPort.publishPort();
			this.communicationServicesInboundPort.publishPort();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		}
	}


	public void connect(P2PAddressI address, String communicationInboundURI, String routingInboundPortURI) {
		try {
			if (communicationConnections.containsKey(address))
				return;
			CommunicationServicesOutBoundPort port = new CommunicationServicesOutBoundPort(this);
			port.publishPort();

			doPortConnection(port.getPortURI(), communicationInboundURI,
					CommunicationServicesConnector.class.getCanonicalName());
			
			communicationConnections.put(address, port);
			port.connect(this.address,communicationServicesInboundPort.getPortURI(),routingInboundPortURI);
			System.out.println("nouvelle connection établie : " + address);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	
	public void routeMessage(MessageI m) {
		int N = 3;
		try {
			if (this.address.equals(m.getAddress())) {
				System.out.println("Un message reçu");
				System.out.println("Message = '" + m.getContent()+"'");
				return;
			}

			int route = hasRouteFor(m.getAddress());
			if (route != -1) {
				System.out.println("Taille route " + route);
				System.out.println("L'addresse du déstinataire est " + sendingAddressI);
				communicationConnections.get(sendingAddressI).routeMessage(m);

			} else {
				for (CommunicationServicesOutBoundPort port : communicationConnections.values()) {
					if (N == 0)
						break;
					port.routeMessage(m);
					N--;
				}
				System.out.println("aucune route detecté, éteindre");
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int hasRouteFor(AddressI address) {
		if (this.address.equals(address))
			return 0;
		int min = Integer.MAX_VALUE;
		int counter = 0;
		int current;
		try {

			for (Entry<P2PAddressI, CommunicationServicesOutBoundPort> entry : communicationConnections.entrySet()) {
				current = entry.getValue().hasRouteFor(address);
				if (current == -1)
					counter++;
				if (current < min) {
					sendingAddressI = entry.getKey();
					min = current;
				}
			}
			return counter == communicationConnections.size() ? -1 : min + 1;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}

	}

	void ping() {

	}

	@Override
	public synchronized void execute() throws Exception {

		doPortConnection(registerServicesOutboundPort.getPortURI(), RegisterComponent.REGISTER_INBOUND_PORT_URI,
				RegistrationServicesConnector.class.getCanonicalName());
		
		Thread.sleep(3000L);
		Set<ConnectionInfo> connectionInfos = registerServicesOutboundPort.registerInternal(address,
				communicationServicesInboundPort.getPortURI(), initialPosition, initialRange, reflectionInboundPortURI);

		System.out.println("connection d'un noeud de taille = " + connectionInfos.size());
		for (ConnectionInfo connectionInfo : connectionInfos) {
			connect(connectionInfo.getAddress(), connectionInfo.getCommunicationInboudPort(),connectionInfo.getRoutingInboundPortURI());

		}

		//registerServicesOutboundPort.unregister(address);
		
		Message message = new Message(new P2PAddress("192.168.25.1"), "Hello", 55);
		Message message1 = new Message(new P2PAddress("192.168.25.2"), "Hi", 5 );
		routeMessage(message1);
		routeMessage(message);
		super.execute();

	}

	@Override
	public synchronized void finalise() throws Exception {
		doPortDisconnection(registerServicesOutboundPort.getPortURI());
		for (CommunicationServicesOutBoundPort port : communicationConnections.values()) {
			doPortDisconnection(port.getPortURI());
		}
		super.finalise();
	}

	@Override
	public synchronized void shutdown() throws ComponentShutdownException {

		try {
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
