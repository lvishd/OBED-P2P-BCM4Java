package test;


import P2PAddress.P2PAddress;
import components.CommunicationComponent;
import components.RegisterComponent;
import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.cvm.AbstractCVM;
import position.Position;

public class CVMMessage extends AbstractCVM {

	public CVMMessage() throws Exception {

	}

	@Override
	public void deploy() throws Exception {
		AbstractComponent.createComponent(RegisterComponent.class.getCanonicalName(), new Object[] {});
		AbstractComponent.createComponent(CommunicationComponent.class.getCanonicalName(),
		new Object[] { new P2PAddress("192.168.25.1"), new Position(1, 2), 1120.0 });
		AbstractComponent.createComponent(CommunicationComponent.class.getCanonicalName(),
				new Object[] { new P2PAddress("192.168.25.2"), new Position(2,3), 1120.0 });
				
		super.deploy();
	}

	public static void main(String[] args) {
		try {
			CVMMessage c = new CVMMessage();
			c.startStandardLifeCycle(1000L);
			System.exit(0);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}