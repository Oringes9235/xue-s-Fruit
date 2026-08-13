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
	 * 现实中为乔木类的水果，在世界中生成果树。
	 * 其余水果（葡萄、草莓、火龙果、猕猴桃、蓝莓）为灌木/藤本，
	 * 只生成野生果丛，不生成树。
	 */
	public static final String[] TREE_FRUITS = {
			"banana", "orange", "mango", "durian", "lychee"
	};

	/**
	 * 现实中为灌木/藤本的水果，只生成野生果丛。
	 */
	public static final String[] BUSH_FRUITS = {
			"grape", "strawberry", "dragon_fruit", "kiwi", "blueberry"
	};

	/**
	 * 主注册入口：注册所有 Feature，并将其加入主世界生成。
	 */
	public static void register() {
		// 注册全部 Feature 类型（树木 + 果丛），
		// 确保 data 目录中已有的 configured_feature 均能正常解析，避免缺失引用。
		for (String fruit : ModBlocks.FRUITS) {
			registerFeature(fruit + "_tree",
					new FruitTreeFeature(DefaultFeatureConfig.CODEC, fruit));
			registerFeature(fruit + "_wild_bush",
					new WildFruitBushFeature(DefaultFeatureConfig.CODEC, fruit));
		}

		// 仅按现实形态注入世界生成：
		// 乔木类水果生成果树，灌木/藤本类水果只生成野生果丛。
		for (String fruit : TREE_FRUITS) {
			addToOverworld(fruit + "_tree");
		}
		for (String fruit : BUSH_FRUITS) {
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