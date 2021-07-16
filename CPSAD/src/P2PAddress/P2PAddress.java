package P2PAddress;

import java.io.Serializable;

import interfaces.AddressI;
import interfaces.P2PAddressI;

public class P2PAddress implements P2PAddressI,Serializable {

	
	private static final long serialVersionUID = 1L;
	private String addr;
	
	public P2PAddress(String addr) 
	{
		this.addr = addr;
	}

	@Override
	public boolean isP2PAddress()
	{
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isIPAddress() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean equals(AddressI a) 
	{
		if(a == null)
		{
			return false;
		}
		if(!a.isP2PAddress())
			return false;
		P2PAddress address = (P2PAddress)  a;
		if(!this.addr.equals(address.addr))
			return false;
		return true;
	}

	public String getAddr() {
		return addr;
	}

}
