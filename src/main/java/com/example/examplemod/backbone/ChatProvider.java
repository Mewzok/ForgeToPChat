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
	public static final Capability<IChatStates> WC_DEFAULT = null;
	
	private IChatStates instance = WC_DEFAULT.getDefaultInstance();

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing)
	{
		return capability == WC_DEFAULT;
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing)
	{
		return capability == WC_DEFAULT ? WC_DEFAULT.<T> cast(this.instance) : null;
	}

	@Override
	public NBTBase serializeNBT()
	{
		return WC_DEFAULT.getStorage().writeNBT(WC_DEFAULT, this.instance, null);
	}

	@Override
	public void deserializeNBT(NBTBase nbt)
	{
		WC_DEFAULT.getStorage().readNBT(WC_DEFAULT, this.instance, null, nbt);
	}
	
}
