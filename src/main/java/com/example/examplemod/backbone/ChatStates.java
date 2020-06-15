package com.example.examplemod.backbone;

//default implementation of chat states
public class ChatStates implements IChatStates
{
	private boolean wcState = true;
	private boolean tcState = true;
	
	public void setWC(boolean wcState)
	{
		// Set World Chat to enabled or disabled
		this.wcState = wcState;
	}

	public boolean getWC()
	{
		// Get World Chat state
		return this.wcState;
	}

	@Override
	public void setTC(boolean tcState)
	{
		// Set Trade Chat to enabled or disabled
		this.tcState = tcState;
	}

	@Override
	public boolean getTC()
	{
		// Get Trade Chat state
		return this.tcState;
	}

	@Override
	public void setLC(boolean lcState)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean getLC()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setGC(boolean GCState)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean getGC()
	{
		// TODO Auto-generated method stub
		return false;
	}
}
