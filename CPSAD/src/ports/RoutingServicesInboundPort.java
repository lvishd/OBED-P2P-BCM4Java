package ports;

import java.util.Set;


import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.ports.AbstractInboundPort;
import interfaces.P2PAddressI;
import interfaces.RoutingManagementCI;
import route.RouteInfo;
import components.RoutingComponent;


public class RoutingServicesInboundPort extends AbstractInboundPort implements RoutingManagementCI {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public RoutingServicesInboundPort(ComponentI owner) throws Exception{
		super(RoutingManagementCI.class, owner);
	}
	
	public RoutingServicesInboundPort(String uri, ComponentI owner)
			throws Exception {
		super(uri, RoutingManagementCI.class, owner);
	}

	@Override
	public void updateRouting(P2PAddressI neighbour, Set<RouteInfo> set) throws Exception {
		// TODO Auto-generated method stub
		getOwner().handleRequest(c->{
			((RoutingComponent)c).updateRouting(neighbour, set);
			return null;
		});
	}

	@Override
	public void updateAccessPoint(P2PAddressI neighbour, int numberOfHops) throws Exception {
		getOwner().handleRequest(c->{
			((RoutingComponent)c).updateAccessPoint(neighbour, numberOfHops);
			return null;
		});		
	}
		
	
}
