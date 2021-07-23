package interfaces;

import java.util.Set;

import fr.sorbonne_u.components.interfaces.OfferedCI;
import fr.sorbonne_u.components.interfaces.RequiredCI;

import route.RouteInfo;

/**
 * Interface permettant d'aider � cr�er une classe de type composant dans le r�seau.
 * @author OBED
 */
public interface RoutingManagementCI extends OfferedCI,RequiredCI {
	/**
	 * Mets � jour le routing de la route du voisin et les informations.
	 * @param neighbour l'addresse du noeud voisin.
	 * @param routes la liste des routes et les informations qui leurs appartiennent. 
	 * @throws Exception une exception.
	 */
	void updateRouting(P2PAddressI neighbour, Set<RouteInfo> routes) throws Exception;
	/**
	 * Mets � jour le point d'acc�s courrant du voisin.
	 * @param neighbour l'addresse du noeud voisin.
	 * @param numberOfHops le nombre d'�tapes.
	 * @throws Exception une exception.
	 */
	void updateAccessPoint(P2PAddressI neighbour, int numberOfHops) throws Exception;
}
