package com.xuefengpeng.fruit.worldgen;

import com.mojang.serialization.Codec;
import com.xuefengpeng.fruit.block.FruitSaplingBlock;
import com.xuefengpeng.fruit.block.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

/**
 * ============================================================
 * 野生水果灌木特性
 * ============================================================
 * 在世界表面生成低矮的野生水果灌木。
 * 灌木直接使用结果阶段(4)的树苗方块，
 * 因此会周期性地在周围掉落水果。
 */
public class WildFruitBushFeature extends Feature<DefaultFeatureConfig> {

	/** 灌木对应的水果名称 */
	private final String fruit;

	public WildFruitBushFeature(Codec<DefaultFeatureConfig> codec, String fruit) {
		super(codec);
		this.fruit = fruit;
	}

	/**
	 * 生成灌木：检查地表并放置结果阶段的树苗。
	 */
	@Override
	public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
		StructureWorldAccess world = context.getWorld();
		BlockPos origin = context.getOrigin();
		BlockPos base = origin.down();

		if (!isSoil(world, base)) {
			return false;
		}
		if (!world.getBlockState(origin).isAir()) {
			return false;
		}

		// 延迟绑定：在生成时刻读取已注册的树苗方块
		BlockState bushState = ModBlocks.BLOCKS.get(fruit + "_sapling").getDefaultState()
				.with(FruitSaplingBlock.GROWTH, 4);
		world.setBlockState(origin, bushState, 0b11);
		return true;
	}

	private boolean isSoil(StructureWorldAccess world, BlockPos pos) {
		BlockState state = world.getBlockState(pos);
		return state.isOf(Blocks.GRASS_BLOCK) || state.isOf(Blocks.DIRT);
	}
}