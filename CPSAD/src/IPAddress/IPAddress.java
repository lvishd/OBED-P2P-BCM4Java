package IPAddress;


import java.io.Serializable;

import interfaces.AddressI;
import interfaces.IPAddressI;

public class IPAddress implements IPAddressI, Serializable{
	private static final long serialVersionUID = 1L;
	private String addr;
	
	
	public IPAddress(String addr) 
	{
		this.addr = addr;
	}
	
	public String getAddr() 
	{
		return addr;
	}
	

	@Override
	public boolean isP2PAddress() 
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isIPAddress() 
	{
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean equals(AddressI a) 
	{
		// TODO Auto-generated method stub
		if(!a.isIPAddress())
			return false;
		IPAddress address = (IPAddress)  a;
		if(!this.addr.equals(address.addr))
			return false;
		return true;
	}

}
