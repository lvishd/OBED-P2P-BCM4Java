package interfaces;

/**
 * Interface permettant de créer des classes de type IPAddress.
 * @author OBED
 */
public interface IPAddressI extends AddressI {
		
       default boolean isP2PAddress() {
			return false;
		}

		default boolean isIPAddress() {
			return true;
		}
}
