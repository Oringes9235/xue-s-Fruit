package com.xuefengpeng.fruit.block;

import com.xuefengpeng.fruit.blockentity.DryerBlockEntity;
import com.xuefengpeng.fruit.blockentity.ModBlockEntities;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

/**
 * ============================================================
 * 烘干机方块（Minecraft 1.20.1 API）
 * ============================================================
 * 带方块实体的加工机器。右键打开 GUI。
 */
public class DryerBlock extends HorizontalFacingBlock implements BlockEntityProvider {

	public DryerBlock(Settings settings) {
		super(settings);
		// 注册 facing 属性并设置默认朝向，否则 blockstate 无法匹配变体，方块会渲染为黑紫块。
		this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH));
	}

	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		return this.getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing().getOpposite());
	}

	@Override
	protected void appendProperties(StateManager.Builder<net.minecraft.block.Block, BlockState> builder) {
		builder.add(FACING);
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
	@SuppressWarnings("unchecked")
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
		if (type == ModBlockEntities.DRYER_BLOCK_ENTITY) {
			return (BlockEntityTicker<T>) (BlockEntityTicker<DryerBlockEntity>) DryerBlockEntity::tick;
		}
		return null;
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