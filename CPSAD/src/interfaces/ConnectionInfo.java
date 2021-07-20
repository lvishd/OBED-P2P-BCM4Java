package interfaces;

import fr.sorbonne_u.components.interfaces.OfferedCI;
import fr.sorbonne_u.components.interfaces.RequiredCI;

public interface ConnectionInfo extends OfferedCI,RequiredCI{
	P2PAddressI getAddress();
	String getCommunicationInboudPortURI();
	String getRoutingInboundPortURI();
	boolean isRouting();
	PositionI getPosition();
}
