package com.xuefengpeng.fruit.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/**
 * ============================================================
 * 自然掉落的水果
 * ============================================================
 * 与普通 ItemEntity 唯一的区别：其实体类型不同。
 * 果树叶（FruitLeavesBlock）会检测当前碰撞实体是否为
 * 本类，若是则返回空碰撞箱，从而让自然掉落的水果穿透树叶；
 * 而玩家丢弃的普通 ItemEntity 仍会正常与树叶碰撞。
 *
 * 本类不覆盖任何物理逻辑，仅作为区分自然掉落与玩家丢弃的标记。
 * 落地后的物理、拾取、堆叠等行为与普通掉落物完全一致。
 */
public class FallingFruitItemEntity extends ItemEntity {

	/**
	 * 实体类型工厂所需构造器（由 EntityType 在生成实体时调用）。
	 */
	public FallingFruitItemEntity(EntityType<FallingFruitItemEntity> type, World world) {
		super(type, world);
	}

	/**
	 * 在指定位置创建一个自然掉落的水果实体。
	 */
	public static FallingFruitItemEntity drop(World world, double x, double y, double z, ItemStack stack) {
		FallingFruitItemEntity entity = new FallingFruitItemEntity(ModEntities.FALLING_FRUIT_ITEM, world);
		entity.setPosition(x, y, z);
		entity.setStack(stack);
		return entity;
	}
}