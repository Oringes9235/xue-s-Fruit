package com.xuefengpeng.fruit.recipe;

import com.xuefengpeng.fruit.XuesFruitMod;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

/**
 * ============================================================
 * 配方注册（Minecraft 1.20.1 API）
 * ============================================================
 * 注册榨汁机与烘干机的配方类型及对应序列化器。
 */
public final class ModRecipes {

	private ModRecipes() {
	}

	/** 榨汁配方类型 */
	public static final RecipeType<JuicerRecipe> JUICER_RECIPE_TYPE = registerType("juicing");
	/** 烘干配方类型 */
	public static final RecipeType<DryerRecipe> DRYER_RECIPE_TYPE = registerType("drying");

	/** 榨汁配方序列化器 */
	public static final RecipeSerializer<JuicerRecipe> JUICER_RECIPE_SERIALIZER =
			registerSerializer("juicing", new JuicerRecipe.Serializer());
	/** 烘干配方序列化器 */
	public static final RecipeSerializer<DryerRecipe> DRYER_RECIPE_SERIALIZER =
			registerSerializer("drying", new DryerRecipe.Serializer());

	/**
	 * 主注册入口（静态字段已完成注册）。
	 */
	public static void register() {
	}

	/**
	 * 注册配方类型。
	 */
	private static <T extends Recipe<?>> RecipeType<T> registerType(String name) {
		return Registry.register(Registries.RECIPE_TYPE, XuesFruitMod.id(name),
				new RecipeType<T>() {
					@Override
					public String toString() {
						return name;
					}
				});
	}

	/**
	 * 注册配方序列化器。
	 */
	private static <S extends RecipeSerializer<T>, T extends Recipe<?>> S registerSerializer(String name, S serializer) {
		return Registry.register(Registries.RECIPE_SERIALIZER, XuesFruitMod.id(name), serializer);
	}
}