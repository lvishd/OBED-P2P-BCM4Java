package connectors;

import java.util.Set;

import fr.sorbonne_u.components.connectors.AbstractConnector;
import interfaces.P2PAddressI;
import route.RouteInfo;
import interfaces.RoutingManagementCI;

public class RoutingServicesConnector extends AbstractConnector implements RoutingManagementCI {
	
	public RoutingServicesConnector() {
		super();
	}

	
	@Override
	public void updateRouting(P2PAddressI neighbour, Set<RouteInfo> set) throws Exception {
		((RoutingManagementCI) offering).updateRouting(neighbour, set);
	}


	@Override
	public void updateAccessPoint(P2PAddressI neighbour, int numberOfHops) throws Exception {
		((RoutingManagementCI) offering).updateAccessPoint(neighbour, numberOfHops);

	}

}
