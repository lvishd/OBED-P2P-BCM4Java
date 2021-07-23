package connectors;

import interfaces.P2PAddressI;

/**
 * Classe permettant d'obtenir les information sur une connexion.
 * @author OBED
 */
public class ConnectionInfo {
	/** L'addresse du composant. */
	private P2PAddressI address;
	/** L'URI du port entrant du composant. */
	private String communicationInboundPortURI;
	/** L'URI du port entrant du routing. */
	private String routingInboundPortURI;
	/** Un booléen vérifiant si le composant est dans un routing. */
	private boolean isRouting;
	
	/**
	 * Constructeur permettant d'initialiser une concaténation d'informations de connections.
	 * @param address l'addresse du composant.
	 * @param communicationInboundPortURI l'URI du port entrant du composant.
	 * @param routingInboundPortURI l'URI du port entrant du routing.
	 * @param isRouting le booléen vérifiant si le composant est dans un routing.
	 */
	public ConnectionInfo(P2PAddressI address, String communicationInboundPortURI, String routingInboundPortURI, boolean isRouting) {
		this.address = address;
		this.communicationInboundPortURI = communicationInboundPortURI;
		this.routingInboundPortURI = routingInboundPortURI;
		this.isRouting = isRouting;
	}

	/**
	 * Permet d'accéder à l'addresse du composant depuis d'autres classes.
	 * @return l'addresse P2P.
	 */
	public P2PAddressI getAddress() {
		return address;
	}
	
	/**
	 * Permet d'accéder à l'URI du port entrant du composant depuis d'autres classes.
	 * @return l'URI du port entrant du composant.
	 */
	public String getCommunicationInboudPort() {
		return communicationInboundPortURI;
	}

	/**
	 * Permet de vérifier si le routing est en cours depuis d'autres classes.
	 * @return un booléen sur la condition, true si le routing est en cours, false sinon.
	 */
	public boolean isRouting() {
		return isRouting;
	}

	/**
	 * Permet d'accéder à l'URI du port entrant du routing depuis d'autres classes.
	 * @return l'URI du port entrant du routing.
	 */
	public String getRoutingInboundPortURI() {
		return routingInboundPortURI;
	}
	
}
