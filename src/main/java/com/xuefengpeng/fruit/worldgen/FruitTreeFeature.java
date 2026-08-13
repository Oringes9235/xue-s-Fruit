package com.xuefengpeng.fruit.worldgen;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

/**
 * ============================================================
 * 果树生成特性
 * ============================================================
 * 在世界中生成小型果树：树干 + 顶部树叶，
 * 树叶使用结果阶段逻辑，可周期性掉落水果。
 */
public class FruitTreeFeature extends Feature<DefaultFeatureConfig> {

	/** 对应的水果名称（用于关联原木与树叶方块） */
	private final String fruit;

	/** 树干高度（方块数） */
	private static final int TRUNK_HEIGHT = 3;

	public FruitTreeFeature(Codec<DefaultFeatureConfig> codec, String fruit) {
		super(codec);
		this.fruit = fruit;
	}

	/**
	 * 生成树：复用 FruitTreeGenerator，生成树干 + 较大树冠。
	 */
	@Override
	public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
		StructureWorldAccess world = context.getWorld();
		BlockPos origin = context.getOrigin();
		BlockPos basePos = origin.down();

		if (!isSoil(world, basePos)) {
			return false;
		}

		return FruitTreeGenerator.generate(world, origin, fruit);
	}

	/**
	 * 判断是否为可种植土壤。
	 */
	private boolean isSoil(StructureWorldAccess world, BlockPos pos) {
		BlockState state = world.getBlockState(pos);
		return state.isOf(Blocks.GRASS_BLOCK) || state.isOf(Blocks.DIRT) || state.isOf(Blocks.FARMLAND);
	}
}