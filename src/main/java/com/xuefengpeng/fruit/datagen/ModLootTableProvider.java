package com.xuefengpeng.fruit.datagen;

import com.xuefengpeng.fruit.block.ModBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;

/**
 * ============================================================
 * 战利品表生成器（Minecraft 1.20.1 API）
 * ============================================================
 * 为所有方块生成默认战利品表（掉落自身）。
 */
public class ModLootTableProvider extends FabricBlockLootTableProvider {

	public ModLootTableProvider(FabricDataOutput output) {
		super(output);
	}

	@Override
	public void generate() {
		// 机器方块：掉落自身
		addDrop(ModBlocks.JUICER);
		addDrop(ModBlocks.DRYER);
		addDrop(ModBlocks.FERMENTATION_BARREL);

		// 果树方块：掉落自身
		for (String fruit : ModBlocks.FRUITS) {
			addDrop(ModBlocks.BLOCKS.get(fruit + "_log"));
			addDrop(ModBlocks.BLOCKS.get(fruit + "_sapling"));
			addDrop(ModBlocks.BLOCKS.get(fruit + "_leaves"));
		}
	}
}