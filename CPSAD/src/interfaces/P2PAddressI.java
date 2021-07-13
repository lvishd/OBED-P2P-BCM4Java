package interfaces;

public interface P2PAddressI extends AddressI{
	
		default boolean isP2PAddress() {
			return false;
		}

		default boolean isIPAddress() {
			return true;
		}

}

