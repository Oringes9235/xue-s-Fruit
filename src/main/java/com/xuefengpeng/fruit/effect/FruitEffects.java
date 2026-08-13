package com.xuefengpeng.fruit.effect;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;

/**
 * ============================================================
 * 水果效果系统（Minecraft 1.20.1 API）
 * ============================================================
 * 集中管理各类水果食用后提供的状态效果实例。
 *
 * 设计要点：
 *   1. 每种水果对应独特效果
 *   2. 效果叠加机制：再次食用时延长时长（由 StatusEffectInstance 叠加逻辑处理）
 *   3. 负面效果：榴莲吃多会上火
 *   4. 组合 Buff：多效果并存时触发水果组合强化
 */
public final class FruitEffects {

	private FruitEffects() {
	}

	/**
	 * 香蕉 - 提供饱腹与轻微生命回复（时长 30s）。
	 */
	public static StatusEffectInstance bananaEffect() {
		return new StatusEffectInstance(StatusEffects.REGENERATION, 30 * 20, 0);
	}

	/**
	 * 橙子 - 维生素爆发：提升攻击力与移速（中等级别）。
	 */
	public static StatusEffectInstance orangeEffect() {
		return new StatusEffectInstance(ModEffects.VITAMIN_BURST, 40 * 20, 1);
	}

	/**
	 * 葡萄 - 血糖激增：加速但会消耗饥饿值。
	 */
	public static StatusEffectInstance grapeEffect() {
		return new StatusEffectInstance(ModEffects.SUGAR_RUSH, 25 * 20, 0);
	}

	/**
	 * 芒果 - 饱腹感。
	 */
	public static StatusEffectInstance mangoEffect() {
		return new StatusEffectInstance(StatusEffects.SATURATION, 10 * 20, 1);
	}

	/**
	 * 草莓 - 幸运。
	 */
	public static StatusEffectInstance strawberryEffect() {
		return new StatusEffectInstance(StatusEffects.LUCK, 30 * 20, 0);
	}

	/**
	 * 火龙果 - 抗火。
	 */
	public static StatusEffectInstance dragonFruitEffect() {
		return new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 20 * 20, 0);
	}

	/**
	 * 榴莲 - 力量提升，但叠加过量会触发"上火"负面效果。
	 */
	public static StatusEffectInstance durianEffect() {
		return new StatusEffectInstance(StatusEffects.STRENGTH, 20 * 20, 1);
	}

	/**
	 * 榴莲过量食用触发的上火（负面）。
	 */
	public static StatusEffectInstance shangHuoEffect() {
		return new StatusEffectInstance(ModEffects.SHANGHUO, 15 * 20, 1);
	}

	/**
	 * 荔枝 - 夜视。
	 */
	public static StatusEffectInstance lycheeEffect() {
		return new StatusEffectInstance(StatusEffects.NIGHT_VISION, 20 * 20, 0);
	}

	/**
	 * 猕猴桃 - 清爽提神：提升挖掘速度。
	 */
	public static StatusEffectInstance kiwiEffect() {
		return new StatusEffectInstance(ModEffects.REFRESHED, 35 * 20, 0);
	}

	/**
	 * 蓝莓 - 免疫强化：提升最大生命上限。
	 */
	public static StatusEffectInstance blueberryEffect() {
		return new StatusEffectInstance(ModEffects.IMMUNITY, 30 * 20, 0);
	}
}