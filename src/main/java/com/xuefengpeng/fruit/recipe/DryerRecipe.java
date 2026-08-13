package com.xuefengpeng.fruit.recipe;

import com.google.gson.JsonObject;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.ShapedRecipe;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

/**
 * ============================================================
 * 烘干配方（Minecraft 1.20.1 API）
 * ============================================================
 * 定义烘干机的输入(水果)与输出(果干)。
 */
public class DryerRecipe implements Recipe<SimpleInventory> {

	private final Identifier id;
	private final Ingredient ingredient;
	private final ItemStack result;
	private final int cookingTime;

	public DryerRecipe(Identifier id, Ingredient ingredient, ItemStack result, int cookingTime) {
		this.id = id;
		this.ingredient = ingredient;
		this.result = result;
		this.cookingTime = cookingTime;
	}

	public Ingredient getIngredient() {
		return ingredient;
	}

	public ItemStack getResultStack() {
		return result;
	}

	public int getCookingTime() {
		return cookingTime;
	}

	@Override
	public boolean matches(SimpleInventory inventory, World world) {
		return ingredient.test(inventory.getStack(0));
	}

	@Override
	public ItemStack craft(SimpleInventory inventory, DynamicRegistryManager registryManager) {
		return result.copy();
	}

	@Override
	public boolean fits(int width, int height) {
		return true;
	}

	@Override
	public ItemStack getOutput(DynamicRegistryManager registryManager) {
		return result;
	}

	@Override
	public Identifier getId() {
		return id;
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return ModRecipes.DRYER_RECIPE_SERIALIZER;
	}

	@Override
	public RecipeType<?> getType() {
		return ModRecipes.DRYER_RECIPE_TYPE;
	}

	/**
	 * 烘干配方的序列化器。
	 */
	public static class Serializer implements RecipeSerializer<DryerRecipe> {

		@Override
		public DryerRecipe read(Identifier id, JsonObject json) {
			Ingredient ingredient = Ingredient.fromJson(json.get("ingredient"));
			ItemStack result = ShapedRecipe.outputFromJson(json.getAsJsonObject("result"));
			int cookingTime = json.has("cookingTime") ? json.get("cookingTime").getAsInt() : 200;
			return new DryerRecipe(id, ingredient, result, cookingTime);
		}

		@Override
		public DryerRecipe read(Identifier id, PacketByteBuf buf) {
			Ingredient ingredient = Ingredient.fromPacket(buf);
			ItemStack result = buf.readItemStack();
			int cookingTime = buf.readInt();
			return new DryerRecipe(id, ingredient, result, cookingTime);
		}

		@Override
		public void write(PacketByteBuf buf, DryerRecipe recipe) {
			recipe.ingredient.write(buf);
			buf.writeItemStack(recipe.result);
			buf.writeInt(recipe.cookingTime);
		}
	}
}