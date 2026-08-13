package com.xuefengpeng.fruit.blockentity;

import com.xuefengpeng.fruit.XuesFruitMod;
import com.xuefengpeng.fruit.block.ModBlocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

/**
 * ============================================================
 * 方块实体注册
 * ============================================================
 * 为三台加工机器注册方块实体类型：
 *   - 榨汁机（JuicerBlockEntity）
 *   - 烘干机（DryerBlockEntity）
 *   - 发酵桶（FermentationBarrelBlockEntity）
 */
public final class ModBlockEntities {

	private ModBlockEntities() {
	}

	/** 榨汁机方块实体类型 */
	public static final BlockEntityType<JuicerBlockEntity> JUICER_BLOCK_ENTITY =
			register("juicer", BlockEntityType.Builder.create(
					JuicerBlockEntity::new, ModBlocks.JUICER).build(null));

	/** 烘干机方块实体类型 */
	public static final BlockEntityType<DryerBlockEntity> DRYER_BLOCK_ENTITY =
			register("dryer", BlockEntityType.Builder.create(
					DryerBlockEntity::new, ModBlocks.DRYER).build(null));

	/** 发酵桶方块实体类型 */
	public static final BlockEntityType<FermentationBarrelBlockEntity> FERMENTATION_BARREL_BLOCK_ENTITY =
			register("fermentation_barrel", BlockEntityType.Builder.create(
					FermentationBarrelBlockEntity::new, ModBlocks.FERMENTATION_BARREL).build(null));

	/**
	 * 主注册入口（静态字段已完成注册，保留方法以保持统一调用结构）。
	 */
	public static void register() {
	}

	/**
	 * 注册单个方块实体类型。
	 */
	private static <T extends BlockEntity> BlockEntityType<T> register(String name, BlockEntityType<T> type) {
		return Registry.register(Registries.BLOCK_ENTITY_TYPE, XuesFruitMod.id(name), type);
	}
}