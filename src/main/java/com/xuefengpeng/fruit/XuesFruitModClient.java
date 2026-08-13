package com.xuefengpeng.fruit;

import com.xuefengpeng.fruit.screen.JuicerScreen;
import com.xuefengpeng.fruit.screen.DryerScreen;
import com.xuefengpeng.fruit.screen.ModScreenHandlers;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screen.ingame.HandledScreens;

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
	}
}