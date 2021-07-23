package IPAddress;

import java.io.Serializable;

import interfaces.AddressI;
import interfaces.IPAddressI;

/**
 * Classe initialisant une addresse r�seau.
 * @author OBED
 */
public class IPAddress implements IPAddressI, Serializable {
	/** Num�ro de s�rie pour chaque instance. */
	private static final long serialVersionUID = 1L;
	/** L'addresse elle-m�me. */
	private String address;
	
	/**
	 * Constructeur d'une addresse IP standard.
	 * @param address la chaine de caract�re que l'on souhaite donner � l'instance.
	 */
	public IPAddress(String address) {
		this.address = address;
	}
	
	/**
	 * Permet d'obtenir la chaine de caract�re de l'addresse depuis les autres classes.
	 * @return la chaine de caract�re de l'addresse.
	 */
	public String getAddress() {
		return address;
	}

	@Override
	public boolean isP2PAddress()  {
		return false;
	}

	@Override
	public boolean isIPAddress() {
		return true;
	}

	@Override
	public boolean equals(AddressI address) {
		if (!address.isIPAddress()) return false;
		IPAddress other = (IPAddress) address;
		if (!this.address.equals(other.address)) return false;
		return true;
	}
}
