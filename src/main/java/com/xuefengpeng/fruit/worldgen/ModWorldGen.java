package com.xuefengpeng.fruit.worldgen;

import com.xuefengpeng.fruit.XuesFruitMod;
import com.xuefengpeng.fruit.block.ModBlocks;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * ============================================================
 * 世界生成主类
 * ============================================================
 * 负责：
 *   1. 为每种水果注册果树 Feature 与野生灌木 Feature
 *   2. 通过 Fabric API 的 BiomeModifications 将特性注入主世界群系
 *
 * 树的生成形状由 FruitTreeFeature 类实现，
 * 群系放置规则与密度通过 DataGen 生成的
 * configured_feature / placed_feature JSON 控制。
 */
public final class ModWorldGen {

	private ModWorldGen() {
	}

	/** 所有已注册的 Feature（路径 -> Feature），供 DataGen 引用 */
	public static final Map<String, Feature<DefaultFeatureConfig>> FEATURES = new LinkedHashMap<>();

	/**
	 * 主注册入口：注册所有 Feature，并将其加入主世界生成。
	 */
	public static void register() {
		for (String fruit : ModBlocks.FRUITS) {
			String treeFeature = fruit + "_tree";
			String bushFeature = fruit + "_wild_bush";

			// 注册果树 Feature（fruit 参数用于关联原木与树叶方块）
			Feature<DefaultFeatureConfig> tree = new FruitTreeFeature(DefaultFeatureConfig.CODEC, fruit);
			registerFeature(treeFeature, tree);

			// 注册野生灌木 Feature
			Feature<DefaultFeatureConfig> bush = new WildFruitBushFeature(DefaultFeatureConfig.CODEC, fruit);
			registerFeature(bushFeature, bush);
		}

		// 将果树/灌木特性注入所有主世界群系（植被装饰阶段）
		for (String fruit : ModBlocks.FRUITS) {
			addToOverworld(fruit + "_tree");
			addToOverworld(fruit + "_wild_bush");
		}
	}

	/**
	 * 注册单个 Feature。
	 */
	private static void registerFeature(String name, Feature<DefaultFeatureConfig> feature) {
		Registry.register(Registries.FEATURE, XuesFruitMod.id(name), feature);
		FEATURES.put(name, feature);
	}

	/**
	 * 将指定的 PlacedFeature（由 DataGen JSON 定义）加入主世界生成。
	 * PlacedFeature 的 RegistryKey 与生成 JSON 文件名对应。
	 */
	private static void addToOverworld(String featureName) {
		RegistryKey<net.minecraft.world.gen.feature.PlacedFeature> placedFeatureKey =
				RegistryKey.of(RegistryKeys.PLACED_FEATURE, XuesFruitMod.id(featureName + "_placed"));

		BiomeModifications.addFeature(
				BiomeSelectors.foundInOverworld(),
				GenerationStep.Feature.VEGETAL_DECORATION,
				placedFeatureKey);
	}
}