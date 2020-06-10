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

	@Override
	public NBTBase writeNBT(Capability<IChatStates> capability, IChatStates instance, EnumFacing side)
	{
		// Save information to NBT
		return new NBTTagString(instance.getChatStates());
	}

	@Override
	public void readNBT(Capability<IChatStates> capability, IChatStates instance, EnumFacing side, NBTBase nbt)
	{
		System.out.println("CCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCC");
		// Load information from NBT
		// This line below is breaking.
		instance.setWC(((NBTTagCompound) nbt).getBoolean(null));
	}

}
