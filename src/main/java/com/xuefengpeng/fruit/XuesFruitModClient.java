package com.xuefengpeng.fruit;

import com.xuefengpeng.fruit.block.FruitBushBlock;
import com.xuefengpeng.fruit.block.FruitLeavesBlock;
import com.xuefengpeng.fruit.block.FruitSaplingBlock;
import com.xuefengpeng.fruit.block.ModBlocks;
import com.xuefengpeng.fruit.screen.JuicerScreen;
import com.xuefengpeng.fruit.screen.DryerScreen;
import com.xuefengpeng.fruit.screen.ModScreenHandlers;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.block.Block;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.RenderLayer;

/**
 * ============================================================
 * Xue's Fruit - 客户端入口
 * ============================================================
 * 仅在客户端环境执行。负责将屏幕处理器(GUI 逻辑)
 * 与屏幕渲染类(GUI 视觉)绑定。
 */
public class XuesFruitModClient implements ClientModInitializer {

	/**
	 * 客户端初始化入口。
	 * 将榨汁机、烘干机的屏幕处理器与渲染实现一一配对。
	 */
	@Override
	public void onInitializeClient() {
		// 榨汁机 GUI
		HandledScreens.register(ModScreenHandlers.JUICER_SCREEN_HANDLER, JuicerScreen::new);
		// 烘干机 GUI
		HandledScreens.register(ModScreenHandlers.DRYER_SCREEN_HANDLER, DryerScreen::new);

		// 注册透明方块的渲染层，避免 cross 模型（树苗/灌木）透明区域被渲染成黑色
		for (Block block : ModBlocks.BLOCKS.values()) {
			if (block instanceof FruitSaplingBlock || block instanceof FruitBushBlock) {
				// 树苗/灌木：十字模型，使用 cutout 裁剪透明像素
				BlockRenderLayerMap.INSTANCE.putBlock(block, RenderLayer.getCutout());
			} else if (block instanceof FruitLeavesBlock) {
				// 树叶：与原版一致使用 cutout_mipped，保留平滑边缘
				BlockRenderLayerMap.INSTANCE.putBlock(block, RenderLayer.getCutoutMipped());
			}
		}
	}
}
