package com.xuefengpeng.fruit.blockentity;

import com.xuefengpeng.fruit.recipe.JuicerRecipe;
import com.xuefengpeng.fruit.recipe.ModRecipes;
import com.xuefengpeng.fruit.screen.JuicerScreenHandler;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.Recipe;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Optional;

/**
 * ============================================================
 * 榨汁机方块实体（Minecraft 1.20.1 API）
 * ============================================================
 * 加工水果得到果汁。直接实现 Inventory 接口。
 * 槽位：0=输入 1=燃料 2=输出。
 */
public class JuicerBlockEntity extends BlockEntity implements Inventory, NamedScreenHandlerFactory {

	public static final int INVENTORY_SIZE = 3;
	public static final int INPUT_SLOT = 0;
	public static final int FUEL_SLOT = 1;
	public static final int OUTPUT_SLOT = 2;

	private static final int PROCESS_TIME = 100;
	private static final int FUEL_TIME = 200;

	private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(INVENTORY_SIZE, ItemStack.EMPTY);

	private int burnTime = 0;
	private int maxBurnTime = 0;
	private int progress = 0;

	private final PropertyDelegate propertyDelegate = new PropertyDelegate() {
		@Override
		public int get(int index) {
			return switch (index) {
				case 0 -> burnTime;
				case 1 -> maxBurnTime;
				case 2 -> progress;
				default -> 0;
			};
		}

		@Override
		public void set(int index, int value) {
			switch (index) {
				case 0 -> burnTime = value;
				case 1 -> maxBurnTime = value;
				case 2 -> progress = value;
			}
		}

		@Override
		public int size() {
			return 3;
		}
	};

	public JuicerBlockEntity(BlockPos pos, BlockState state) {
		super(ModBlockEntities.JUICER_BLOCK_ENTITY, pos, state);
	}

	public static void tick(World world, BlockPos pos, BlockState state, JuicerBlockEntity entity) {
		if (!world.isClient) {
			entity.tickServer(world);
		}
	}

	private void tickServer(World world) {
		if (burnTime > 0) {
			burnTime--;
		}

		Optional<JuicerRecipe> recipe = findRecipe(world);
		boolean hasFuel = burnTime > 0;

		if (hasFuel && recipe.isPresent()) {
			progress++;
			if (progress >= PROCESS_TIME) {
				progress = 0;
				craftItem(recipe.get());
				markDirty();
			}
		} else if (progress > 0) {
			progress--;
		}

		if (burnTime <= 0 && recipe.isPresent() && !getStack(FUEL_SLOT).isEmpty()) {
			burnTime = FUEL_TIME;
			maxBurnTime = FUEL_TIME;
			getStack(FUEL_SLOT).decrement(1);
			markDirty();
		}

		if (burnTime <= 0 && maxBurnTime > 0) {
			maxBurnTime = 0;
		}
	}

	private Optional<JuicerRecipe> findRecipe(World world) {
		ItemStack input = getStack(INPUT_SLOT);
		if (input.isEmpty()) {
			return Optional.empty();
		}
		SimpleInventory recipeInput = new SimpleInventory(1);
		recipeInput.setStack(0, input);
		return world.getRecipeManager().getFirstMatch(ModRecipes.JUICER_RECIPE_TYPE, recipeInput, world);
	}

	private void craftItem(JuicerRecipe recipe) {
		ItemStack input = getStack(INPUT_SLOT);
		ItemStack result = recipe.getResultStack().copy();

		if (!canInsertResult(result)) {
			return;
		}

		ItemStack output = getStack(OUTPUT_SLOT);
		if (output.isEmpty()) {
			setStack(OUTPUT_SLOT, result);
		} else {
			output.increment(result.getCount());
		}
		input.decrement(1);
	}

	private boolean canInsertResult(ItemStack result) {
		ItemStack output = getStack(OUTPUT_SLOT);
		if (output.isEmpty()) {
			return true;
		}
		return output.isOf(result.getItem())
				&& output.getCount() + result.getCount() <= output.getMaxCount();
	}

	@Override
	public int size() {
		return inventory.size();
	}

	@Override
	public boolean isEmpty() {
		return inventory.stream().allMatch(ItemStack::isEmpty);
	}

	@Override
	public ItemStack getStack(int slot) {
		return inventory.get(slot);
	}

	@Override
	public ItemStack removeStack(int slot, int amount) {
		return Inventories.splitStack(inventory, slot, amount);
	}

	@Override
	public ItemStack removeStack(int slot) {
		return Inventories.removeStack(inventory, slot);
	}

	@Override
	public void setStack(int slot, ItemStack stack) {
		inventory.set(slot, stack);
		markDirty();
	}

	@Override
	public boolean canPlayerUse(PlayerEntity player) {
		return true;
	}

	@Override
	public void clear() {
		inventory.clear();
	}

	@Override
	public void writeNbt(NbtCompound nbt) {
		super.writeNbt(nbt);
		Inventories.writeNbt(nbt, inventory);
		nbt.putInt("BurnTime", burnTime);
		nbt.putInt("MaxBurnTime", maxBurnTime);
		nbt.putInt("Progress", progress);
	}

	@Override
	public void readNbt(NbtCompound nbt) {
		super.readNbt(nbt);
		Inventories.readNbt(nbt, inventory);
		burnTime = nbt.getInt("BurnTime");
		maxBurnTime = nbt.getInt("MaxBurnTime");
		progress = nbt.getInt("Progress");
	}

	public PropertyDelegate getPropertyDelegate() {
		return propertyDelegate;
	}

	@Override
	public Text getDisplayName() {
		return Text.translatable("block.xuesfruit.juicer");
	}

	@Override
	public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
		return new JuicerScreenHandler(syncId, playerInventory, this, propertyDelegate);
	}
}