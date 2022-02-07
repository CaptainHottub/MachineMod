package com.captainhottub.machinemod.common.block;



import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Stream;

import com.captainhottub.machinemod.MachineMod;
import com.captainhottub.machinemod.common.util.UtilParticle;
import com.captainhottub.machinemod.core.config.ModConfigs;
import com.captainhottub.machinemod.core.init.BlockEntityInit;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.FarmBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.IPlantable;



public class SprinklerBlock extends HorizontalDirectionalBlock implements EntityBlock {	
	//private final int range;
	//private final int cooldown;
	int range;
	int cooldown;
	double chance;
	
	
	private static final Map<Direction,VoxelShape> SHAPES = new EnumMap<>(Direction.class);
	private static final Optional<VoxelShape> SHAPE = Stream
			.of(Block.box(5.5, 0.5, 3, 6.5, 1.5, 12),
				Block.box(7.5, 2, 3, 8.5, 3, 12),
				Block.box(5, 0, 2, 11, 3.5, 3),
				Block.box(5, 0, 12, 11, 3.5, 13),
				Block.box(9.5, 0.5, 3, 10.5, 1.5, 12)
			).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR));


	
	public SprinklerBlock(Properties properties) {
		super(properties);
		registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.NORTH));
		runCalculation(SHAPE.orElse(Shapes.block()));
		
		range = ModConfigs.SPRINKLER_RANGE.get();
		cooldown = ModConfigs.SPRINKLER_COOLDOWN.get();
		chance = ModConfigs.SPRINKLER_GROWTH_CHANCE.get();
		
	}	
	
	
	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(FACING);
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return SHAPES.get(state.getValue(FACING));
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
	}

	protected void runCalculation(VoxelShape shape) {
		for (Direction direction : Direction.values())
			SHAPES.put(direction, MachineMod.calculateShapes(direction, shape));
	}
	
	
	// https://www.youtube.com/watch?v=yQb46-RQ3Z0&t=4s&ab_channel=TurtyWurty
	
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state,
			BlockEntityType<T> type) {
	}
	
	
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return BlockEntityInit.SPRINKLER.get().create(pos, state);
	}

	
	
	
	
	
	/*
	 *	
	 * Make it increase crop growth speed. by sending random tick updates 
	 * to any plantBlock instanceof BonemealableBlock IPlantable, Blocks.MYCELIUM, Blocks.CHORUS_FLOWER
	 * 
	 *
	 * Also make the 2 if statements in tick() 1 if statemtent
	 * and put the hydrate and crop growth code in the tick method, like WateringCanItem from MysticalAgriculture
	 * 
	 * Finnaly when its done, remove the InteractionResult use method, so it doenst become OP
	 * 
	 * https://forums.minecraftforge.net/topic/69690-updatetick-and-scheduleupdate/
	 * https://forums.minecraftforge.net/topic/77001-solved-1144-block-ticking-and-properties/
	 * https://forums.minecraftforge.net/topic/83183-1152-adding-a-particle-track-to-my-projectileitementity/
	 * 
	
        
	 */
	
	
	@Override
	public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean isMoving) {
		level.scheduleTick(pos, this, cooldown);
	}

	// this spawns the rain particles
	@SuppressWarnings("resource")
	public void doWater(BlockState state, Level level, BlockPos pos) {
		//int range = 9;
		int aoeRange = (this.range - 1) / 2;
		for (int x = -aoeRange; x <= aoeRange; x++) {
            for (int z = -aoeRange; z <= aoeRange; z++) {
            	double d0 = pos.offset(x, 0, z).getX() + level.getRandom().nextFloat();
                double d1 = pos.offset(x, 0, z).getY() + 1.0D;
                double d2 = pos.offset(x, 0, z).getZ() + level.getRandom().nextFloat();
               
                // I dont think bellow is neccesarry, just make the Y offset hardcoded, ie: +0.4D
                var state1 = level.getBlockState(pos);
                if (state1.canOcclude() || state1.getBlock() instanceof FarmBlock)
                    d1 += 0.3D;
           
                Minecraft.getInstance().particleEngine.createParticle(ParticleTypes.FALLING_WATER, d0, d1, d2, 0.0D, 0.0D, 0.0D);
                //level.addParticle(ParticleTypes.FALLING_WATER, d0, d1, d2, 0.0D, 0.0D, 0.0D);
            }
        }
	}
	
	
	
	
	//int range = 9;
	@SuppressWarnings("resource")
	@Override
	public void tick(BlockState state, ServerLevel level, BlockPos pos, Random random) {
		FluidState fluidstate = level.getFluidState(pos.below());
        if (fluidstate.is(FluidTags.WATER)) {

            //if (!level.isClientSide) {
        	
        	
        	// Hydrates the farmland
	    	int range = (this.range - 1) / 2;
	   	 	// It is like that because it is a distance away from the block
	        BlockPos.betweenClosedStream(pos.offset(-range, -range, -range), pos.offset(range, range, range)).forEach(aoePos -> {
	        	var aoeState = level.getBlockState(aoePos);
	            if (aoeState.getBlock() instanceof FarmBlock) {
	            	int moisture = aoeState.getValue(FarmBlock.MOISTURE);
	            	if (moisture < 7) {
	            		level.setBlock(aoePos, aoeState.setValue(FarmBlock.MOISTURE, 7), 3);
	                   }
	               }
	        	});
	        
        	
	        /*
        	
	    	// Creates the water particles
	    	//int aoeRange = (this.range - 1) / 2;
			for (int x = -range; x <= range; x++) {
	            for (int z = -range; z <= range; z++) {
	            	double d0 = pos.offset(x, 0, z).getX() + level.getRandom().nextFloat();
	                double d1 = pos.offset(x, 0, z).getY() + 1.0D;
	                double d2 = pos.offset(x, 0, z).getZ() + level.getRandom().nextFloat();
	               
	                // I dont think bellow is neccesarry, just make the Y offset hardcoded, ie: +0.4D
	                var state1 = level.getBlockState(pos);
	                if (state1.canOcclude() || state1.getBlock() instanceof FarmBlock)
	                    d1 += 0.3D;
	           
	                //Minecraft.getInstance().particleEngine.createParticle(ParticleTypes.FALLING_WATER, d0, d1, d2, 0.0D, 0.0D, 0.0D);
	                level.addParticle(ParticleTypes.FALLING_WATER, d0, d1, d2, 0.0D, 0.0D, 0.0D);
	            }
	        }
	    	*/
			
	        
			/*
			 * Make it increase crop growth speed. by sending random tick updates 
			 * to any plantBlock instanceof BonemealableBlock IPlantable, Blocks.MYCELIUM, Blocks.CHORUS_FLOWER
			 * 
			 * It applies a bonemeal-like effect to all plants that accept it 
			 * (plants like those added by Mystical Agriculture are excluded) in a 9x9 area when placed over a water block. 
			 * Every 100 ticks it applies this effect to a randomly selected 40% of plants in its area.
			 */
			
			// Right not it has a 40% chance to activate and it will  applies the effect to all in 9x9
			// make it activate every 40 ticks and have a 40% chance to apply effect on block
			
	        if (!level.isClientSide) {	    

	        		BlockPos.betweenClosedStream(pos.offset(-range, -range, -range), pos.offset(range, range, range)).forEach(aoePos -> {
	                	var aoeState = level.getBlockState(aoePos);
	                	var plantBlock = aoeState.getBlock();	

	                    if (plantBlock instanceof BonemealableBlock || plantBlock instanceof IPlantable || plantBlock == Blocks.MYCELIUM || plantBlock == Blocks.CHORUS_FLOWER) {
	                    	System.out.println("plantBlock is BonemealableBlock or wtv");
	                    	if (Math.random() <= this.chance) {
	        	        		System.out.println("Math.random() was less then 0.40");
	        	        		
	        	        		System.out.println(aoePos);
	        	        		
	        	        		UtilParticle.spawnParticle2(level, ParticleTypes.FALLING_WATER, pos, this.range);
	        	        		
	                    	
	                    	}   
	                    }
	                    	
	                });
	        }
        	  

	        
        } 
		level.scheduleTick(pos, this, cooldown);
	}
	
	
	// This is just for testing, Remove when its works properly.
	@Override
	public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand,
			BlockHitResult result) {
		// Only works if sprinkle is above water.
        FluidState fluidstate = level.getFluidState(pos.below());
        if (fluidstate.is(FluidTags.WATER)) {
        	//doWater(state, level, pos);
        	return InteractionResult.SUCCESS;	
        }
		return InteractionResult.FAIL;	
	}


	
	
	



}
