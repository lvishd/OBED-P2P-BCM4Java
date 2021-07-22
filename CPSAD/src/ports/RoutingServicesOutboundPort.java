package ports;

import java.util.Set;


import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.ports.AbstractOutboundPort;
import interfaces.P2PAddressI;
import route.RouteInfo;
import interfaces.RoutingManagementCI;

public class RoutingServicesOutboundPort extends AbstractOutboundPort implements RoutingManagementCI{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RoutingServicesOutboundPort(ComponentI owner) throws Exception {
		super(RoutingManagementCI.class, owner);
	}

	@Override
	public void updateRouting(P2PAddressI address, Set<RouteInfo> routes) throws Exception {
		((RoutingManagementCI)getConnector()).updateRouting(address, routes);
		
	}

	@Override
	public void updateAccessPoint(P2PAddressI neighbour, int numberOfHops) throws Exception {
		((RoutingManagementCI)getConnector()).updateAccessPoint(neighbour, numberOfHops);
	}
}