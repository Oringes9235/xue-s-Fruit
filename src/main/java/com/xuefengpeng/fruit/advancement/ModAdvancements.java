package com.xuefengpeng.fruit.advancement;

import com.xuefengpeng.fruit.XuesFruitMod;
import net.minecraft.advancement.Advancement;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

/**
 * ============================================================
 * 成就系统（Minecraft 1.20.1 API）
 * ============================================================
 * 定义本模组的核心成就 ID 并由代码触发。
 */
public final class ModAdvancements {

	private ModAdvancements() {
	}

	/** 种植第一棵果树 */
	public static final Identifier PLANT_FIRST_TREE = XuesFruitMod.id("plant_first_tree");
	/** 品尝所有水果 */
	public static final Identifier TASTE_ALL_FRUITS = XuesFruitMod.id("taste_all_fruits");
	/** 制作终极水果沙拉 */
	public static final Identifier ULTIMATE_FRUIT_SALAD = XuesFruitMod.id("ultimate_fruit_salad");
	/** 收集所有果树 */
	public static final Identifier COLLECT_ALL_TREES = XuesFruitMod.id("collect_all_trees");

	/**
	 * 授予指定玩家某成就（忽略已授予情况）。
	 */
	public static void grant(ServerPlayerEntity player, Identifier advancementId) {
		Advancement advancement = player.getServer().getAdvancementLoader().get(advancementId);
		if (advancement != null) {
			player.getAdvancementTracker().grantCriterion(advancement, "done");
		}
	}
}