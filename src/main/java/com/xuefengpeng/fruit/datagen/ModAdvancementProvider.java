package com.xuefengpeng.fruit.datagen;

import com.xuefengpeng.fruit.advancement.ModAdvancements;
import com.xuefengpeng.fruit.block.ModBlocks;
import com.xuefengpeng.fruit.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementFrame;
import net.minecraft.advancement.criterion.InventoryChangedCriterion;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.function.Consumer;

/**
 * ============================================================
 * 成就生成器（Minecraft 1.20.1 API）
 * ============================================================
 * 生成 4 个核心成就。
 */
public class ModAdvancementProvider extends FabricAdvancementProvider {

	public ModAdvancementProvider(FabricDataOutput output) {
		super(output);
	}

	@Override
	public void generateAdvancement(Consumer<Advancement> consumer) {
		// 1. 种植第一棵果树
		Advancement plantFirstTree = Advancement.Builder.create()
				.display(
						ModBlocks.BLOCK_ITEMS.get("banana_sapling").getDefaultStack(),
						Text.translatable("advancements.xuesfruit.plant_first_tree.title"),
						Text.translatable("advancements.xuesfruit.plant_first_tree.description"),
						(Identifier) null,
						AdvancementFrame.TASK,
						true, true, false)
				.criterion("planted", InventoryChangedCriterion.Conditions.items(
						ModBlocks.BLOCK_ITEMS.get("banana_sapling")))
				.build(consumer, ModAdvancements.PLANT_FIRST_TREE.toString());

		// 2. 品尝所有水果
		Advancement tasteAllFruits = Advancement.Builder.create()
				.display(
						ModItems.FRUIT_SALAD.getDefaultStack(),
						Text.translatable("advancements.xuesfruit.taste_all_fruits.title"),
						Text.translatable("advancements.xuesfruit.taste_all_fruits.description"),
						(Identifier) null,
						AdvancementFrame.GOAL,
						true, true, false)
				.criterion("tasted", InventoryChangedCriterion.Conditions.items(ModItems.FRUIT_SALAD))
				.build(consumer, ModAdvancements.TASTE_ALL_FRUITS.toString());

		// 3. 制作终极水果沙拉
		Advancement ultimateSalad = Advancement.Builder.create()
				.display(
						ModItems.FRUIT_SALAD.getDefaultStack(),
						Text.translatable("advancements.xuesfruit.ultimate_fruit_salad.title"),
						Text.translatable("advancements.xuesfruit.ultimate_fruit_salad.description"),
						(Identifier) null,
						AdvancementFrame.CHALLENGE,
						true, true, false)
				.criterion("made", InventoryChangedCriterion.Conditions.items(ModItems.FRUIT_SALAD))
				.build(consumer, ModAdvancements.ULTIMATE_FRUIT_SALAD.toString());

		// 4. 收集所有果树
		Advancement collectAllTrees = Advancement.Builder.create()
				.display(
						ModBlocks.BLOCK_ITEMS.get("dragon_fruit_log").getDefaultStack(),
						Text.translatable("advancements.xuesfruit.collect_all_trees.title"),
						Text.translatable("advancements.xuesfruit.collect_all_trees.description"),
						(Identifier) null,
						AdvancementFrame.GOAL,
						true, true, false)
				.criterion("collected", InventoryChangedCriterion.Conditions.items(
						ModBlocks.BLOCK_ITEMS.get("banana_log"),
						ModBlocks.BLOCK_ITEMS.get("orange_log")))
				.build(consumer, ModAdvancements.COLLECT_ALL_TREES.toString());
	}
}