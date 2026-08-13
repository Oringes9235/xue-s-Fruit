package com.xuefengpeng.fruit.item;

import com.xuefengpeng.fruit.effect.FruitEffects;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/**
 * ============================================================
 * 特殊水果类（榴莲等，Minecraft 1.20.1 API）
 * ============================================================
 * 在普通水果基础上叠加额外食用逻辑。
 * 榴莲特性：食用后若已处于高等级力量效果（叠加过量），
 * 额外附加"上火"负面效果。
 */
public class SpecialFruitItem extends FruitItem {

	/**
	 * 构造特殊水果。
	 *
	 * @param settings   物品设置
	 * @param tooltipKey 提示文本键
	 */
	public SpecialFruitItem(Settings settings, String tooltipKey) {
		super(settings, tooltipKey);
	}

	/**
	 * 食用完成后的回调，用于附加特殊负面效果。
	 */
	@Override
	public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
		ItemStack result = super.finishUsing(stack, world, user);

		// 仅在服务端处理效果，且使用者为玩家
		if (!world.isClient && user instanceof PlayerEntity player) {
			applyDurianSideEffect(player);
		}
		return result;
	}

	/**
	 * 榴莲过量判定：当玩家已有力量效果且增幅等级 >= 1 时，
	 * 附加"上火"负面效果。
	 */
	private void applyDurianSideEffect(PlayerEntity player) {
		StatusEffectInstance strength = player.getStatusEffect(StatusEffects.STRENGTH);
		if (strength != null && strength.getAmplifier() >= 1) {
			player.addStatusEffect(FruitEffects.shangHuoEffect());
		}
	}
}