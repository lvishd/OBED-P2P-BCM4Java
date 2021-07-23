package position;

import java.io.Serializable;

import interfaces.PositionI;

/**
 * Classe permettant de créer une position pour un noeud.
 * @author OBED
 */
public class Position implements PositionI, Serializable {
	/** Numéro de série pour chaque instance.*/
	private static final long serialVersionUID = 1L;
	/** La position en X */
	private double x;
	/** La position en Y */
	private double y;
	
	/**
	 * Constructeur d'une position avec une abscisse et une ordonnée.
	 * @param x l'abscisse.
	 * @param y l'ordonnée.
	 */
	public Position(double x, double y)  {
		this.x = x;
		this.y = y;
	}

	/**
	 * Permet d'obtenir la position en X depuis les autres classes.
	 * @return la position en X.
	 */
	public double getX() {
		return x;
	}

	/**
	 * Permet d'obtenir la position en Y depuis les autres classes.
	 * @return la position en Y.
	 */
	public double getY() {
		return y;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Position other = (Position) obj;
		if (this.x != other.x)
			return false;
		if (this.y != other.y)
			return false;
		return true;
	}

	@Override
	public double distance(PositionI other) {
		if(!(other instanceof Position))
			return -1;
		Position p = (Position)other;
		return Math.sqrt(Math.pow(p.x-this.x, 2)+Math.pow(p.y - this.y, 2));
	}
}
