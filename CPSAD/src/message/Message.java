package message;

import java.io.Serializable;

import interfaces.AddressI;
import interfaces.MessageI;

public class Message implements MessageI {

	@Override
	public AddressI getAddress() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Serializable getContent() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean stillAlive() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void decrementHops() {
		// TODO Auto-generated method stub
		
	}

}
