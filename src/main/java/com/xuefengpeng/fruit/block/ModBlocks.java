package com.xuefengpeng.fruit.block;

import com.xuefengpeng.fruit.XuesFruitMod;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.MapColor;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * ============================================================
 * 方块注册
 * ============================================================
 * 集中注册本模组所有方块：
 *   - 5 种乔木类果树方块（树苗 + 树叶 + 原木）
 *   - 5 种灌木/藤本类水果（果丛，无原木与树叶）
 *   - 3 台加工机器（榨汁机 / 烘干机 / 发酵桶）
 */
public final class ModBlocks {

	private ModBlocks() {
	}

	/** 所有已注册方块（路径 -> 方块），供 DataGen 与标签使用 */
	public static final Map<String, Block> BLOCKS = new LinkedHashMap<>();
	/** 所有已注册方块物品（路径 -> 物品），供物品组与物品注册使用 */
	public static final Map<String, BlockItem> BLOCK_ITEMS = new LinkedHashMap<>();

	/** 机器方块实例 */
	public static final Block JUICER = registerMachine("juicer", new JuicerBlock(AbstractBlock.Settings.create()
			.mapColor(MapColor.IRON_GRAY).strength(3.5f).sounds(BlockSoundGroup.METAL)
			.pistonBehavior(PistonBehavior.BLOCK)));

	public static final Block DRYER = registerMachine("dryer", new DryerBlock(AbstractBlock.Settings.create()
			.mapColor(MapColor.IRON_GRAY).strength(3.5f).sounds(BlockSoundGroup.METAL)
			.pistonBehavior(PistonBehavior.BLOCK)));

	public static final Block FERMENTATION_BARREL = registerMachine("fermentation_barrel",
			new FermentationBarrelBlock(AbstractBlock.Settings.create()
					.mapColor(MapColor.OAK_TAN).strength(2.0f).sounds(BlockSoundGroup.WOOD)
					.pistonBehavior(PistonBehavior.BLOCK)));

	/**
	 * 现实中为乔木类的水果，拥有树苗 + 树叶 + 原木，可长成树。
	 */
	public static final String[] TREE_FRUITS = {
			"banana", "orange", "mango", "durian", "lychee"
	};

	/**
	 * 现实中为灌木/藤本类的水果，只有果丛，无原木与树叶。
	 */
	public static final String[] BUSH_FRUITS = {
			"grape", "strawberry", "dragon_fruit", "kiwi", "blueberry"
	};

	/**
	 * 全部 10 种水果（与物品注册顺序一致），供交易、标签、世界生成等遍历使用。
	 */
	public static final String[] ALL_FRUITS = {
			"banana", "orange", "grape", "mango", "strawberry",
			"dragon_fruit", "durian", "lychee", "kiwi", "blueberry"
	};

	/**
	 * 主注册入口。
	 */
	public static void register() {
		for (String fruit : TREE_FRUITS) {
			registerTreeBlocks(fruit);
		}
		for (String fruit : BUSH_FRUITS) {
			registerBushBlock(fruit);
		}
	}

	/**
	 * 为乔木类水果注册树苗、树叶、原木三个方块。
	 */
	private static void registerTreeBlocks(String fruit) {
		Identifier fruitId = XuesFruitMod.id(fruit);

		// 树苗方块（可长成树）
		FruitSaplingBlock sapling = new FruitSaplingBlock(
				AbstractBlock.Settings.create()
						.mapColor(MapColor.DARK_GREEN)
						.noCollision()
						.ticksRandomly()
						.breakInstantly()
						.sounds(BlockSoundGroup.GRASS),
				fruitId, true);
		register(fruit + "_sapling", sapling);

		// 树叶方块
		FruitLeavesBlock leaves = new FruitLeavesBlock(
				AbstractBlock.Settings.create()
						.mapColor(MapColor.GREEN)
						.strength(0.2f)
						.ticksRandomly()
						.sounds(BlockSoundGroup.GRASS)
						.nonOpaque(),
				fruitId);
		register(fruit + "_leaves", leaves);

		// 原木方块
		FruitLogBlock log = new FruitLogBlock(
				AbstractBlock.Settings.create()
						.mapColor(MapColor.BROWN)
						.strength(2.0f)
						.sounds(BlockSoundGroup.WOOD));
		register(fruit + "_log", log);
	}

	/**
	 * 为灌木/藤本类水果注册果丛方块（无原木与树叶）。
	 */
	private static void registerBushBlock(String fruit) {
		Identifier fruitId = XuesFruitMod.id(fruit);

		FruitBushBlock bush = new FruitBushBlock(
				AbstractBlock.Settings.create()
						.mapColor(MapColor.DARK_GREEN)
						.noCollision()
						.ticksRandomly()
						.breakInstantly()
						.sounds(BlockSoundGroup.SWEET_BERRY_BUSH),
				fruitId);
		register(fruit + "_sapling", bush);
	}

	/**
	 * 注册一个方块及其对应的方块物品。
	 */
	public static <T extends Block> T register(String path, T block) {
		Registry.register(Registries.BLOCK, XuesFruitMod.id(path), block);
		BLOCKS.put(path, block);

		BlockItem blockItem = new BlockItem(block, new Item.Settings());
		Registry.register(Registries.ITEM, XuesFruitMod.id(path), blockItem);
		BLOCK_ITEMS.put(path, blockItem);
		return block;
	}

	/**
	 * 注册机器方块（带方块实体）。
	 */
	private static <T extends Block> T registerMachine(String path, T block) {
		Registry.register(Registries.BLOCK, XuesFruitMod.id(path), block);
		BLOCKS.put(path, block);

		BlockItem blockItem = new BlockItem(block, new Item.Settings());
		Registry.register(Registries.ITEM, XuesFruitMod.id(path), blockItem);
		BLOCK_ITEMS.put(path, blockItem);
		return block;
	}
}