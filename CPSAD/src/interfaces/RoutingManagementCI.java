package interfaces;

import java.util.Set;

import fr.sorbonne_u.components.interfaces.OfferedCI;
import fr.sorbonne_u.components.interfaces.RequiredCI;
import route.RouteInfo;

public interface RoutingManagementCI extends OfferedCI,RequiredCI{
	void updateRouting(P2PAddressI neighbour, 
						Set<RouteInfo> set) throws Exception;
	void updateAccessPoint(P2PAddressI neighbour, int numberOfHops) throws Exception;

	

}
