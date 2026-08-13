package com.xuefengpeng.fruit.item;

import com.xuefengpeng.fruit.effect.FruitEffects;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;

import java.util.List;

/**
 * ============================================================
 * 组合食物类（水果沙拉 / 水果蛋糕，Minecraft 1.20.1 API）
 * ============================================================
 * 食用后附加"水果组合 Buff"：
 *   同时给予生命回复、抗性提升、速度三种增益。
 */
public class FruitCombinationItem extends Item {

	/** 组合食物提示文本键 */
	private final String tooltipKey;

	/**
	 * 构造组合食物。
	 */
	public FruitCombinationItem(Settings settings, String tooltipKey) {
		super(settings);
		this.tooltipKey = tooltipKey;
	}

	/**
	 * 食用完成后附加组合 Buff。
	 */
	@Override
	public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
		ItemStack result = super.finishUsing(stack, world, user);

		if (!world.isClient && user instanceof PlayerEntity player) {
			applyCombinationBuff(player);
		}
		return result;
	}

	/**
	 * 组合 Buff：生命回复 + 抗性提升 + 速度激增。
	 */
	private void applyCombinationBuff(PlayerEntity player) {
		player.addStatusEffect(FruitEffects.bananaEffect());
		player.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 30 * 20, 0));
		player.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 30 * 20, 1));
	}

	/**
	 * 追加组合食物提示。
	 */
	@Override
	public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
		tooltip.add(Text.translatable(tooltipKey).formatted(Formatting.LIGHT_PURPLE));
	}
}