package com.xuefengpeng.fruit.screen;

import com.xuefengpeng.fruit.blockentity.JuicerBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;

/**
 * ============================================================
 * 榨汁机 GUI 逻辑（屏幕处理器，Minecraft 1.20.1 API）
 * ============================================================
 */
public class JuicerScreenHandler extends ScreenHandler {

	private final Inventory inventory;
	private final PropertyDelegate propertyDelegate;

	/**
	 * 客户端构造器。
	 */
	public JuicerScreenHandler(int syncId, PlayerInventory playerInventory) {
		this(syncId, playerInventory, new SimpleInventory(3), new ArrayPropertyDelegate(3));
	}

	/**
	 * 服务端构造器。
	 */
	public JuicerScreenHandler(int syncId, PlayerInventory playerInventory,
	                           Inventory inventory, PropertyDelegate delegate) {
		super(ModScreenHandlers.JUICER_SCREEN_HANDLER, syncId);
		this.inventory = inventory;
		this.propertyDelegate = delegate;

		addProperties(delegate);

		if (inventory != null) {
			this.addSlot(new Slot(inventory, JuicerBlockEntity.INPUT_SLOT, 44, 35));
			this.addSlot(new Slot(inventory, JuicerBlockEntity.FUEL_SLOT, 68, 53));
			this.addSlot(new Slot(inventory, JuicerBlockEntity.OUTPUT_SLOT, 116, 35));
		}

		for (int row = 0; row < 3; row++) {
			for (int col = 0; col < 9; col++) {
				this.addSlot(new Slot(playerInventory, col + row * 9 + 9, 8 + col * 18, 84 + row * 18));
			}
		}
		for (int col = 0; col < 9; col++) {
			this.addSlot(new Slot(playerInventory, col, 8 + col * 18, 142));
		}
	}

	/** 剩余燃料 tick */
	public int getBurnTime() {
		return propertyDelegate.get(0);
	}

	/** 最大燃料 tick */
	public int getMaxBurnTime() {
		return propertyDelegate.get(1);
	}

	/** 加工进度 */
	public int getProgress() {
		return propertyDelegate.get(2);
	}

	@Override
	public ItemStack quickMove(PlayerEntity player, int index) {
		ItemStack result = ItemStack.EMPTY;
		Slot slot = this.slots.get(index);
		if (slot != null && slot.hasStack()) {
			ItemStack stackInSlot = slot.getStack();
			result = stackInSlot.copy();
			if (index < 3) {
				if (!this.insertItem(stackInSlot, 3, 39, true)) {
					return ItemStack.EMPTY;
				}
			} else {
				if (!this.insertItem(stackInSlot, 0, 3, false)) {
					return ItemStack.EMPTY;
				}
			}
			if (stackInSlot.isEmpty()) {
				slot.setStack(ItemStack.EMPTY);
			} else {
				slot.markDirty();
			}
		}
		return result;
	}

	@Override
	public boolean canUse(PlayerEntity player) {
		return this.inventory.canPlayerUse(player);
	}
}