package com.xuefengpeng.fruit.datagen;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

/**
 * ============================================================
 * 数据生成入口
 * ============================================================
 * Fabric 数据生成入口点。运行 `gradlew runDatagen` 时调用。
 * 注册本模组各类数据生成器，自动产出：
 *   - 物品/方块模型 JSON
 *   - 配方 JSON
 *   - 战利品表 JSON
 *   - 方块/物品标签 JSON
 *   - 成就 JSON
 */
public class ModDataGenerator implements DataGeneratorEntrypoint {

	/**
	 * 数据生成入口。
	 */
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

		// 方块/物品模型
		pack.addProvider(ModModelProvider::new);
		// 配方
		pack.addProvider(ModRecipeProvider::new);
		// 战利品表
		pack.addProvider(ModLootTableProvider::new);
		// 方块标签
		pack.addProvider(ModBlockTagProvider::new);
		// 物品标签
		pack.addProvider(ModItemTagProvider::new);
		// 成就
		pack.addProvider(ModAdvancementProvider::new);
	}
}