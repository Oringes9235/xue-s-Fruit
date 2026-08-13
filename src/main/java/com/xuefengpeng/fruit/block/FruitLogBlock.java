package com.xuefengpeng.fruit.block;

import net.minecraft.block.PillarBlock;

/**
 * ============================================================
 * 果树原木
 * ============================================================
 * 继承原版柱状方块（PillarBlock），具有横向/纵向的纹理
 * 朝向，作为果树树干使用。破坏后掉落自身。
 */
public class FruitLogBlock extends PillarBlock {

	/**
	 * 构造果树原木方块。
	 *
	 * @param settings 方块设置（硬度、工具类型等）
	 */
	public FruitLogBlock(Settings settings) {
		super(settings);
	}
}