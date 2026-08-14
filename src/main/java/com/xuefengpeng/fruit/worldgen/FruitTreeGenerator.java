package com.xuefengpeng.fruit.worldgen;

import com.xuefengpeng.fruit.block.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldAccess;

/**
 * 果树生成工具：在世界中放置一棵正常规模的果树（树干 + 多层大型树冠）。
 * 世界生成的 Feature 与树苗骨粉催熟共用此逻辑，保证果树形状一致。
 */
public final class FruitTreeGenerator {

	/** 树干高度（方块数）。 */
	private static final int TRUNK_HEIGHT = 5;

	private FruitTreeGenerator() {
	}

	/**
	 * 在给定位置放置一棵果树。若上方空间不足则返回 false。
	 *
	 * @param world  可编辑世界
	 * @param origin 树干底部位置（即树苗所在位置）
	 * @param fruit  水果名称
	 * @return 是否成功生成
	 */
	public static boolean generate(WorldAccess world, BlockPos origin, String fruit) {
		BlockState trunk = ModBlocks.BLOCKS.get(fruit + "_log").getDefaultState();
		if (trunk == null) {
			return false;
		}
		BlockState leaves = ModBlocks.BLOCKS.get(fruit + "_leaves").getDefaultState();

		// 树干顶部方块（也是树冠的中心锚点）
		BlockPos top = origin.up(TRUNK_HEIGHT - 1);

		// 空间检查：树干所处位置需为空气或可替换方块。
		// 注意：origin 本身是树苗（将被替换为原木），从 y=1 开始检查。
		for (int y = 1; y < TRUNK_HEIGHT; y++) {
			if (!canReplace(world.getBlockState(origin.up(y)))) {
				return false;
			}
		}

		// 空间检查：树冠所处位置需为空气或可替换方块
		for (int dy = -1; dy <= 3; dy++) {
			for (int dx = -2; dx <= 2; dx++) {
				for (int dz = -2; dz <= 2; dz++) {
					if (isCanopy(dx, dy, dz)) {
						BlockPos p = top.add(dx, dy, dz);
						if (!canReplace(world.getBlockState(p))) {
							return false;
						}
					}
				}
			}
		}

		// 树干
		for (int y = 0; y < TRUNK_HEIGHT; y++) {
			world.setBlockState(origin.up(y), trunk, 3);
		}

		// 树冠（多层，直径 5，形成类似原版橡树的蓬松大树冠）
		for (int dy = -1; dy <= 3; dy++) {
			for (int dx = -2; dx <= 2; dx++) {
				for (int dz = -2; dz <= 2; dz++) {
					if (isCanopy(dx, dy, dz)) {
						world.setBlockState(top.add(dx, dy, dz), leaves, 3);
					}
				}
			}
		}

		return true;
	}

	private static boolean canReplace(BlockState state) {
		return state.isAir() || state.isReplaceable();
	}

	/**
	 * 判断相对树冠锚点的某个偏移是否应放置树叶。
	 * 形成“包裹树干上部 + 5x5 圆角两层 + 3x3 顶层 + 顶梢”的树冠。
	 */
	private static boolean isCanopy(int dx, int dy, int dz) {
		int ax = Math.abs(dx);
		int az = Math.abs(dz);
		switch (dy) {
			case -1:
				// 包裹树干倒数第二格：十字，不含中心与四角
				return (ax == 1 && az == 0) || (az == 1 && ax == 0);
			case 0:
				// 树干顶周围：5x5 去四角、去中心（中心被树干占据）
				return (ax != 0 || az != 0) && ax <= 2 && az <= 2 && (ax != 2 || az != 2);
			case 1:
				// 树冠第二层：5x5 去四角
				return ax <= 2 && az <= 2 && (ax != 2 || az != 2);
			case 2:
				// 树冠第三层：3x3
				return ax <= 1 && az <= 1;
			case 3:
				// 顶梢：单块
				return ax == 0 && az == 0;
			default:
				return false;
		}
	}
}