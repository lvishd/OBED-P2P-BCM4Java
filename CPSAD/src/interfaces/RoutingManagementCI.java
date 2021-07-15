package interfaces;

import java.util.Set;

public interface RoutingManagementCI {
	void updateRouting(P2PAddressI neighbour, 
						Set<RouteInfo> routes) throws Exception;
	void updateAccessPoint(P2PAddressI neighbour, int numberOfHops) throws Exception;

	

}
