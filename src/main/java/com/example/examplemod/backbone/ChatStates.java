package com.example.examplemod.backbone;

//default implementation of chat states
public class ChatStates implements IChatStates
{
	private boolean wcState = true;
	private String allStates = "test test test";
	
	@Override
	public boolean setWC(boolean wcState)
	{
		// Set World Chat to enabled or disabled
		if(wcState == true)
			wcState = false;
		else
			wcState = true;
		return wcState;
	}

	@Override
	public String getChatStates()
	{
		// Obtain information on the current state of the player's chat
		return this.allStates;
	}
	
	@Override
	public void setChatStates(String state)
	{
		// Set information on the current state of the player's chat
		this.allStates = "World chat: " + this.wcState;
	}

}
