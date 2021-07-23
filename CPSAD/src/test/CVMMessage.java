package test;

import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.cvm.AbstractCVM;

import P2PAddress.P2PAddress;
import components.CommunicationComponent;
import components.RegisterComponent;
import position.Position;

/**
 * Classe Main permettant de tester l'envoi de message dans le réseau P2P.
 * @author OBED
 */
public class CVMMessage extends AbstractCVM {

	/**
	 * Constructeur vide avec exception imposée par l'interface.
	 * @throws Exception une exception.
	 */
	public CVMMessage() throws Exception {}

	@Override
	public void deploy() throws Exception {

		AbstractComponent.createComponent(RegisterComponent.class.getCanonicalName(), new Object[] {});
		
		AbstractComponent.createComponent(CommunicationComponent.class.getCanonicalName(),
				new Object[] { new P2PAddress("192.168.25.1"), new Position(1, 2), 1120.0 });
		
		AbstractComponent.createComponent(CommunicationComponent.class.getCanonicalName(),
				new Object[] { new P2PAddress("192.168.25.2"), new Position(2, 2), 1120.0 });
		
		AbstractComponent.createComponent(CommunicationComponent.class.getCanonicalName(),
				new Object[] { new P2PAddress("192.168.25.3"), new Position(1, 3), 1120.0 });
		
		/*		AbstractComponent.createComponent(CommunicationComponent.class.getCanonicalName(),
				new Object[] { new P2PAddress("192.168.25.3"), new Position(1, 2), 1120.0 });
*/
		
		super.deploy();
	}

	/**
	 * Méthode main permettant d'envoyer un message dans le réseau.
	 * @param args les arguments de lancement du programme.
	 */
	public static void main(String[] args) {
		try {
			CVMMessage c = new CVMMessage();
			c.startStandardLifeCycle(1000L);
			System.exit(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}