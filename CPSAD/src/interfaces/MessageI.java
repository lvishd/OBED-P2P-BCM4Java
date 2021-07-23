package interfaces;

import java.io.Serializable;

/**
 * Interface permettant de cr�er des messages de type
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
	 * V�rifie si le messsage est toujours en cours d'envoi.
	 * @return true si le message est toujours en cours d'envoi, false sinon.
	 */
	boolean stillAlive();
	/**
	 * D�cremente le nombre d'�tapes � parcourir.
	 */
	void decrementHops();
}
