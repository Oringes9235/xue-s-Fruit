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
 * 榨汁配方（Minecraft 1.20.1 API）
 * ============================================================
 * 定义榨汁机的输入(水果)与输出(果汁)。
 */
public class JuicerRecipe implements Recipe<SimpleInventory> {

	private final Identifier id;
	private final Ingredient ingredient;
	private final ItemStack result;

	public JuicerRecipe(Identifier id, Ingredient ingredient, ItemStack result) {
		this.id = id;
		this.ingredient = ingredient;
		this.result = result;
	}

	public Ingredient getIngredient() {
		return ingredient;
	}

	public ItemStack getResultStack() {
		return result;
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
		return ModRecipes.JUICER_RECIPE_SERIALIZER;
	}

	@Override
	public RecipeType<?> getType() {
		return ModRecipes.JUICER_RECIPE_TYPE;
	}

	/**
	 * 榨汁配方的序列化器。
	 */
	public static class Serializer implements RecipeSerializer<JuicerRecipe> {

		@Override
		public JuicerRecipe read(Identifier id, JsonObject json) {
			Ingredient ingredient = Ingredient.fromJson(json.get("ingredient"));
			ItemStack result = ShapedRecipe.outputFromJson(json.getAsJsonObject("result"));
			return new JuicerRecipe(id, ingredient, result);
		}

		@Override
		public JuicerRecipe read(Identifier id, PacketByteBuf buf) {
			Ingredient ingredient = Ingredient.fromPacket(buf);
			ItemStack result = buf.readItemStack();
			return new JuicerRecipe(id, ingredient, result);
		}

		@Override
		public void write(PacketByteBuf buf, JuicerRecipe recipe) {
			recipe.ingredient.write(buf);
			buf.writeItemStack(recipe.result);
		}
	}
}