package com.captainhottub.machinemod.core.init;

import com.captainhottub.machinemod.MachineMod;
import com.captainhottub.machinemod.common.block.SprinklerBlock;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class BlockInit {
	
	

	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, 
			MachineMod.MODID);
	
	
	public static final RegistryObject<Block> FARMER_BLOCK = BLOCKS.register("farmer_block", 
			() -> new Block(BlockBehaviour.Properties.of(Material.STONE).strength(1.5f, 6.0f)));

	
	public static final RegistryObject<SprinklerBlock> SPRINKLER_BLOCK = BLOCKS.register("sprinkler_block", 
			() -> new SprinklerBlock(BlockBehaviour.Properties.of(Material.METAL, MaterialColor.COLOR_ORANGE)
					.strength(8.0f, 4.0f).dynamicShape()));

	
	private BlockInit() {
		
	}

}
