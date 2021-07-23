package interfaces;

/**
 * Interface pour les classes du type Address.
 * @author OBED
 */
public interface AddressI {
	/**
	 * Teste le type d'addresse de l'instance.
	 * @return true si l'addresse est une addresse P2P, false sinon.
	 */
	boolean isP2PAddress();
	/**
	 * Teste le type d'addresse de l'instance.
	 * @return true si l'addresse est une addresse IP, false sinon.
	 */
	boolean isIPAddress();
	/**
	 * Méthode equals basique vérifiant l'égalité entre deux addresses.
	 * @param address l'addresse avec laquelle l'addresse courrante souhaite vérifier l'égalité.
	 * @return true si tous leurs attributs sont égaux, false sinon.
	 */
	boolean equals(AddressI address);
}
