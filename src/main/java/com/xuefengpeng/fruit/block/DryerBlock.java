package com.xuefengpeng.fruit.block;

import com.xuefengpeng.fruit.blockentity.DryerBlockEntity;
import com.xuefengpeng.fruit.blockentity.ModBlockEntities;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * ============================================================
 * 烘干机方块（Minecraft 1.20.1 API）
 * ============================================================
 * 带方块实体的加工机器。右键打开 GUI。
 */
public class DryerBlock extends BlockWithEntity implements BlockEntityProvider {

	public DryerBlock(Settings settings) {
		super(settings);
	}

	@Override
	public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return new DryerBlockEntity(pos, state);
	}

	@Override
	public BlockRenderType getRenderType(BlockState state) {
		return BlockRenderType.MODEL;
	}

	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
		return checkType(type, ModBlockEntities.DRYER_BLOCK_ENTITY, DryerBlockEntity::tick);
	}

	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		if (!world.isClient) {
			BlockEntity blockEntity = world.getBlockEntity(pos);
			if (blockEntity instanceof DryerBlockEntity dryer) {
				player.openHandledScreen(dryer);
			}
		}
		return ActionResult.SUCCESS;
	}
}