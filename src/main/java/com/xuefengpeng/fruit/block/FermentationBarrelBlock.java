package com.xuefengpeng.fruit.block;

import com.xuefengpeng.fruit.blockentity.FermentationBarrelBlockEntity;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * ============================================================
 * 发酵桶方块（Minecraft 1.20.1 API）
 * ============================================================
 * 用于将水果发酵为果酒的加工机器（右键交互）。
 */
public class FermentationBarrelBlock extends BlockWithEntity implements BlockEntityProvider {

	public FermentationBarrelBlock(Settings settings) {
		super(settings);
	}

	@Override
	public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return new FermentationBarrelBlockEntity(pos, state);
	}

	@Override
	public BlockRenderType getRenderType(BlockState state) {
		return BlockRenderType.MODEL;
	}

	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		if (!world.isClient) {
			BlockEntity blockEntity = world.getBlockEntity(pos);
			if (blockEntity instanceof FermentationBarrelBlockEntity barrel) {
				barrel.onUse(player);
				return ActionResult.SUCCESS;
			}
		}
		return ActionResult.SUCCESS;
	}
}