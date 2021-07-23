package P2PAddress;

import java.io.Serializable;

import interfaces.AddressI;
import interfaces.P2PAddressI;

/**
 * Classe initialisant une addresse r�seau P2P.
 * @author OBED
 */
public class P2PAddress implements P2PAddressI, Serializable {
	/** Num�ro de s�rie pour chaque instance. */
	private static final long serialVersionUID = 1L;
	/** L'addresse elle-m�me. */
	private String address;
	
	/**
	 * Constructeur d'une addresse IP en P2P.
	 * @param address la chaine de caract�re que l'on souhaite donner � l'instance.
	 */
	public P2PAddress(String address) {
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
	public boolean isP2PAddress() {
		return true;
	}

	@Override
	public boolean isIPAddress() {
		return false;
	}

	@Override
	public boolean equals(AddressI address) {
		if(address == null) {
			return false;
		}
		if(!address.isP2PAddress()) return false;
		P2PAddress other = (P2PAddress)  address;
		if(!this.address.equals(other.address)) return false;
		return true;
	}
	
	@Override
	public int hashCode() {
		return getAddress().hashCode();
	}
	
	@Override
	public String toString() {
		return getAddress();
	}

}
