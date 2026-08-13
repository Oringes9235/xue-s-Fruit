package com.xuefengpeng.fruit.effect;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

/**
 * ============================================================
 * 水果自定义状态效果基类
 * ============================================================
 * 继承原版 StatusEffect，提供链式 addAttributeModifier 方法，
 * 方便在注册时以流式风格配置属性修饰符。
 */
public class FruitStatusEffect extends StatusEffect {

	/**
	 * 构造自定义状态效果。
	 *
	 * @param category 效果类别（增益/减益/中性）
	 * @param color    效果粒子与药水图标颜色（0xRRGGBB）
	 */
	public FruitStatusEffect(StatusEffectCategory category, int color) {
		super(category, color);
	}
}