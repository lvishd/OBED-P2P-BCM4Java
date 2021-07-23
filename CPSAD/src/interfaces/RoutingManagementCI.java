package interfaces;

import java.util.Set;

import fr.sorbonne_u.components.interfaces.OfferedCI;
import fr.sorbonne_u.components.interfaces.RequiredCI;

import route.RouteInfo;

/**
 * Interface permettant d'aider à créer une classe de type composant dans le réseau.
 * @author OBED
 */
public interface RoutingManagementCI extends OfferedCI,RequiredCI {
	/**
	 * Mets à jour le routing de la route du voisin et les informations.
	 * @param neighbour l'addresse du noeud voisin.
	 * @param routes la liste des routes et les informations qui leurs appartiennent. 
	 * @throws Exception une exception.
	 */
	void updateRouting(P2PAddressI neighbour, Set<RouteInfo> routes) throws Exception;
	/**
	 * Mets à jour le point d'accès courrant du voisin.
	 * @param neighbour l'addresse du noeud voisin.
	 * @param numberOfHops le nombre d'étapes.
	 * @throws Exception une exception.
	 */
	void updateAccessPoint(P2PAddressI neighbour, int numberOfHops) throws Exception;
}
