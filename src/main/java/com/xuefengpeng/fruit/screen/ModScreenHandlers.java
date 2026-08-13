package com.xuefengpeng.fruit.screen;

import com.xuefengpeng.fruit.XuesFruitMod;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;

/**
 * ============================================================
 * 屏幕处理器注册（Minecraft 1.20.1 API）
 * ============================================================
 */
public final class ModScreenHandlers {

	private ModScreenHandlers() {
	}

	/** 榨汁机屏幕处理器类型 */
	public static final ScreenHandlerType<JuicerScreenHandler> JUICER_SCREEN_HANDLER =
			register("juicer", new ScreenHandlerType<>(
					JuicerScreenHandler::new, FeatureFlags.VANILLA_FEATURES));

	/** 烘干机屏幕处理器类型 */
	public static final ScreenHandlerType<DryerScreenHandler> DRYER_SCREEN_HANDLER =
			register("dryer", new ScreenHandlerType<>(
					DryerScreenHandler::new, FeatureFlags.VANILLA_FEATURES));

	/**
	 * 主注册入口（静态字段已完成注册）。
	 */
	public static void register() {
	}

	/**
	 * 注册单个屏幕处理器类型。
	 */
	private static <T extends ScreenHandler> ScreenHandlerType<T> register(String name, ScreenHandlerType<T> type) {
		return Registry.register(Registries.SCREEN_HANDLER, XuesFruitMod.id(name), type);
	}
}