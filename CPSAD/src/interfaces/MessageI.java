package interfaces;

import java.io.Serializable;

/**
 * Interface permettant de créer des messages de type
 * @author OBED
 */
public interface MessageI {
	/**
	 * Permet d'obtenir l'addresse de destination du message depuis les autres classes.
	 * @return l'addresse de destination.
	 */
	AddressI getAddress();
	/**
	 * Permet d'obtenir le contenu du message depuis les autres classes.
	 * @return le contenu du message.
	 */
	Serializable getContent();
	/**
	 * Vérifie si le messsage est toujours en cours d'envoi.
	 * @return true si le message est toujours en cours d'envoi, false sinon.
	 */
	boolean stillAlive();
	/**
	 * Décremente le nombre d'étapes à parcourir.
	 */
	void decrementHops();
}
