package com.xuefengpeng.fruit.worldgen;

import com.xuefengpeng.fruit.block.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldAccess;

/**
 * 果树生成工具：在世界中放置一棵小型果树（树干 + 较大树冠）。
 * 世界生成的 Feature 与树苗骨粉催熟共用此逻辑，保证果树形状一致。
 */
public final class FruitTreeGenerator {

	/** 树干高度（方块数），比原先生成的 3 更高，树冠更大。 */
	private static final int TRUNK_HEIGHT = 4;

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
		BlockState leaves = ModBlocks.BLOCKS.get(fruit + "_leaves").getDefaultState()
				.with(LeavesBlock.PERSISTENT, true);

		// 空间检查：树干与树冠所处位置需为空气或可替换方块
		for (int y = 1; y <= TRUNK_HEIGHT + 1; y++) {
			BlockState above = world.getBlockState(origin.up(y));
			if (!above.isAir() && !above.isReplaceable()) {
				return false;
			}
		}

		// 树干
		for (int y = 0; y < TRUNK_HEIGHT; y++) {
			world.setBlockState(origin.up(y), trunk, 3);
		}

		BlockPos top = origin.up(TRUNK_HEIGHT);

		// 树冠下层：3x3 满层
		for (int dx = -1; dx <= 1; dx++) {
			for (int dz = -1; dz <= 1; dz++) {
				world.setBlockState(top.add(dx, 0, dz), leaves, 3);
			}
		}
		// 树冠上层：十字形（四角留空），像白桦树一样有更蓬松的树冠
		BlockPos top2 = top.up();
		for (int dx = -1; dx <= 1; dx++) {
			for (int dz = -1; dz <= 1; dz++) {
				if (dx == 0 || dz == 0) {
					world.setBlockState(top2.add(dx, 0, dz), leaves, 3);
				}
			}
		}

		return true;
	}
}