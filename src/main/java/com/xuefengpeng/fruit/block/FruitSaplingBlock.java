package com.xuefengpeng.fruit.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.Fertilizable;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;

/**
 * ============================================================
 * 果树苗（Minecraft 1.20.1 API）
 * ============================================================
 * 果树生长阶段：苗(0) → 幼树(1) → 成树(2) → 开花(3) → 结果(4)。
 * 支持骨粉催熟、光照与土壤检查、结果掉落。
 */
public class FruitSaplingBlock extends Block implements Fertilizable {

	/** 生长阶段属性：0=苗 1=幼树 2=成树 3=开花 4=结果 */
	public static final IntProperty GROWTH = IntProperty.of("growth", 0, 4);

	/** 结果后掉落的水果物品 ID */
	private final Identifier fruitId;

	private static final VoxelShape SAPLING_SHAPE = Block.createCuboidShape(4.0, 0.0, 4.0, 12.0, 12.0, 12.0);
	private static final VoxelShape TREE_SHAPE = Block.createCuboidShape(2.0, 0.0, 2.0, 14.0, 16.0, 14.0);

	public FruitSaplingBlock(Settings settings, Identifier fruitId) {
		super(settings);
		this.fruitId = fruitId;
		this.setDefaultState(this.getStateManager().getDefaultState().with(GROWTH, 0));
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(GROWTH);
	}

	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return state.get(GROWTH) >= 2 ? TREE_SHAPE : SAPLING_SHAPE;
	}

	@Override
	public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
		return isFertileSoil(world.getBlockState(pos.down()));
	}

	private boolean isFertileSoil(BlockState state) {
		return state.isOf(Blocks.GRASS_BLOCK)
				|| state.isOf(Blocks.DIRT)
				|| state.isOf(Blocks.FARMLAND);
	}

	@Override
	public boolean hasRandomTicks(BlockState state) {
		return true;
	}

	@Override
	public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
		if (world.isClient) {
			return;
		}
		int growth = state.get(GROWTH);
		if (growth >= 4) {
			if (random.nextFloat() < 0.2f) {
				dropFruit(world, pos);
			}
			return;
		}
		if (hasEnoughLight(world, pos) && isFertileSoil(world.getBlockState(pos.down()))) {
			if (random.nextFloat() < 0.15f) {
				grow(world, pos, state, 1);
			}
		}
	}

	private boolean hasEnoughLight(ServerWorld world, BlockPos pos) {
		return world.getLightLevel(net.minecraft.world.LightType.SKY, pos.up()) >= 9;
	}

	// ---------------------------------------------------------
	// Fertilizable 接口实现（骨粉催熟）
	// ---------------------------------------------------------

	@Override
	public boolean isFertilizable(WorldView world, BlockPos pos, BlockState state, boolean isClient) {
		return state.get(GROWTH) < 4;
	}

	@Override
	public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
		return state.get(GROWTH) < 4 && isFertileSoil(world.getBlockState(pos.down()));
	}

	@Override
	public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
		int amount = random.nextBoolean() ? 1 : 2;
		grow(world, pos, state, amount);
	}

	private void grow(ServerWorld world, BlockPos pos, BlockState state, int amount) {
		int newGrowth = Math.min(4, state.get(GROWTH) + amount);
		world.setBlockState(pos, state.with(GROWTH, newGrowth), Block.NOTIFY_ALL);
	}

	private void dropFruit(ServerWorld world, BlockPos pos) {
		Item fruit = Registries.ITEM.get(fruitId);
		if (fruit == null) {
			return;
		}
		ItemEntity itemEntity = new ItemEntity(
				world,
				pos.getX() + 0.5,
				pos.getY() + 1.0,
				pos.getZ() + 0.5,
				new ItemStack(fruit, 1));
		world.spawnEntity(itemEntity);
	}
}