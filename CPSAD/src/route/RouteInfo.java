package route;

import interfaces.AddressI;

/**
 * Classe contenant l'information d'une route.
 * @author OBED
 */
public class RouteInfo {
	/** L'addresse de destination. */
	private AddressI destination;
	/** Nombre d'Útape (autres noeuds) par lesquelles l'information passent.*/
	private int numberOfHops;
	
	/**
	 * Constructeur de la concatÚnation d'information d'une route.
	 * @param destination, l'addresse de destination.
	 * @param numberOfHops, le nombre d'Útape.
	 */
	public RouteInfo(AddressI destination, int numberOfHops) {
		this.destination = destination;
		this.numberOfHops = numberOfHops;
	}
	
	/**
	 * Permet d'obtenir l'addresse de la destination.
	 * @return l'addresse de la destination
	 */
	public AddressI getDestination() {
		return destination;
	}
	
	/**
	 * Permet d'obtenir le nombre d'Útapes nÚcessaires pour atteindre la destination.
	 * @return le nombre d'Útapes nÚcessaires pour atteindre la destination.
	 */
	public int getNumberOfHops() {
		return numberOfHops;
	}
}
