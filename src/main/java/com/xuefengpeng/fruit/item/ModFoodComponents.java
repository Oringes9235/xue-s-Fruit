package com.xuefengpeng.fruit.item;

import com.xuefengpeng.fruit.effect.FruitEffects;
import net.minecraft.item.FoodComponent;

/**
 * ============================================================
 * 食物属性定义（Minecraft 1.20.1 API）
 * ============================================================
 * 使用原版 FoodComponent.Builder 定义每种水果/加工食品的
 * 饥饿值(hunger)、饱食度(saturation)与附加状态效果。
 *
 * 数值说明：
 *   - hunger: 食用后恢复的饥饿值（1 = 0.5 鸡腿）
 *   - saturationModifier: 饱食度，数值越大越耐饿
 */
public final class ModFoodComponents {

	private ModFoodComponents() {
	}

	// ---------------------------------------------------------
	// 水果类（10 种）
	// ---------------------------------------------------------

	/** 香蕉 */
	public static final FoodComponent BANANA = new FoodComponent.Builder()
			.hunger(4)
			.saturationModifier(0.3f)
			.statusEffect(FruitEffects.bananaEffect(), 1.0f)
			.build();

	/** 橙子 */
	public static final FoodComponent ORANGE = new FoodComponent.Builder()
			.hunger(4)
			.saturationModifier(0.4f)
			.statusEffect(FruitEffects.orangeEffect(), 1.0f)
			.build();

	/** 葡萄 */
	public static final FoodComponent GRAPE = new FoodComponent.Builder()
			.hunger(2)
			.saturationModifier(0.2f)
			.statusEffect(FruitEffects.grapeEffect(), 1.0f)
			.build();

	/** 芒果 */
	public static final FoodComponent MANGO = new FoodComponent.Builder()
			.hunger(5)
			.saturationModifier(0.5f)
			.statusEffect(FruitEffects.mangoEffect(), 1.0f)
			.build();

	/** 草莓 */
	public static final FoodComponent STRAWBERRY = new FoodComponent.Builder()
			.hunger(3)
			.saturationModifier(0.3f)
			.statusEffect(FruitEffects.strawberryEffect(), 1.0f)
			.build();

	/** 火龙果 */
	public static final FoodComponent DRAGON_FRUIT = new FoodComponent.Builder()
			.hunger(5)
			.saturationModifier(0.5f)
			.statusEffect(FruitEffects.dragonFruitEffect(), 1.0f)
			.build();

	/** 榴莲 */
	public static final FoodComponent DURIAN = new FoodComponent.Builder()
			.hunger(7)
			.saturationModifier(0.6f)
			.statusEffect(FruitEffects.durianEffect(), 1.0f)
			.build();

	/** 荔枝 */
	public static final FoodComponent LYCHEE = new FoodComponent.Builder()
			.hunger(3)
			.saturationModifier(0.3f)
			.statusEffect(FruitEffects.lycheeEffect(), 1.0f)
			.build();

	/** 猕猴桃 */
	public static final FoodComponent KIWI = new FoodComponent.Builder()
			.hunger(4)
			.saturationModifier(0.4f)
			.statusEffect(FruitEffects.kiwiEffect(), 1.0f)
			.build();

	/** 蓝莓 */
	public static final FoodComponent BLUEBERRY = new FoodComponent.Builder()
			.hunger(3)
			.saturationModifier(0.3f)
			.statusEffect(FruitEffects.blueberryEffect(), 1.0f)
			.build();

	// ---------------------------------------------------------
	// 加工类（5 种）
	// ---------------------------------------------------------

	/** 果汁 */
	public static final FoodComponent FRUIT_JUICE = new FoodComponent.Builder()
			.hunger(6)
			.saturationModifier(0.7f)
			.build();

	/** 果酱 */
	public static final FoodComponent FRUIT_JAM = new FoodComponent.Builder()
			.hunger(5)
			.saturationModifier(0.9f)
			.build();

	/** 果干 */
	public static final FoodComponent DRIED_FRUIT = new FoodComponent.Builder()
			.hunger(4)
			.saturationModifier(0.8f)
			.build();

	/** 水果沙拉 */
	public static final FoodComponent FRUIT_SALAD = new FoodComponent.Builder()
			.hunger(8)
			.saturationModifier(0.8f)
			.build();

	/** 水果蛋糕 */
	public static final FoodComponent FRUIT_CAKE = new FoodComponent.Builder()
			.hunger(10)
			.saturationModifier(1.2f)
			.build();
}