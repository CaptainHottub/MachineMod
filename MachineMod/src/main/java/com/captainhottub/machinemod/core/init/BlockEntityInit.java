package com.captainhottub.machinemod.core.init;

import com.captainhottub.machinemod.MachineMod;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class BlockEntityInit {
	
	public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister
			.create(ForgeRegistries.BLOCK_ENTITIES, MachineMod.MODID);
	
	
	public static final RegistryObject<BlockEntityType<SprinklerBlockEntity>> SPRINKLER = BLOCK_ENTITIES.register(
			"sprinkler", () -> BlockEntityType.Builder.of(SprinklerBlockEntity::new, BlockInit.SPRINKLER_BLOCK.get()));
	
	
	private BlockEntityInit() {
		
	}
}
