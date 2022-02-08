package com.captainhottub.machinemod.common.block;



import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import com.captainhottub.machinemod.MachineMod;
import com.captainhottub.machinemod.common.entity.SprinklerBlockEntity;
import com.captainhottub.machinemod.core.config.ModConfigs;
import com.captainhottub.machinemod.core.init.BlockEntityInit;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;



public class SprinklerBlock extends HorizontalDirectionalBlock implements  EntityBlock {	
	//private final int range;
	//private final int cooldown;
	static int range;
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


	
	
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return BlockEntityInit.SPRINKLER.get().create(pos, state);
	}
	

	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state,
			BlockEntityType<T> type) {
		return level.isClientSide ? null : ($0, $1, $2, blockEntity) -> ((SprinklerBlockEntity) blockEntity).tick();
	}
	


}
