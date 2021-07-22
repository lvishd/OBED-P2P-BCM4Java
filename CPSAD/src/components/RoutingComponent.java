package components;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

//import IPAddress.IPAddress;
import connectors.CommunicationServicesConnector;
import connectors.ConnectionInfo;
import connectors.RegistrationServicesConnector;
import connectors.RoutingServicesConnector;

import java.util.Map.Entry;

import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.annotations.OfferedInterfaces;
import fr.sorbonne_u.components.annotations.RequiredInterfaces;
import fr.sorbonne_u.components.exceptions.ComponentShutdownException;
import interfaces.RegistrationCI;
import interfaces.RoutingManagementCI;
import message.Message;
import ports.CommunicationServicesInboundPort;
import ports.CommunicationServicesOutBoundPort;
import ports.RegisterServicesOutboundPort;
import ports.RoutingServicesInboundPort;
import ports.RoutingServicesOutboundPort;
import position.Position;
import route.RouteInfo;
import interfaces.AddressI;
import interfaces.CommunicationCI;
import interfaces.IPAddressI;
import interfaces.MessageI;
import interfaces.P2PAddressI;
import interfaces.PositionI;
import P2PAddress.P2PAddress;

@RequiredInterfaces(required = { RegistrationCI.class, CommunicationCI.class, RoutingManagementCI.class })
@OfferedInterfaces(offered = { CommunicationCI.class, RoutingManagementCI.class })
public class RoutingComponent extends AbstractComponent {
		private CommunicationServicesInboundPort communicationServicesInboundPort;
		private RegisterServicesOutboundPort registerServicesOutboundPort;
		private RoutingServicesInboundPort routingServicesInboundPort;
		
		private Map<P2PAddressI, CommunicationServicesOutBoundPort> communicationConnectionPorts;
		private Map<P2PAddressI, RoutingServicesOutboundPort> routingServicesOutboundPorts;
		private Map<P2PAddressI, Set<RouteInfo>> routes;
		private Map<P2PAddressI, Integer> accessPointsMap;
		private P2PAddressI address;
		private PositionI initialPosition;
		private double initialRange;
		private int executorIndex;

		
		protected RoutingComponent(P2PAddressI address, PositionI initiaPosition, double initialRange)
				throws Exception {
			super(1, 0);
			this.setAddress(address);
			this.initialPosition = initiaPosition;
			this.initialRange = initialRange;

			this.routes = new HashMap<>();
			this.accessPointsMap = new HashMap<>();
			this.communicationConnectionPorts = new HashMap<>();
			this.routingServicesOutboundPorts = new HashMap<>();

			this.communicationServicesInboundPort = new CommunicationServicesInboundPort(this);
			this.registerServicesOutboundPort = new RegisterServicesOutboundPort(this);
			this.routingServicesInboundPort = new RoutingServicesInboundPort(this);

			this.communicationServicesInboundPort.publishPort();
			this.registerServicesOutboundPort.publishPort();
			this.routingServicesInboundPort.publishPort();
			this.executorIndex = createNewExecutorService("ROUTING NODE EXECUTOR SERVICE", 1, false);
		}
		
		protected RoutingComponent() throws Exception {
			super(1, 0);

			this.address = new P2PAddress("some ip");
			this.initialPosition = new Position(12, 23);
			this.initialRange = 120;
			this.routes = new HashMap<>();
			this.accessPointsMap = new HashMap<>();
			this.communicationConnectionPorts = new HashMap<>();
			this.routingServicesOutboundPorts = new HashMap<>();

			this.communicationServicesInboundPort = new CommunicationServicesInboundPort(this);
			this.registerServicesOutboundPort = new RegisterServicesOutboundPort(this);
			this.routingServicesInboundPort = new RoutingServicesInboundPort(this);

			this.communicationServicesInboundPort.publishPort();
			this.registerServicesOutboundPort.publishPort();
			this.routingServicesInboundPort.publishPort();
		}

		@Override
		public synchronized void execute() throws Exception {

			doPortConnection(registerServicesOutboundPort.getPortURI(), RegisterComponent.REGISTER_INBOUND_PORT_URI,
					RegistrationServicesConnector.class.getCanonicalName());
			
			Set<ConnectionInfo> connectionInfos = registerServicesOutboundPort.registerAccessPoint(address,
					communicationServicesInboundPort.getPortURI(), initialPosition, initialRange,
					routingServicesInboundPort.getPortURI());
			
			System.out.println("ROUTING NODE CONNECTIONS = " + connectionInfos.size());

			for (ConnectionInfo connectionInfo : connectionInfos) {
				this.connectRouting(connectionInfo.getAddress(), connectionInfo.getCommunicationInboudPort(),
						connectionInfo.getRoutingInboundPortURI());
			}
			getExecutorService(executorIndex).execute(() -> {
				while(true) {
					try {
						
						for(RoutingServicesOutboundPort neighboroOutboundPort: routingServicesOutboundPorts.values()) {
							for(Entry<P2PAddressI, Set<RouteInfo>> entry : routes.entrySet()) {
								neighboroOutboundPort.updateRouting(entry.getKey(), entry.getValue());
							}
							
							for(Entry<P2PAddressI, Integer> entry : accessPointsMap.entrySet()) {
								neighboroOutboundPort.updateAccessPoint(entry.getKey(), entry.getValue() + 1);
							}
							
						}
						
						
						
					} catch (InterruptedException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
	/*
			registerServicesOutboundPort.unregister(P2PAddress);
			registerServicesOutboundPort.unregister(address);
	*/
			
			//uncomment this to test message sending
	
			Message message = new Message(new P2PAddress("192.168.25.2"), "Hello", 5 );
			//Message message1 = new Message(new P2PAddress("192.168.25.1"), "Hello", 5 );
			//Message message11 = new Message((AddressI) new IPAddress("192.168.25.6"), "Hello", 5 );
			//routeMessage(message11);
			//routeMessage(message1);
			routeMessage(message);
			super.execute();
	
		}

		@Override
		public synchronized void shutdown() throws ComponentShutdownException {
			try {
				registerServicesOutboundPort.unpublishPort();
				communicationServicesInboundPort.unpublishPort();
				routingServicesInboundPort.unpublishPort();
				for (CommunicationServicesOutBoundPort port : communicationConnectionPorts.values())
					port.unpublishPort();
				for (RoutingServicesOutboundPort port : routingServicesOutboundPorts.values())
					port.unpublishPort();

			} catch (Exception e) {
				throw new ComponentShutdownException(e);
			}
			super.shutdown();
		}

		@Override
		public synchronized void finalise() throws Exception {
			doPortDisconnection(registerServicesOutboundPort.getPortURI());
			for (CommunicationServicesOutBoundPort port : communicationConnectionPorts.values())
				doPortDisconnection(port.getPortURI());
			for (RoutingServicesOutboundPort routing : routingServicesOutboundPorts.values())
				doPortDisconnection(routing.getPortURI());
			super.finalise();
		}


		
		void connect(P2PAddressI address, String communicationInboundURI) {


			System.out.println("FROM ROUTING NODE: CONNECT METHOD IS INVOKED" + address.toString());
			try {
				if (communicationConnectionPorts.containsKey(address))
					return;

				// we create a new outbound port for the new neighbor
				CommunicationServicesOutBoundPort port = new CommunicationServicesOutBoundPort(this);
				port.publishPort();

				doPortConnection(port.getPortURI(), communicationInboundURI,
						CommunicationServicesConnector.class.getCanonicalName());
				communicationConnectionPorts.put(address, port);
				System.out.println("ROUTING NODE: A NEW CONNECTION WAS ESTABLISHED WITH A TERMINAL NODE " + address);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		
		void connectRouting(P2PAddressI address, String communicationServicesInboundPortURI, String routingInboudPortURI) {
			try {
				if (communicationConnectionPorts.containsKey(address)) {
					return;
				}

				CommunicationServicesOutBoundPort port = new CommunicationServicesOutBoundPort(this);
				port.publishPort();
				RoutingServicesOutboundPort RoutingServicesOutboundPort = new RoutingServicesOutboundPort(this);
				RoutingServicesOutboundPort.publishPort();

				doPortConnection(RoutingServicesOutboundPort.getPortURI(), routingInboudPortURI,
						RoutingServicesConnector.class.getCanonicalName());

				doPortConnection(port.getPortURI(), communicationServicesInboundPortURI,
						CommunicationServicesConnector.class.getCanonicalName());

				communicationConnectionPorts.put(address, port);
				routingServicesOutboundPorts.put(address, RoutingServicesOutboundPort);
				System.out
						.println("ROUTING NODE: A NEW CONNECTION WAS ESTABLISHED WITH A ROUTING NODE " + address);
				Set<RouteInfo> routeInfos = new HashSet<>();
				RouteInfo info = new RouteInfo(address, 1);
				routeInfos.add(info);
				routes.put(address, routeInfos);
				System.out.println("ROUTING NODE: total neighbors  = " + communicationConnectionPorts.size()
				+ ", routing neighbors = " + routes.size() + ", other access points = " + accessPointsMap.size());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		
		P2PAddressI getClosestAccessPoint() {
			int min = Integer.MAX_VALUE;
			P2PAddressI closestAddress = null;
			for (Entry<P2PAddressI, Integer> entry : accessPointsMap.entrySet()) {
				if (entry.getValue() > min) {
					min = entry.getValue();
					closestAddress = entry.getKey();
				}
			}
			return closestAddress;
		}

		
		void routeMessage(MessageI m) {
			System.out.println("FROM TRANSMIT MESSAGE IN ROUTING NODE");
			if (!m.stillAlive()) {
				System.out.println("Message is dead");
				return;
			}

			if (address.equals(m.getAddress())) {
				System.out.println("MESSAGE IS RECEIVED IN ROUTING NODE");
				return;
			}
			try {
				
				if(m.getAddress() instanceof IPAddressI) {
					System.out.println("ROUTING NODE HAS RECEIVED A NETWORK ADDRESS, REDIRECTING TO AN ACCESS POINT");
					P2PAddressI closestAccessPoint = getClosestAccessPoint();
					if (closestAccessPoint == null) {
						System.err.println("NO ACCESS POINT TO BE FOUND!");
						return;
					}
					communicationConnectionPorts.get(closestAccessPoint).routeMessage(m);
					
				}
				
				CommunicationServicesOutBoundPort sendingPort = communicationConnectionPorts.get(m.getAddress());
				m.decrementHops();
				if(sendingPort != null) {
					System.out.println("ROUTING NODE HAS AN ENTRY FOR THE DISTINATION");
					sendingPort.routeMessage(m);
					
				}else {
					System.out.println("ROUTING NODE PROCEEDING WITH MESSAGE PROPAGATION");
					
					 int n = 3;
					 for(CommunicationServicesOutBoundPort port: communicationConnectionPorts.values()) {
						 if (n==0) return;
						 port.routeMessage(m);
						 n--;
					 }
				}
			}catch (Exception e) {
				e.printStackTrace();
			}		
		}

		
		int hasRouteFor(AddressI address) {
			System.err.println(communicationConnectionPorts.containsKey(address));
			if (communicationConnectionPorts.containsKey(address))
				return 1;
			else
				return -1;

		}

		void ping() {

		}

		
		public void updateRouting(P2PAddressI address, Set<RouteInfo> routes) {
			System.out.println("A NEW ENTRY WAS RECEIVED----ROUTING NODE-----: UPDATE ROUTING");
			if (!this.routes.containsKey(address))
				this.routes.put(address, routes);
		
			Set<RouteInfo> currentInfos = this.routes.get(address);
			currentInfos.addAll(routes);
			this.routes.put(address, currentInfos);
		}
		
		
		public void updateAccessPoint(P2PAddressI address, int numberOfHops) {
			System.out.println("A NEW ENTRY WAS RECEIVED----ROUTING NODE-----: UPDATE ACCESS POINT");
			if (!accessPointsMap.containsKey(address))
				accessPointsMap.put(address, numberOfHops);
			if (accessPointsMap.get(address) > numberOfHops)
				accessPointsMap.put(address, numberOfHops);
		}

		public P2PAddressI getAddress() {
			return address;
		}

		public void setAddress(P2PAddressI address) {
			this.address = address;
		}
	}
