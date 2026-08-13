package com.xuefengpeng.fruit.trade;

import com.xuefengpeng.fruit.XuesFruitMod;
import com.xuefengpeng.fruit.item.ModItems;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.VillagerProfession;

/**
 * ============================================================
 * 村民交易系统（Minecraft 1.20.1 API）
 * ============================================================
 * 为农民职业注册水果相关的交易。
 */
public final class ModVillagerTrades {

	private ModVillagerTrades() {
	}

	/**
	 * 主注册入口：向农民职业添加水果交易。
	 */
	public static void register() {
		registerFruitTrades();
		registerProcessedTrades();
		registerSaplingTrades();
	}

	/**
	 * 农民购买水果（用绿宝石换取玩家手中的水果）。
	 */
	private static void registerFruitTrades() {
		for (String fruit : com.xuefengpeng.fruit.block.ModBlocks.ALL_FRUITS) {
			net.minecraft.item.Item fruitItem = Registries.ITEM.get(XuesFruitMod.id(fruit));
			TradeOfferHelper.registerVillagerOffers(
					VillagerProfession.FARMER, 1,
					factories -> factories.add((entity, random) ->
							new TradeOffer(
									new ItemStack(fruitItem, 8),
									new ItemStack(Items.EMERALD, 1),
									16, 2, 0.05f)));
		}
	}

	/**
	 * 加工食品交易。
	 */
	private static void registerProcessedTrades() {
		TradeOfferHelper.registerVillagerOffers(
				VillagerProfession.FARMER, 3,
				factories -> {
					factories.add((entity, random) ->
							new TradeOffer(
									new ItemStack(Items.EMERALD, 3),
									new ItemStack(ModItems.FRUIT_JAM, 1),
									8, 5, 0.05f));
					factories.add((entity, random) ->
							new TradeOffer(
									new ItemStack(Items.EMERALD, 4),
									new ItemStack(ModItems.DRIED_MANGO, 2),
									8, 5, 0.05f));
					factories.add((entity, random) ->
							new TradeOffer(
									new ItemStack(Items.EMERALD, 6),
									new ItemStack(ModItems.FRUIT_JUICE, 1),
									4, 8, 0.05f));
				});
	}

	/**
	 * 树苗交易。
	 */
	private static void registerSaplingTrades() {
		for (String fruit : com.xuefengpeng.fruit.block.ModBlocks.ALL_FRUITS) {
			var sapling = com.xuefengpeng.fruit.block.ModBlocks.BLOCK_ITEMS.get(fruit + "_sapling");
			TradeOfferHelper.registerVillagerOffers(
					VillagerProfession.FARMER, 2,
					factories -> factories.add((entity, random) ->
							new TradeOffer(
									new ItemStack(Items.EMERALD, 2),
									new ItemStack(sapling, 1),
									6, 4, 0.05f)));
		}
	}
}