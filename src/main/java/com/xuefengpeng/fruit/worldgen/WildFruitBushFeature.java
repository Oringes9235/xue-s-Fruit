package com.xuefengpeng.fruit.worldgen;

import com.mojang.serialization.Codec;
import com.xuefengpeng.fruit.block.FruitBushBlock;
import com.xuefengpeng.fruit.block.ModBlocks;
import net.minecraft.block.Block;
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
 * 在世界表面生成低矮的野生水果灌木（成熟阶段）。
 * 灌木使用 FruitBushBlock，可右键采摘水果。
 */
public class WildFruitBushFeature extends Feature<DefaultFeatureConfig> {

	private final String fruit;

	public WildFruitBushFeature(Codec<DefaultFeatureConfig> codec, String fruit) {
		super(codec);
		this.fruit = fruit;
	}

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

		Block baseBlock = ModBlocks.BLOCKS.get(fruit + "_sapling");
		if (baseBlock == null) {
			return false;
		}
		BlockState bushState = baseBlock.getDefaultState().with(FruitBushBlock.AGE, 3);
		world.setBlockState(origin, bushState, 0b11);
		return true;
	}

	private boolean isSoil(StructureWorldAccess world, BlockPos pos) {
		BlockState state = world.getBlockState(pos);
		return state.isOf(Blocks.GRASS_BLOCK) || state.isOf(Blocks.DIRT);
	}
}