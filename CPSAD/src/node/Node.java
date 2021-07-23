package node;

import interfaces.P2PAddressI;
import interfaces.PositionI;
import connectors.ConnectionInfo;

/**
 * Classe permettant d'identifier un noeud.
 * @author OBED
 */
public class Node {
	
	/** La position initiale. */
	private PositionI initialPosition;
	/** L'URI du port entrant du noeud. */
	private String communicationInboundPortURI;
	/** L'URI du port entrant de l'élément via lequel le noeud va offrir tous les services de gestion du routage dans le réseau. */
	private String routingInboundPortURI;
	
	/**
	 * Constructeur d'un noeud contenant toutes les informations nécessaires à son fonctionnement.
	 * @param initialPosition la position initiale.
	 * @param communicationInboundPortURI l'URI du port entrant du noeud.
	 * @param routingInboundPortURI l'URI du port entrant de l'élément via lequel le noeud va offrir tous les services de gestion du routage dans le réseau.
	 */
	public Node(PositionI initialPosition, String communicationInboundPortURI, String routingInboundPortURI) {
		super();
		this.communicationInboundPortURI = communicationInboundPortURI;
		this.initialPosition = initialPosition;
		this.routingInboundPortURI = routingInboundPortURI;
	}
	
	/**
	 * Constructeur d'un noeud contenant seulement l'information du port entrant du noeud.
	 * @param communicationInboundPortURI l'URI du port entrant du noeud.
	 */
	public Node(String communicationInboundPortURI) {
		super();
		this.communicationInboundPortURI = communicationInboundPortURI;
	}

	/**
	 * Constructeur d'un noeud contenant seulement l'information du port entrant du noeud et sa position initiale.
	 * @param initialPosition la position initiale.
	 * @param communicationInboundPortURI l'URI du port entrant du noeud.
	 */
	public Node(PositionI initialPosition, String communicationInboundPortURI) {
		super();
		this.initialPosition = initialPosition;
		this.communicationInboundPortURI = communicationInboundPortURI;
		this.routingInboundPortURI = null;
	}
	
	/**
	 * Constructeur d'un noeud contenant seulement l'information du port entrant du noeud et le port de routing.
	 * @param communicationInboundPortURI l'URI du port entrant du noeud.
	 * @param routingInboundPortURI l'URI du port entrant de l'élément via lequel le noeud va offrir tous les services de gestion du routage dans le réseau.
	 */
	public Node(String communicationInboundPortURI, String routingInboundPortURI){
		super();
		this.communicationInboundPortURI = communicationInboundPortURI;
		this.routingInboundPortURI = routingInboundPortURI;
	}
	
	/**
	 * Permet d'obtenir la position initiale du noeud depuis les autres classes.
	 * @return la position initiale du noeud.
	 */
	public PositionI getInitialPosition() {
		return initialPosition;
	}

	/**
	 * Créer l'information sur la connexion du noeud avec le reste du système.
	 * @param address l'addresse du noeud.
	 * @param node le noeud avec l'information qu'il contient déjà.
	 * @return l'information sur la connexion du noeud avec le reste du système.
	 */
	public static ConnectionInfo getConnectionInfo(P2PAddressI address, Node node) {
		if (node.routingInboundPortURI == null) {
			return new ConnectionInfo(address, node.communicationInboundPortURI, null, false);
		} else {
			return new ConnectionInfo(address, node.communicationInboundPortURI, node.communicationInboundPortURI, true);
		}
	}
}
