package com.xuefengpeng.fruit.effect;

import com.xuefengpeng.fruit.XuesFruitMod;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

/**
 * ============================================================
 * 自定义状态效果注册（Minecraft 1.20.1 API）
 * ============================================================
 * 定义本模组独有的状态效果：
 *   - 维生素爆发：提升攻击力与移动速度
 *   - 血糖激增：提升速度
 *   - 清爽提神：提升挖掘速度
 *   - 上火：榴莲吃多触发的负面效果
 *   - 免疫强化：提升最大生命上限
 */
public final class ModEffects {

	private ModEffects() {
	}

	/** 维生素爆发 */
	public static final StatusEffect VITAMIN_BURST = new FruitStatusEffect(
			StatusEffectCategory.BENEFICIAL, 0xFFA500)
			.addAttributeModifier(
					EntityAttributes.GENERIC_ATTACK_DAMAGE,
					uuid("effect.vitamin_attack"),
					0.15f,
					EntityAttributeModifier.Operation.MULTIPLY_TOTAL)
			.addAttributeModifier(
					EntityAttributes.GENERIC_MOVEMENT_SPEED,
					uuid("effect.vitamin_speed"),
					0.10f,
					EntityAttributeModifier.Operation.MULTIPLY_TOTAL);

	/** 血糖激增 */
	public static final StatusEffect SUGAR_RUSH = new FruitStatusEffect(
			StatusEffectCategory.BENEFICIAL, 0x8B00FF)
			.addAttributeModifier(
					EntityAttributes.GENERIC_MOVEMENT_SPEED,
					uuid("effect.sugar_speed"),
					0.08f,
					EntityAttributeModifier.Operation.MULTIPLY_TOTAL);

	/** 清爽提神 */
	public static final StatusEffect REFRESHED = new FruitStatusEffect(
			StatusEffectCategory.BENEFICIAL, 0x00FF7F)
			.addAttributeModifier(
					EntityAttributes.GENERIC_MOVEMENT_SPEED,
					uuid("effect.refresh_mining"),
					0.20f,
					EntityAttributeModifier.Operation.MULTIPLY_BASE);

	/** 上火（负面效果） */
	public static final StatusEffect SHANGHUO = new FruitStatusEffect(
			StatusEffectCategory.HARMFUL, 0xDC143C);

	/** 免疫强化 */
	public static final StatusEffect IMMUNITY = new FruitStatusEffect(
			StatusEffectCategory.BENEFICIAL, 0x4169E1)
			.addAttributeModifier(
					EntityAttributes.GENERIC_MAX_HEALTH,
					uuid("effect.immunity_health"),
					0.20f,
					EntityAttributeModifier.Operation.MULTIPLY_TOTAL);

	/**
	 * 向游戏注册所有状态效果。
	 */
	public static void register() {
		register("vitamin_burst", VITAMIN_BURST);
		register("sugar_rush", SUGAR_RUSH);
		register("refreshed", REFRESHED);
		register("shanghuo", SHANGHUO);
		register("immunity", IMMUNITY);
	}

	/**
	 * 注册单个状态效果到登记表。
	 */
	private static void register(String name, StatusEffect effect) {
		Registry.register(Registries.STATUS_EFFECT, XuesFruitMod.id(name), effect);
	}

	/**
	 * 根据键名生成稳定的 UUID 字符串，
	 * 作为属性修饰符的唯一标识符。
	 */
	private static String uuid(String key) {
		return UUID.nameUUIDFromBytes(
				(XuesFruitMod.MOD_ID + ":" + key).getBytes(StandardCharsets.UTF_8)).toString();
	}
}