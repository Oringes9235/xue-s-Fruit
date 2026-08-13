package com.xuefengpeng.fruit.worldgen;

import com.mojang.serialization.Codec;
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
	 * 生成树：在给定位置放置树干与顶部树冠。
	 */
	@Override
	public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
		StructureWorldAccess world = context.getWorld();
		BlockPos origin = context.getOrigin();
		BlockPos basePos = origin.down();

		// 检查地面是否为可种植土壤
		if (!isSoil(world, basePos)) {
			return false;
		}

		// 检查上方空间是否足够
		for (int y = 1; y <= TRUNK_HEIGHT + 1; y++) {
			if (!world.getBlockState(origin.up(y)).isAir()) {
				return false;
			}
		}

		// 放置树干
		BlockState trunkState = ModBlocks.BLOCKS.get(fruit + "_log").getDefaultState();
		for (int y = 0; y < TRUNK_HEIGHT; y++) {
			world.setBlockState(origin.up(y), trunkState, 0b11);
		}

		// 放置树冠（3x3 树叶，顶部一层）
		BlockState leavesState = ModBlocks.BLOCKS.get(fruit + "_leaves").getDefaultState();
		BlockPos top = origin.up(TRUNK_HEIGHT);
		for (int dx = -1; dx <= 1; dx++) {
			for (int dz = -1; dz <= 1; dz++) {
				if (Math.abs(dx) == 1 && Math.abs(dz) == 1) {
					continue; // 四角留空
				}
				world.setBlockState(top.add(dx, 0, dz), leavesState, 0b11);
			}
		}
		// 顶部中心的上方再盖一层叶子
		world.setBlockState(top.up(), leavesState, 0b11);

		return true;
	}

	/**
	 * 判断是否为可种植土壤。
	 */
	private boolean isSoil(StructureWorldAccess world, BlockPos pos) {
		BlockState state = world.getBlockState(pos);
		return state.isOf(Blocks.GRASS_BLOCK) || state.isOf(Blocks.DIRT) || state.isOf(Blocks.FARMLAND);
	}
}