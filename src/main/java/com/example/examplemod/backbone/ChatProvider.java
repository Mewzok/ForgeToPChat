package com.example.examplemod.backbone;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
// Provides chat capabilities
public class ChatProvider implements ICapabilitySerializable<NBTBase>
{
	@CapabilityInject(IChatStates.class)
	public static final Capability<IChatStates> wc = null;
	
	private IChatStates instance = wc.getDefaultInstance();

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing)
	{
		return capability == wc;
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing)
	{
		return capability == wc ? wc.<T> cast(this.instance) : null;
	}

	@Override
	public NBTBase serializeNBT()
	{
		return wc.getStorage().writeNBT(wc, this.instance, null);
	}

	@Override
	public void deserializeNBT(NBTBase nbt)
	{
		wc.getStorage().readNBT(wc, this.instance, null, nbt);
	}
	
}
