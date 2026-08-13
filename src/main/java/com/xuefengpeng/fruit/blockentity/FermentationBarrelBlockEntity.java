package com.xuefengpeng.fruit.blockentity;

import com.xuefengpeng.fruit.XuesFruitMod;
import com.xuefengpeng.fruit.block.ModBlocks;
import com.xuefengpeng.fruit.item.ModItems;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * ============================================================
 * 发酵桶方块实体（Minecraft 1.20.1 API）
 * ============================================================
 * 将水果发酵为果酒的加工机器（无 GUI，右键交互）。
 */
public class FermentationBarrelBlockEntity extends BlockEntity {

	private static final int FERMENT_TIME = 2400; // 2 分钟

	private int progress = -1;

	public FermentationBarrelBlockEntity(BlockPos pos, BlockState state) {
		super(ModBlockEntities.FERMENTATION_BARREL_BLOCK_ENTITY, pos, state);
	}

	public static void tick(World world, BlockPos pos, BlockState state, FermentationBarrelBlockEntity entity) {
		if (world.isClient) {
			return;
		}
		entity.tickServer((ServerWorld) world);
	}

	private void tickServer(ServerWorld world) {
		if (progress >= 0) {
			progress++;
			if (progress >= FERMENT_TIME) {
				progress = -1;
				dropItem(world, new ItemStack(ModItems.FRUIT_WINE, 1));
			}
			markDirty();
		}
	}

	public void onUse(PlayerEntity player) {
		World world = this.world;
		if (world == null || world.isClient) {
			return;
		}

		if (progress < 0) {
			ItemStack held = player.getMainHandStack();
			if (isFruit(held)) {
				held.decrement(1);
				progress = 0;
				world.playSound(null, pos, SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.BLOCKS, 0.8f, 1.0f);
				markDirty();
				return;
			}
		}

		player.sendMessage(Text.translatable("message.xuesfruit.fermentation.status",
				progress < 0 ? "空闲" : "发酵中"), false);
	}

	private boolean isFruit(ItemStack stack) {
		if (stack.isEmpty()) {
			return false;
		}
		for (String fruit : ModBlocks.FRUITS) {
			if (stack.isOf(Registries.ITEM.get(XuesFruitMod.id(fruit)))) {
				return true;
			}
		}
		return false;
	}

	private void dropItem(ServerWorld world, ItemStack stack) {
		ItemEntity entity = new ItemEntity(
				world,
				pos.getX() + 0.5,
				pos.getY() + 1.0,
				pos.getZ() + 0.5,
				stack);
		world.spawnEntity(entity);
	}

	@Override
	public void writeNbt(NbtCompound nbt) {
		super.writeNbt(nbt);
		nbt.putInt("Progress", progress);
	}

	@Override
	public void readNbt(NbtCompound nbt) {
		super.readNbt(nbt);
		progress = nbt.getInt("Progress");
	}
}