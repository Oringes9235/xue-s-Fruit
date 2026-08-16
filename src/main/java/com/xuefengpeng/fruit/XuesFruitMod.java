package com.xuefengpeng.fruit;

import com.xuefengpeng.fruit.block.ModBlocks;
import com.xuefengpeng.fruit.blockentity.ModBlockEntities;
import com.xuefengpeng.fruit.effect.ModEffects;
import com.xuefengpeng.fruit.entity.ModEntities;
import com.xuefengpeng.fruit.item.ModItems;
import com.xuefengpeng.fruit.recipe.ModRecipes;
import com.xuefengpeng.fruit.screen.ModScreenHandlers;
import com.xuefengpeng.fruit.trade.ModVillagerTrades;
import com.xuefengpeng.fruit.worldgen.ModWorldGen;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ============================================================
 * Xue's Fruit - 模组主入口
 * ============================================================
 * 在 Fabric 加载器初始化模组时调用 onInitialize()。
 * 负责按序注册：
 *   1. 效果（自定义状态效果）
 *   2. 方块（果树/树苗/机器，先于物品注册）
 *   3. 物品（20 种食物物品，依赖效果与方块）
 *   4. 方块实体（机器逻辑）
 *   5. 屏幕处理器（GUI）
 *   6. 配方类型与序列化器
 *   7. 世界生成特性
 *   8. 村民交易
 * ============================================================
 */
public class XuesFruitMod implements ModInitializer {

	/** Mod ID，所有资源定位符的统一命名空间 */
	public static final String MOD_ID = "xuesfruit";

	/** 全局日志记录器，调试时输出信息 */
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	/**
	 * 为给定的路径片段构造一个属于本模组的资源定位符。
	 *
	 * @param path 资源路径（不含命名空间）
	 * @return 形如 "xuesfruit:path" 的 Identifier
	 */
	public static Identifier id(String path) {
		// Minecraft 1.20.1 使用传统构造器，而非 Identifier.of
		return new Identifier(MOD_ID, path);
	}

	/**
	 * Fabric 初始化入口。注册顺序有依赖关系，不可随意调整。
	 */
	@Override
	public void onInitialize() {
		LOGGER.info("正在初始化 Xue's Fruit ...");

		// 1. 自定义效果（水果效果系统依赖）
		ModEffects.register();

		// 2. 方块（先注册方块，树苗物品需要引用树苗方块实例）
		ModBlocks.register();

		// 3. 物品（食物物品需要引用效果，树苗物品需要引用方块）
		ModItems.register();
		// 物品组在物品注册后初始化
		ModItems.registerItemGroups();

		// 4. 方块实体
		ModBlockEntities.register();

		// 4.5 实体（自然掉落水果，需在客户端与服务端均完成注册）
		ModEntities.register();

		// 5. 屏幕处理器（GUI）
		ModScreenHandlers.register();

		// 6. 配方类型 / 序列化器
		ModRecipes.register();

		// 7. 世界生成（果树 + 野生果丛）
		ModWorldGen.register();

		// 8. 村民交易
		ModVillagerTrades.register();

		LOGGER.info("Xue's Fruit 初始化完成！");
	}
}