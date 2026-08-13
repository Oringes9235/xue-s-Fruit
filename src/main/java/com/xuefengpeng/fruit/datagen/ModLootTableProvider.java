package com.xuefengpeng.fruit.datagen;

import com.xuefengpeng.fruit.block.ModBlocks;
import com.xuefengpeng.fruit.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

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

		// 果树方块：原木 / 树苗掉落自身；树叶掉落对应水果而非树叶方块
		for (String fruit : ModBlocks.FRUITS) {
			Block log = ModBlocks.BLOCKS.get(fruit + "_log");
			Block sapling = ModBlocks.BLOCKS.get(fruit + "_sapling");
			Block leaves = ModBlocks.BLOCKS.get(fruit + "_leaves");

			addDrop(log);
			addDrop(sapling);

			// 树叶不会掉落自身，只掉落成熟水果
			Item fruitItem = ModItems.ITEMS.get(fruit);
			if (fruitItem != null) {
				addDrop(leaves, fruitItem);
			}
		}
	}
}