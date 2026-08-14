package com.xuefengpeng.fruit.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.Fertilizable;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import net.minecraft.item.ItemPlacementContext;

/**
 * ============================================================
 * 野生水果灌木块（甜浆果风格，Minecraft 1.20.1 API）
 * ============================================================
 * 用于现实中为灌木/藤本的水果（蓝莓、草莓、葡萄、猕猴桃、火龙果）。
 * 有生长阶段，成熟后可右键采摘水果，而非掉落方块自身。
 */
public class FruitBushBlock extends Block implements Fertilizable {

	/** 生长阶段：0-3，阶段 3 为成熟 */
	public static final IntProperty AGE = IntProperty.of("age", 0, 3);

	private final Identifier fruitId;

	public FruitBushBlock(Settings settings, Identifier fruitId) {
		super(settings);
		this.fruitId = fruitId;
		this.setDefaultState(this.getStateManager().getDefaultState().with(AGE, 0));
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(AGE);
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

	private boolean isFertileSoil(BlockState state) {
		return state.isOf(Blocks.GRASS_BLOCK)
				|| state.isOf(Blocks.DIRT)
				|| state.isOf(Blocks.FARMLAND);
	}

	@Override
	public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
		return !state.canPlaceAt(world, pos)
				? Blocks.AIR.getDefaultState()
				: super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
	}

	@Override
	public boolean hasRandomTicks(BlockState state) {
		return true;
	}

	@Override
	public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
		int age = state.get(AGE);
		if (age < 3 && random.nextFloat() < 0.1f) {
			world.setBlockState(pos, state.with(AGE, age + 1), Block.NOTIFY_ALL);
		}
	}

	/**
	 * 成熟后果丛可右键采摘水果。
	 */
	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		if (state.get(AGE) < 3) {
			return ActionResult.PASS;
		}
		if (!world.isClient) {
			dropFruit((ServerWorld) world, pos);
			world.setBlockState(pos, state.with(AGE, 0), Block.NOTIFY_ALL);
			world.playSound(null, pos, SoundEvents.BLOCK_SWEET_BERRY_BUSH_PICK_BERRIES, SoundCategory.BLOCKS, 1.0f, 0.8f);
		}
		return ActionResult.success(world.isClient);
	}

	private void dropFruit(ServerWorld world, BlockPos pos) {
		Item fruit = Registries.ITEM.get(fruitId);
		if (fruit == null) {
			return;
		}
		ItemEntity entity = new ItemEntity(
				world,
				pos.getX() + 0.5,
				pos.getY() + 0.5,
				pos.getZ() + 0.5,
				new ItemStack(fruit, 1));
		world.spawnEntity(entity);
	}

	@Override
	public boolean isFertilizable(WorldView world, BlockPos pos, BlockState state, boolean isClient) {
		return state.get(AGE) < 3;
	}

	@Override
	public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
		return true;
	}

	@Override
	public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
		int growth = state.get(AGE) + 1;
		if (growth > 3) {
			growth = 3;
		}
		world.setBlockState(pos, state.with(AGE, growth), Block.NOTIFY_ALL);
	}
}