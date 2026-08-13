package com.xuefengpeng.fruit.datagen;

import com.xuefengpeng.fruit.XuesFruitMod;
import com.xuefengpeng.fruit.block.ModBlocks;
import com.xuefengpeng.fruit.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.TagKey;

import java.util.concurrent.CompletableFuture;

/**
 * ============================================================
 * 物品标签生成器
 * ============================================================
 * 生成自定义物品标签：
 *   - fruit：所有 10 种水果
 *   - processed_food：所有加工食品
 */
public class ModItemTagProvider extends FabricTagProvider.ItemTagProvider {

	public ModItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
		super(output, completableFuture);
	}

	@Override
	protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
		// 自定义标签：水果
		TagKey<Item> fruitTag = TagKey.of(RegistryKeys.ITEM, XuesFruitMod.id("fruit"));
		var fruitBuilder = getOrCreateTagBuilder(fruitTag);
		for (String fruit : ModBlocks.ALL_FRUITS) {
			fruitBuilder.add(ModItems.ITEMS.get(fruit));
		}

		// 自定义标签：加工食品
		TagKey<Item> processedTag = TagKey.of(RegistryKeys.ITEM, XuesFruitMod.id("processed_food"));
		var processedBuilder = getOrCreateTagBuilder(processedTag);
		processedBuilder.add(ModItems.FRUIT_JUICE);
		processedBuilder.add(ModItems.FRUIT_JAM);
		processedBuilder.add(ModItems.FRUIT_SALAD);
		processedBuilder.add(ModItems.FRUIT_CAKE);
		processedBuilder.add(ModItems.FRUIT_WINE);
		for (String fruit : ModBlocks.ALL_FRUITS) {
			processedBuilder.add(ModItems.driedItemFor(fruit));
		}
	}
}