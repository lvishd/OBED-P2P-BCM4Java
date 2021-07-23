package interfaces;

import fr.sorbonne_u.components.interfaces.OfferedCI;
import fr.sorbonne_u.components.interfaces.RequiredCI;

/**
 * Interface pour les classes de type composants de communication.
 * @author OBED
 */
public interface CommunicationCI extends OfferedCI,RequiredCI {
	/**
	 * Connecte le composant avec le noeud de destination.
	 * @param address l'addresse du noeud avec lequel se connecter
	 * @param communicationInboundPortURI le port entrant de communication.
	 * @param routingInboundPortURI le port entrant du routeur. 
	 * @throws Exception une exception.
	 */
	void connect (P2PAddressI address, String communicationInboundPortURI, String routingInboundPortURI) throws Exception;
	/**
	 * Permet de router le message.
	 * @param m le message.
	 * @throws Exception une exception.
	 */
	void routeMessage(MessageI m) throws Exception;
	
	/**
	 * Fait un ping.
	 * @throws Exception une exception.
	 */
	void ping() throws Exception;
}
