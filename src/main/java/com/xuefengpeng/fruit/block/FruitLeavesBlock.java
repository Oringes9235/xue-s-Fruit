package com.xuefengpeng.fruit.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;

/**
 * ============================================================
 * 果树叶
 * ============================================================
 * 继承原版树叶方块。特性：
 *   - 随机刻：有概率在叶片位置生成成熟果实掉落物
 *   - 破坏时可能掉落果实与树苗（由战利品表控制）
 *
 * 使用水果的物品 Identifier（而非直接引用物品对象），
 * 避免物品-方块之间的类初始化顺序依赖。
 */
public class FruitLeavesBlock extends LeavesBlock {

	/** 该树叶对应掉落的水果物品 ID */
	private final Identifier fruitId;

	/**
	 * 构造果树叶。
	 *
	 * @param settings 方块设置
	 * @param fruitId  成熟后掉落的水果物品 ID
	 */
	public FruitLeavesBlock(Settings settings, Identifier fruitId) {
		super(settings);
		this.fruitId = fruitId;
		// 果树叶默认即为持久状态：不会因距离计算而自然腐烂掉落，
		// 只会通过结果机制掉落水果。树叶方块本身不掉落。
		this.setDefaultState(this.getStateManager().getDefaultState()
				.with(LeavesBlock.PERSISTENT, true));
	}

	/**
	 * 启用随机刻，用于模拟结果掉落。
	 */
	@Override
	public boolean hasRandomTicks(BlockState state) {
		return true;
	}

	/**
	 * 随机刻：模拟"结果"阶段，在树叶位置生成水果掉落物。
	 * 仅在服务端执行，保证掉落物刷新。
	 */
	@Override
	public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
		super.randomTick(state, world, pos, random);
		// 5% 概率结果
		if (random.nextFloat() < 0.05f) {
			dropFruit(world, pos);
		}
	}

	/**
	 * 在树叶位置生成一个成熟水果掉落物实体。
	 */
	private void dropFruit(ServerWorld world, BlockPos pos) {
		Item fruit = Registries.ITEM.get(fruitId);
		if (fruit == null) {
			return;
		}
		ItemEntity itemEntity = new ItemEntity(
				world,
				pos.getX() + 0.5,
				pos.getY(),
				pos.getZ() + 0.5,
				new ItemStack(fruit, 1));
		world.spawnEntity(itemEntity);
	}
}