package test;

import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.cvm.AbstractCVM;

import components.RegisterComponent;

/**
 * Classe Main permettant de tester le simulateur.
 * @author OBED
 */
public class CVMRegister extends AbstractCVM {

	/**
	 * Constructeur vide avec exception imposée par l'interface.
	 * @throws Exception une exception.
	 */
	public CVMRegister() throws Exception {}

	@Override
	public void deploy() throws Exception {
		AbstractComponent.createComponent(RegisterComponent.class.getCanonicalName(), new Object[] {});
		super.deploy();
	}
	
	/**
	 * Méthode main permettant de déployer le simulateur.
	 * @param args les arguments de lancement du programme.
	 */
	public static void main(String[] args) {
		try {
			CVMRegister c = new CVMRegister();
			c.startStandardLifeCycle(5000L);
			System.exit(0);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}