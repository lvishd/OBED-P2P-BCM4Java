package interfaces;

/**
 * Interface permettant de créer une classe de type Position.
 * @author OBED
 */
public interface PositionI {
	/**
	 * Calcule la distance entre deux positions.
	 * @param other l'autre position.
	 * @return la distance entre deux positions.
	 */
	double distance(PositionI other);
}
