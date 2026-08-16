package com.xuefengpeng.fruit.entity;

import com.xuefengpeng.fruit.XuesFruitMod;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

/**
 * ============================================================
 * 实体注册
 * ============================================================
 * 注册"自然掉落水果"实体类型。
 * 与普通 ItemEntity 不同，该实体在下落时会穿透果树叶，
 * 到达地面后恢复正常掉落物行为。
 */
public final class ModEntities {

	private ModEntities() {
	}

	/** 自然掉落水果实体类型 */
	public static final EntityType<FallingFruitItemEntity> FALLING_FRUIT_ITEM =
			Registry.register(
					Registries.ENTITY_TYPE,
					XuesFruitMod.id("falling_fruit_item"),
					EntityType.Builder.<FallingFruitItemEntity>create(
									FallingFruitItemEntity::new, SpawnGroup.MISC)
							.setDimensions(0.25f, 0.25f)
							.maxTrackingRange(6)
							.trackingTickInterval(20)
							.build("falling_fruit_item"));

	/**
	 * 主注册入口。实体类型已在静态字段中完成注册，
	 * 保留此方法以保持与其它注册类一致的调用结构。
	 */
	public static void register() {
	}
}