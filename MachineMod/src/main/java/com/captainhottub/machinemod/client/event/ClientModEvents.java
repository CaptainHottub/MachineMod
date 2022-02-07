package com.captainhottub.machinemod.client.event;

import com.captainhottub.machinemod.MachineMod;
import com.captainhottub.machinemod.core.init.BlockInit;

import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = MachineMod.MODID, bus = Bus.MOD, value = Dist.CLIENT)

public final class ClientModEvents {
	
	
	@SubscribeEvent
	public static void clientSetup(FMLClientSetupEvent event) {
		ItemBlockRenderTypes.setRenderLayer(BlockInit.SPRINKLER_BLOCK.get(), RenderType.cutout());
	}
	
	
	private ClientModEvents() {
	}
	
}
