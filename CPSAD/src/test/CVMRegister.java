package test;


import components.RegisterComponent;
import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.cvm.AbstractCVM;

public class CVMRegister extends AbstractCVM {

	public CVMRegister() throws Exception {

	}

	@Override
	public void deploy() throws Exception {
		
		AbstractComponent.createComponent(RegisterComponent.class.getCanonicalName(), new Object[] {});
	
		super.deploy();
	}

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