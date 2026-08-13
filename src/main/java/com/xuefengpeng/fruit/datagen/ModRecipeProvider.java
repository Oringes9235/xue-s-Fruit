package com.xuefengpeng.fruit.datagen;

import com.xuefengpeng.fruit.XuesFruitMod;
import com.xuefengpeng.fruit.block.ModBlocks;
import com.xuefengpeng.fruit.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.util.Identifier;

import java.util.function.Consumer;

/**
 * ============================================================
 * 配方生成器（Minecraft 1.20.1 API）
 * ============================================================
 * 生成工作台合成配方。榨汁/烘干配方 JSON 已由脚本生成。
 */
public class ModRecipeProvider extends FabricRecipeProvider {

	public ModRecipeProvider(FabricDataOutput output) {
		super(output);
	}

	/**
	 * 生成所有合成配方。
	 */
	@Override
	public void generate(Consumer<RecipeJsonProvider> exporter) {
		// 水果沙拉：橙子 + 草莓 + 蓝莓 + 碗
		ShapelessRecipeJsonBuilder.create(RecipeCategory.FOOD, ModItems.FRUIT_SALAD, 1)
				.input(ModItems.ORANGE)
				.input(ModItems.STRAWBERRY)
				.input(ModItems.BLUEBERRY)
				.input(Items.BOWL)
				.criterion(FabricRecipeProvider.hasItem(ModItems.ORANGE),
						FabricRecipeProvider.conditionsFromItem(ModItems.ORANGE))
				.offerTo(exporter, new Identifier(getRecipeName(ModItems.FRUIT_SALAD)));

		// 果酱：香蕉 + 糖 + 玻璃瓶
		ShapelessRecipeJsonBuilder.create(RecipeCategory.FOOD, ModItems.FRUIT_JAM, 1)
				.input(ModItems.BANANA)
				.input(Items.SUGAR)
				.input(Items.GLASS_BOTTLE)
				.criterion(FabricRecipeProvider.hasItem(ModItems.BANANA),
						FabricRecipeProvider.conditionsFromItem(ModItems.BANANA))
				.offerTo(exporter, new Identifier(getRecipeName(ModItems.FRUIT_JAM)));

		// 水果蛋糕：水果沙拉 + 小麦 + 糖
		ShapelessRecipeJsonBuilder.create(RecipeCategory.FOOD, ModItems.FRUIT_CAKE, 1)
				.input(ModItems.FRUIT_SALAD)
				.input(Items.WHEAT)
				.input(Items.SUGAR)
				.criterion(FabricRecipeProvider.hasItem(ModItems.FRUIT_SALAD),
						FabricRecipeProvider.conditionsFromItem(ModItems.FRUIT_SALAD))
				.offerTo(exporter, new Identifier(getRecipeName(ModItems.FRUIT_CAKE)));

		// 榨汁机
		ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.BLOCK_ITEMS.get("juicer"), 1)
				.pattern("III")
				.pattern("IBI")
				.pattern("III")
				.input('I', Items.IRON_INGOT)
				.input('B', Items.BUCKET)
				.criterion(FabricRecipeProvider.hasItem(Items.IRON_INGOT),
						FabricRecipeProvider.conditionsFromItem(Items.IRON_INGOT))
				.offerTo(exporter, XuesFruitMod.id("juicer"));

		// 烘干机
		ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.BLOCK_ITEMS.get("dryer"), 1)
				.pattern("III")
				.pattern("IFI")
				.pattern("III")
				.input('I', Items.IRON_INGOT)
				.input('F', Items.FURNACE)
				.criterion(FabricRecipeProvider.hasItem(Items.IRON_INGOT),
						FabricRecipeProvider.conditionsFromItem(Items.IRON_INGOT))
				.offerTo(exporter, XuesFruitMod.id("dryer"));

		// 发酵桶
		ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.BLOCK_ITEMS.get("fermentation_barrel"), 1)
				.pattern("WWW")
				.pattern("WGW")
				.pattern("WWW")
				.input('W', Items.OAK_PLANKS)
				.input('G', Items.GLASS_BOTTLE)
				.criterion(FabricRecipeProvider.hasItem(Items.OAK_PLANKS),
						FabricRecipeProvider.conditionsFromItem(Items.OAK_PLANKS))
				.offerTo(exporter, XuesFruitMod.id("fermentation_barrel"));
	}
}