package interfaces;

public interface IPAddressI {
	
       default boolean isP2PAddress() {
			return false;
		}

		default boolean isIPAddress() {
			return true;
		}
}
