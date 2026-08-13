package com.xuefengpeng.fruit.item;

import com.xuefengpeng.fruit.XuesFruitMod;
import com.xuefengpeng.fruit.block.ModBlocks;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * ============================================================
 * 物品注册（Minecraft 1.20.1 API）
 * ============================================================
 * 注册本模组核心物品：
 *   - 水果类（10 种）
 *   - 独立果干（10 种）
 *   - 加工类（果汁 / 果酱 / 水果沙拉 / 水果蛋糕）
 *   - 果酒（发酵桶产物）
 */
public final class ModItems {

	private ModItems() {
	}

	/** 所有已注册食物物品（路径 -> 物品），供 DataGen 使用 */
	public static final Map<String, Item> ITEMS = new LinkedHashMap<>();

	// ---------------------------------------------------------
	// 水果类（10 种）
	// ---------------------------------------------------------

	/** 香蕉 */
	public static final Item BANANA = registerFruit("banana", ModFoodComponents.BANANA, "tooltip.xuesfruit.banana", false);
	/** 橙子 */
	public static final Item ORANGE = registerFruit("orange", ModFoodComponents.ORANGE, "tooltip.xuesfruit.orange", false);
	/** 葡萄 */
	public static final Item GRAPE = registerFruit("grape", ModFoodComponents.GRAPE, "tooltip.xuesfruit.grape", false);
	/** 芒果 */
	public static final Item MANGO = registerFruit("mango", ModFoodComponents.MANGO, "tooltip.xuesfruit.mango", false);
	/** 草莓 */
	public static final Item STRAWBERRY = registerFruit("strawberry", ModFoodComponents.STRAWBERRY, "tooltip.xuesfruit.strawberry", false);
	/** 火龙果 */
	public static final Item DRAGON_FRUIT = registerFruit("dragon_fruit", ModFoodComponents.DRAGON_FRUIT, "tooltip.xuesfruit.dragon_fruit", false);
	/** 榴莲（特殊水果，吃多会上火） */
	public static final Item DURIAN = registerFruit("durian", ModFoodComponents.DURIAN, "tooltip.xuesfruit.durian", true);
	/** 荔枝 */
	public static final Item LYCHEE = registerFruit("lychee", ModFoodComponents.LYCHEE, "tooltip.xuesfruit.lychee", false);
	/** 猕猴桃 */
	public static final Item KIWI = registerFruit("kiwi", ModFoodComponents.KIWI, "tooltip.xuesfruit.kiwi", false);
	/** 蓝莓 */
	public static final Item BLUEBERRY = registerFruit("blueberry", ModFoodComponents.BLUEBERRY, "tooltip.xuesfruit.blueberry", false);

	// ---------------------------------------------------------
	// 独立果干（10 种）
	// ---------------------------------------------------------

	public static final Item DRIED_BANANA = register("dried_banana",
			new Item(new Item.Settings().food(ModFoodComponents.DRIED_FRUIT)));
	public static final Item DRIED_ORANGE = register("dried_orange",
			new Item(new Item.Settings().food(ModFoodComponents.DRIED_FRUIT)));
	public static final Item DRIED_GRAPE = register("dried_grape",
			new Item(new Item.Settings().food(ModFoodComponents.DRIED_FRUIT)));
	public static final Item DRIED_MANGO = register("dried_mango",
			new Item(new Item.Settings().food(ModFoodComponents.DRIED_FRUIT)));
	public static final Item DRIED_STRAWBERRY = register("dried_strawberry",
			new Item(new Item.Settings().food(ModFoodComponents.DRIED_FRUIT)));
	public static final Item DRIED_DRAGON_FRUIT = register("dried_dragon_fruit",
			new Item(new Item.Settings().food(ModFoodComponents.DRIED_FRUIT)));
	public static final Item DRIED_DURIAN = register("dried_durian",
			new Item(new Item.Settings().food(ModFoodComponents.DRIED_FRUIT)));
	public static final Item DRIED_LYCHEE = register("dried_lychee",
			new Item(new Item.Settings().food(ModFoodComponents.DRIED_FRUIT)));
	public static final Item DRIED_KIWI = register("dried_kiwi",
			new Item(new Item.Settings().food(ModFoodComponents.DRIED_FRUIT)));
	public static final Item DRIED_BLUEBERRY = register("dried_blueberry",
			new Item(new Item.Settings().food(ModFoodComponents.DRIED_FRUIT)));

	// ---------------------------------------------------------
	// 加工类
	// ---------------------------------------------------------

	/** 果汁 */
	public static final Item FRUIT_JUICE = register("fruit_juice",
			new FruitJuiceItem(new Item.Settings().food(ModFoodComponents.FRUIT_JUICE), "tooltip.xuesfruit.fruit_juice"));
	/** 果酱 */
	public static final Item FRUIT_JAM = register("fruit_jam",
			new Item(new Item.Settings().food(ModFoodComponents.FRUIT_JAM)));
	/** 水果沙拉（组合食物） */
	public static final Item FRUIT_SALAD = register("fruit_salad",
			new FruitCombinationItem(new Item.Settings().food(ModFoodComponents.FRUIT_SALAD), "tooltip.xuesfruit.fruit_salad"));
	/** 水果蛋糕（组合食物） */
	public static final Item FRUIT_CAKE = register("fruit_cake",
			new FruitCombinationItem(new Item.Settings().food(ModFoodComponents.FRUIT_CAKE), "tooltip.xuesfruit.fruit_cake"));

	// ---------------------------------------------------------
	// 果酒（发酵桶产物）
	// ---------------------------------------------------------

	/** 果酒 */
	public static final Item FRUIT_WINE = register("fruit_wine",
			new FruitJuiceItem(new Item.Settings().food(ModFoodComponents.FRUIT_JUICE), "tooltip.xuesfruit.fruit_wine"));

	/**
	 * 根据水果名获取对应的果干物品。
	 */
	public static Item driedItemFor(String fruit) {
		return ITEMS.get("dried_" + fruit);
	}

	/** 模组物品组 */
	public static final ItemGroup XUES_FRUIT_GROUP = FabricItemGroup.builder()
			.icon(() -> new ItemStack(ModItems.ORANGE))
			.displayName(Text.translatable("itemGroup.xuesfruit"))
			.entries((displayContext, entries) -> {
				// 水果
				for (String fruit : ModBlocks.ALL_FRUITS) {
					Item item = ITEMS.get(fruit);
					if (item != null) {
						entries.add(item);
					}
				}
				// 加工食品
				entries.add(FRUIT_JUICE);
				entries.add(FRUIT_JAM);
				entries.add(FRUIT_SALAD);
				entries.add(FRUIT_CAKE);
				entries.add(FRUIT_WINE);
				// 独立果干
				for (String fruit : ModBlocks.ALL_FRUITS) {
					Item dried = driedItemFor(fruit);
					if (dried != null) {
						entries.add(dried);
					}
				}
				// 机器方块
				entries.add(ModBlocks.BLOCK_ITEMS.get("juicer"));
				entries.add(ModBlocks.BLOCK_ITEMS.get("dryer"));
				entries.add(ModBlocks.BLOCK_ITEMS.get("fermentation_barrel"));
				// 乔木类：树苗 + 树叶 + 原木
				for (String fruit : ModBlocks.TREE_FRUITS) {
					entries.add(ModBlocks.BLOCK_ITEMS.get(fruit + "_sapling"));
					entries.add(ModBlocks.BLOCK_ITEMS.get(fruit + "_leaves"));
					entries.add(ModBlocks.BLOCK_ITEMS.get(fruit + "_log"));
				}
				// 灌木类：果丛
				for (String fruit : ModBlocks.BUSH_FRUITS) {
					entries.add(ModBlocks.BLOCK_ITEMS.get(fruit + "_sapling"));
				}
			})
			.build();

	/**
	 * 主注册入口：食物物品已在静态字段初始化时注册。
	 */
	public static void register() {
	}

	/**
	 * 注册物品组到登记表。
	 */
	public static void registerItemGroups() {
		Registry.register(Registries.ITEM_GROUP, XuesFruitMod.id("fruit_group"), XUES_FRUIT_GROUP);
	}

	/**
	 * 注册普通水果物品。
	 */
	private static Item registerFruit(String name, FoodComponent food, String tooltipKey, boolean special) {
		Item item;
		if (special) {
			item = new SpecialFruitItem(new Item.Settings().food(food), tooltipKey);
		} else {
			item = new FruitItem(new Item.Settings().food(food), tooltipKey);
		}
		return register(name, item);
	}

	/**
	 * 注册单个物品。
	 */
	public static <T extends Item> T register(String name, T item) {
		Registry.register(Registries.ITEM, XuesFruitMod.id(name), item);
		ITEMS.put(name, item);
		return item;
	}
}