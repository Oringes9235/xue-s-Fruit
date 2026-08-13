package com.xuefengpeng.fruit.datagen;

import com.xuefengpeng.fruit.block.ModBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;

import java.util.concurrent.CompletableFuture;

/**
 * ============================================================
 * 方块标签生成器
 * ============================================================
 * 生成原木/树叶相关标签，确保：
 *   - 原木可被斧头加速挖掘
 *   - 树叶可被剪刀/附魔采集
 */
public class ModBlockTagProvider extends FabricTagProvider.BlockTagProvider {

	public ModBlockTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
		super(output, registriesFuture);
	}

	@Override
	protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
		// 所有原木加入 minecraft:logs 标签
		var logsBuilder = getOrCreateTagBuilder(BlockTags.LOGS);
		// 所有树叶加入 minecraft:leaves 标签
		var leavesBuilder = getOrCreateTagBuilder(BlockTags.LEAVES);

		for (String fruit : ModBlocks.FRUITS) {
			logsBuilder.add(ModBlocks.BLOCKS.get(fruit + "_log"));
			leavesBuilder.add(ModBlocks.BLOCKS.get(fruit + "_leaves"));
		}
	}
}