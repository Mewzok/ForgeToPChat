package com.example.examplemod.backbone;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTPrimitive;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

// Saves and reads information from or to server
public class ChatStateStorage implements IStorage<IChatStates>
{

	// Save player information to NBT storage
	@Override
	public NBTBase writeNBT(Capability<IChatStates> capability, IChatStates instance, EnumFacing side)
	{
		// Save information to NBT
		NBTTagCompound cm = new NBTTagCompound();
		cm.setBoolean("getWC", instance.getWC());
		cm.setBoolean("getTC", instance.getTC());
		cm.setBoolean("getLC", instance.getLC());
		cm.setString("getMode", instance.getMode());
		return cm;
	}

	// Write player information to NBT storage
	@Override
	public void readNBT(Capability<IChatStates> capability, IChatStates instance, EnumFacing side, NBTBase nbt)
	{
		// Load information from NBT
		instance.setWC(((NBTTagCompound) nbt).getBoolean("getWC"));
		instance.setTC(((NBTTagCompound) nbt).getBoolean("getTC"));
		instance.setLC(((NBTTagCompound) nbt).getBoolean("getLC"));
		instance.setMode(((NBTTagCompound) nbt).getString("getMode"));
	}
}
