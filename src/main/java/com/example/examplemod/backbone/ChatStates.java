package com.example.examplemod.backbone;

//default implementation of chat states
public class ChatStates implements IChatStates
{
	private boolean wcState = true;
	private boolean tcState = true;
	private boolean lcState = true;
	private boolean gcState = true;
	private String currentMode = "World";
	
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
		// Set Local Chat state
		this.lcState = lcState;
	}

	@Override
	public boolean getLC()
	{
		// Get Local Chat state
		return this.lcState;
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

	@Override
	public void setMode(String curMode)
	{
		// TODO Auto-generated method stub
		this.currentMode=curMode;
		
		/*
		if(curMode=="World")
		{
			this.currentMode="World";
		}else if(curMode=="Trade")
		{
			this.currentMode="Trade";
		}else if(curMode=="Local")
		{
			this.currentMode="Local";
		}else if(curMode=="Team")
		{
			this.currentMode="Team";
		}else
			this.currentMode="Unknown";
			*/
	}

	@Override
	public String getMode()
	{
		// TODO Auto-generated method stub
		return this.currentMode;
	}
}
