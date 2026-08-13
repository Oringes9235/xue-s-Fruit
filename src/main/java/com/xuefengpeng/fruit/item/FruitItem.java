package com.xuefengpeng.fruit.item;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;

import java.util.List;

/**
 * ============================================================
 * 普通水果类（Minecraft 1.20.1 API）
 * ============================================================
 * 所有普通水果的基础物品类。
 * 仅额外提供自定义物品提示文本（tooltip）。
 */
public class FruitItem extends Item {

	/** 该水果在提示文本中显示的能力描述键 */
	private final String tooltipKey;

	/**
	 * 构造普通水果。
	 *
	 * @param settings   物品基础设置
	 * @param tooltipKey 提示文本的翻译键
	 */
	public FruitItem(Settings settings, String tooltipKey) {
		super(settings);
		this.tooltipKey = tooltipKey;
	}

	/**
	 * 追加物品提示信息，展示水果的特殊效果说明。
	 */
	@Override
	public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
		tooltip.add(Text.translatable(tooltipKey).formatted(Formatting.GOLD));
	}
}