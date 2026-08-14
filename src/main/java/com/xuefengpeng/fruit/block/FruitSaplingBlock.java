package com.xuefengpeng.fruit.block;

import com.xuefengpeng.fruit.worldgen.FruitTreeGenerator;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.Fertilizable;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

/**
 * ============================================================
 * 果树苗（Minecraft 1.20.1 API）
 * ============================================================
 * 乔木类水果的树苗。可自然生长为树，也支持骨粉催熟为树。
 * 生长阶段保留用于展示（苗 → 幼树 → 成树），成熟后掉落水果。
 */
public class FruitSaplingBlock extends Block implements Fertilizable {

	/** 生长阶段属性：0=苗 1=幼树 2=成树 3=开花 4=结果 */
	public static final IntProperty GROWTH = IntProperty.of("growth", 0, 4);

	/** 结果后掉落的水果物品 ID */
	private final Identifier fruitId;

	private static final VoxelShape SAPLING_SHAPE = Block.createCuboidShape(4.0, 0.0, 4.0, 12.0, 12.0, 12.0);
	private static final VoxelShape TREE_SHAPE = Block.createCuboidShape(2.0, 0.0, 2.0, 14.0, 16.0, 14.0);

	public FruitSaplingBlock(Settings settings, Identifier fruitId, boolean canGrowIntoTree) {
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
		return SAPLING_SHAPE;
	}

	@Override
	public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
		return isFertileSoil(world.getBlockState(pos.down()));
	}

	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		BlockState state = super.getPlacementState(ctx);
		return state != null && state.canPlaceAt(ctx.getWorld(), ctx.getBlockPos()) ? state : null;
	}

	@Override
	public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
		return !state.canPlaceAt(world, pos)
				? Blocks.AIR.getDefaultState()
				: super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
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
		// 已长成幼树/成树阶段，仍有概率继续生长为完整果树
		if (hasEnoughLight(world, pos) && isFertileSoil(world.getBlockState(pos.down()))) {
			if (random.nextFloat() < 0.1f) {
				// 尝试长成完整果树；空间不足则退化为提升一个生长阶段
				if (!FruitTreeGenerator.generate(world, pos, fruitId.getPath())) {
					growStage(world, pos, state, 1);
				}
			}
		} else if (growth >= 4) {
			// 无光照/土壤时的结果回落：仍有少量概率掉落水果
			if (random.nextFloat() < 0.2f) {
				dropFruit(world, pos);
			}
		}
	}

	private void growStage(ServerWorld world, BlockPos pos, BlockState state, int amount) {
		int newGrowth = Math.min(4, state.get(GROWTH) + amount);
		world.setBlockState(pos, state.with(GROWTH, newGrowth), Block.NOTIFY_ALL);
	}

	private boolean hasEnoughLight(ServerWorld world, BlockPos pos) {
		return world.getLightLevel(net.minecraft.world.LightType.SKY, pos.up()) >= 9;
	}

	// ---------------------------------------------------------
	// Fertilizable 接口实现（骨粉催熟为树）
	// ---------------------------------------------------------

	@Override
	public boolean isFertilizable(WorldView world, BlockPos pos, BlockState state, boolean isClient) {
		return true;
	}

	@Override
	public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
		return isFertileSoil(world.getBlockState(pos.down()));
	}

	@Override
	public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
		String fruit = fruitId.getPath();
		// 骨粉催熟：直接生成一棵完整果树
		if (FruitTreeGenerator.generate(world, pos, fruit)) {
			return;
		}
		// 若空间不足，退化为提升一个生长阶段
		int newGrowth = Math.min(4, state.get(GROWTH) + 1);
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