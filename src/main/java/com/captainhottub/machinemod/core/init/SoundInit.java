package com.captainhottub.machinemod.core.init;

import com.captainhottub.machinemod.MachineMod;

import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public final class SoundInit {
	
	
	public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS,
			MachineMod.MODID);
	
	/*
	public static final RegistryObject<SoundEvent> ANVIL_BREAK = SOUNDS.register("anvil_break",
			() -> SoundEvent(new ResourceLocation(MachineMod.MODID, "entity.anvil_break")));
	*/
	
	
	private SoundInit() {}

}
