package connectors;

import java.util.Set;

import interfaces.P2PAddressI;
import interfaces.RouteInfo;
import interfaces.RoutingManagementCI;

public class RoutingServicesConnector implements RoutingManagementCI {

	@Override
	public void updateRouting(P2PAddressI neighbour, Set<RouteInfo> routes) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateAccessPoint(P2PAddressI neighbour, int numberOfHops) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
