package interfaces;

import java.util.Set;

import fr.sorbonne_u.components.interfaces.OfferedCI;
import fr.sorbonne_u.components.interfaces.RequiredCI;

/**
 * Interface permettant de cr�er une classe type Registration.
 * @author OBED
 */
public interface RegistrationCI extends OfferedCI, RequiredCI{
	/**
	 * Permet d'enregistrer un noeud interne.
	 * @param address l'addresse que l'on souhaite enregistrer.
	 * @param communicationInboundPort le port entrant de communication.
	 * @param initialPosition la position initiale.
	 * @param initialRange la port�e initiale.
	 * @param routingInboundPortURI l'URI du port entrant du router.
	 * @return une collection de tous les nouveaux noeuds voisins qui peuvent router avec nous m�me.
	 * @throws Exception une exception.
	 */
	Set<connectors.ConnectionInfo> registerInternal(P2PAddressI address, 
					String communicationInboundPort,PositionI initialPosition, 
					double initialRange,String routingInboundPortURI) throws Exception;


	/**
	 * Permet d'enregistrer un point d'acc�s.
	 * @param address l'addresse que l'on souhaite enregistrer.
	 * @param communicationInboundPort le port entrant de communication.
	 * @param initialPosition la position initiale.
	 * @param initialRange la port�e initiale.
	 * @param routingInboundPortURI l'URI du port entrant du router.
	 * @return une collection de tous les nouveaux noeuds voisins qui peuvent router avec nous m�me.
	 * @throws Exception une exception.
	 */
	Set<connectors.ConnectionInfo> registerAccessPoint(P2PAddressI address, 
					String communicationInboundPort, 
					PositionI initialPosition, 
					double initialRange,  String routingInboundPortURI) throws Exception;

	/**
	 * Permet de d�senregister une address dans le simulateur.
	 * @param address l'address que l'on souhaite retirer.
	 * @throws Exception une exception.
	 */
	void unregister(P2PAddressI address) throws Exception;
}