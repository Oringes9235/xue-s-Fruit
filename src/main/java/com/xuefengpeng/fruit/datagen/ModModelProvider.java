package com.xuefengpeng.fruit.datagen;

import com.xuefengpeng.fruit.XuesFruitMod;
import com.xuefengpeng.fruit.block.ModBlocks;
import com.xuefengpeng.fruit.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;

import java.util.Map;

/**
 * ============================================================
 * 模型生成器
 * ============================================================
 * 自动为所有水果食物物品生成通用物品模型，
 * 为机器方块生成方块状态与方块模型。
 */
public class ModModelProvider extends FabricModelProvider {

	public ModModelProvider(FabricDataOutput output) {
		super(output);
	}

	/**
	 * 生成方块模型：机器方块使用立方体全模型。
	 */
	@Override
	public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
		// 机器方块：榨汁机 / 烘干机 / 发酵桶
		blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.JUICER);
		blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.DRYER);
		blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.FERMENTATION_BARREL);
	}

	/**
	 * 生成物品模型：所有食物物品使用通用物品模型（贴图与注册名一致）。
	 */
	@Override
	public void generateItemModels(ItemModelGenerator itemModelGenerator) {
		for (Map.Entry<String, net.minecraft.item.Item> entry : ModItems.ITEMS.entrySet()) {
			itemModelGenerator.register(entry.getValue(), Models.GENERATED);
		}
		// 方块物品模型由方块状态生成器自动处理
	}
}