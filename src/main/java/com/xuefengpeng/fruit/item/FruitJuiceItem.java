package com.xuefengpeng.fruit.item;

import com.xuefengpeng.fruit.effect.ModEffects;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;

import java.util.List;

/**
 * ============================================================
 * 果汁类（Minecraft 1.20.1 API）
 * ============================================================
 * 果蔬榨汁得到的饮品。饮用后：
 *   1. 恢复饥饿值（由食物组件处理）
 *   2. 清除已有的"上火"负面效果
 */
public class FruitJuiceItem extends Item {

	/** 果汁提示文本键 */
	private final String tooltipKey;

	/**
	 * 构造果汁物品。
	 */
	public FruitJuiceItem(Settings settings, String tooltipKey) {
		super(settings);
		this.tooltipKey = tooltipKey;
	}

	/**
	 * 饮用完成回调：清除上火。
	 */
	@Override
	public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
		ItemStack result = super.finishUsing(stack, world, user);

		if (!world.isClient && user instanceof PlayerEntity player) {
			// 果汁能中和榴莲的"上火"
			player.removeStatusEffect(ModEffects.SHANGHUO);
		}
		return result;
	}

	/**
	 * 追加提示文本。
	 */
	@Override
	public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
		tooltip.add(Text.translatable(tooltipKey).formatted(Formatting.AQUA));
	}
}