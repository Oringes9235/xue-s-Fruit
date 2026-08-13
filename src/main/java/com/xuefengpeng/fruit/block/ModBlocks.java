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
 *   - 10 组果树方块（树苗 + 树叶 + 原木），共 30 个
 *   - 3 台加工机器（榨汁机 / 烘干机 / 发酵桶）
 *
 * 使用 Map 保存所有已注册方块的物品（BlockItem）供物品注册与战利品表使用。
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
	 * 10 种水果对应的树苗 / 树叶 / 原木方块。
	 * 水果命名顺序与物品保持一致。
	 */
	public static final String[] FRUITS = {
			"banana", "orange", "grape", "mango", "strawberry",
			"dragon_fruit", "durian", "lychee", "kiwi", "blueberry"
	};

	/**
	 * 主注册入口。
	 */
	public static void register() {
		// 机器方块已在静态字段初始化时注册，此处注册果树方块组
		for (String fruit : FRUITS) {
			registerFruitBlocks(fruit);
		}
	}

	/**
	 * 为单个水果注册树苗、树叶、原木三个方块。
	 *
	 * @param fruit 水果名称（用于构造注册路径）
	 */
	private static void registerFruitBlocks(String fruit) {
		Identifier fruitId = XuesFruitMod.id(fruit);

		// 树苗方块（生长 + 结果）
		FruitSaplingBlock sapling = new FruitSaplingBlock(
				AbstractBlock.Settings.create()
						.mapColor(MapColor.DARK_GREEN)
						.noCollision()
						.ticksRandomly()
						.breakInstantly()
						.sounds(BlockSoundGroup.GRASS),
				fruitId);
		register(fruit + "_sapling", sapling);

		// 树叶方块（结果掉落）
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
	 * 注册一个方块及其对应的方块物品。
	 *
	 * @param path  方块注册路径
	 * @param block 方块实例
	 * @param <T>   方块类型
	 * @return 已注册的方块实例
	 */
	public static <T extends Block> T register(String path, T block) {
		Registry.register(Registries.BLOCK, XuesFruitMod.id(path), block);
		BLOCKS.put(path, block);

		// 同时注册方块物品
		BlockItem blockItem = new BlockItem(block, new Item.Settings());
		Registry.register(Registries.ITEM, XuesFruitMod.id(path), blockItem);
		BLOCK_ITEMS.put(path, blockItem);
		return block;
	}

	/**
	 * 注册机器方块（带方块实体，方块物品在物品注册阶段单独创建）。
	 *
	 * @param path  方块注册路径
	 * @param block 方块实例
	 * @param <T>   方块类型
	 * @return 已注册的方块实例
	 */
	private static <T extends Block> T registerMachine(String path, T block) {
		Registry.register(Registries.BLOCK, XuesFruitMod.id(path), block);
		BLOCKS.put(path, block);

		// 机器方块物品
		BlockItem blockItem = new BlockItem(block, new Item.Settings());
		Registry.register(Registries.ITEM, XuesFruitMod.id(path), blockItem);
		BLOCK_ITEMS.put(path, blockItem);
		return block;
	}
}