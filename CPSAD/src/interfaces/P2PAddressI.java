package interfaces;

/**
 * Interface permettant de cr�er des classes de type P2PAddress.
 * @author OBED
 */
public interface P2PAddressI extends AddressI {
	
		default boolean isP2PAddress() {
			return false;
		}

		default boolean isIPAddress() {
			return true;
		}
}

