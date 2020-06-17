package com.example.examplemod.backbone;

import net.minecraft.nbt.NBTTagCompound;

//States for chat types
public interface IChatStates
{
	public void setWC(boolean wcState);
	public boolean getWC();
	public void setTC(boolean tcState);
	public boolean getTC();
	public void setLC(boolean lcState);
	public boolean getLC();
	public void setGC(boolean gcState);
	public boolean getGC();
	public void setMode(String curMode);
	public String getMode();
}
