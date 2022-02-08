package com.captainhottub.machinemod.common.entity;

import com.captainhottub.machinemod.common.util.Utils;
import com.captainhottub.machinemod.core.init.BlockEntityInit;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.FarmBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraftforge.common.ForgeConfigSpec.DoubleValue;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;
import net.minecraftforge.common.IPlantable;

public class SprinklerBlockEntity extends BlockEntity{

	
	public static IntValue COOLDOWN;
	public static DoubleValue GROWTH_CHANCE;
	public static IntValue RANGE;
	int timer;
	
	public SprinklerBlockEntity(BlockPos pos, BlockState state) {
		super(BlockEntityInit.SPRINKLER.get(), pos, state);
	}

	public void tick() {
		timer--;
	    if (timer > 0) {
	    	return;
	    }
	    timer = COOLDOWN.get();
	    
	    FluidState fluidstate = level.getFluidState(this.worldPosition.below());
        if (fluidstate.is(FluidTags.WATER)) {
        	
        	// Hydrates the farmland
	    	int aoeRange = (RANGE.get() - 1) / 2;
	   	 	// It is like that because it is a distance away from the block
	        BlockPos.betweenClosedStream(this.worldPosition.offset(-aoeRange, -aoeRange, -aoeRange), this.worldPosition.offset(aoeRange, aoeRange, aoeRange)).forEach(aoePos -> {
	        	var aoeState = level.getBlockState(aoePos);
	            if (aoeState.getBlock() instanceof FarmBlock) {
	            	int moisture = aoeState.getValue(FarmBlock.MOISTURE);
	            	if (moisture < 7) {
	            		level.setBlock(aoePos, aoeState.setValue(FarmBlock.MOISTURE, 7), 3);
	            		//UtilParticle.spawnParticle2(level, ParticleTypes.FALLING_WATER, this.worldPosition, range);
	                   }
	               }
	        	});
        	
	        
            //System.out.println(ran);
            if (Math.random() < GROWTH_CHANCE.get()) {
        		// Spawns particles on plants.
		    	BlockPos.betweenClosedStream(this.worldPosition.offset(-RANGE.get(), -RANGE.get(), -RANGE.get()), this.worldPosition.offset(RANGE.get(), RANGE.get(), RANGE.get())).forEach(aoePos -> {
		        	var aoeState = level.getBlockState(aoePos);
		        	var plantBlock = aoeState.getBlock();	
		
		            if (plantBlock instanceof BonemealableBlock || plantBlock instanceof IPlantable || plantBlock == Blocks.MYCELIUM || plantBlock == Blocks.CHORUS_FLOWER) {
		            	//System.out.println("plantBlock is BonemealableBlock or wtv");  
		        		aoeState.randomTick((ServerLevel) level, aoePos, Utils.RANDOM);
		            }   
		    	});
        	}

        }

	}
	
	
	
}
