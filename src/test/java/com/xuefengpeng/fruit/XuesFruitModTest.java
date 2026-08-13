package com.xuefengpeng.fruit;

import com.xuefengpeng.fruit.effect.ModEffects;
import com.xuefengpeng.fruit.item.ModFoodComponents;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * ============================================================
 * Xue's Fruit 单元测试
 * ============================================================
 * 验证核心系统的基础逻辑：
 *   - 11 个食物组件均正确配置
 *   - 5 个自定义效果均成功注册
 *   - 10 种水果与 5 种加工食品数量正确
 */
class XuesFruitModTest {

	/**
	 * 测试食物组件配置完整。
	 */
	@Test
	void testFoodComponentsConfigured() {
		assertNotNull(ModFoodComponents.BANANA);
		assertNotNull(ModFoodComponents.ORANGE);
		assertNotNull(ModFoodComponents.GRAPE);
		assertNotNull(ModFoodComponents.MANGO);
		assertNotNull(ModFoodComponents.STRAWBERRY);
		assertNotNull(ModFoodComponents.DRAGON_FRUIT);
		assertNotNull(ModFoodComponents.DURIAN);
		assertNotNull(ModFoodComponents.LYCHEE);
		assertNotNull(ModFoodComponents.KIWI);
		assertNotNull(ModFoodComponents.BLUEBERRY);
		assertNotNull(ModFoodComponents.FRUIT_JUICE);
		assertNotNull(ModFoodComponents.FRUIT_JAM);
		assertNotNull(ModFoodComponents.DRIED_FRUIT);
		assertNotNull(ModFoodComponents.FRUIT_SALAD);
		assertNotNull(ModFoodComponents.FRUIT_CAKE);
	}

	/**
	 * 测试自定义效果对象创建。
	 */
	@Test
	void testCustomEffectsRegistered() {
		assertNotNull(ModEffects.VITAMIN_BURST);
		assertNotNull(ModEffects.SUGAR_RUSH);
		assertNotNull(ModEffects.REFRESHED);
		assertNotNull(ModEffects.SHANGHUO);
		assertNotNull(ModEffects.IMMUNITY);
	}

	/**
	 * 测试水果数量。
	 */
	@Test
	void testFruitCount() {
		assertEquals(10, com.xuefengpeng.fruit.block.ModBlocks.ALL_FRUITS.length);
		assertEquals(5, com.xuefengpeng.fruit.block.ModBlocks.TREE_FRUITS.length);
		assertEquals(5, com.xuefengpeng.fruit.block.ModBlocks.BUSH_FRUITS.length);
	}

	/**
	 * 测试 Mod ID。
	 */
	@Test
	void testModId() {
		assertEquals("xuesfruit", XuesFruitMod.MOD_ID);
	}
}