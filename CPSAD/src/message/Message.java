package message;

import java.io.Serializable;

import interfaces.AddressI;
import interfaces.MessageI;

/**
 * Class servant à créer un message.
 * @author OBED
 */
public class Message implements MessageI {
	/** L'addresse du  destinataire. */
	private AddressI address;
	/** Le contenu du message (du texte).  */
	private Serializable content;
	/** Nombre d'étapes. */
	private int numberOfHops;
	
	/**
	 * Constructeur d'un message.
	 * @param address l'address du destinataire.
	 * @param content le contenu du message.
	 * @param numberOfHops le nombre d'étapes.
	 */
	public Message(AddressI address, Serializable content, int numberOfHops) {
		this.address = address;
		this.content = content;
		this.numberOfHops = numberOfHops;
		
	}
	
	@Override
	public AddressI getAddress() {
		return address;
	}

	@Override
	public Serializable getContent() {
		return content;
	}

	@Override
	public boolean stillAlive() {
		return numberOfHops > 0;
	}

	@Override
	public void decrementHops() {
		numberOfHops--;
	}

}
