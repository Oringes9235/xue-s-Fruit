package com.xuefengpeng.fruit.screen;

import com.xuefengpeng.fruit.XuesFruitMod;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

/**
 * ============================================================
 * 烘干机 GUI 渲染（Minecraft 1.20.1 API）
 * ============================================================
 * 负责在客户端绘制烘干机界面背景、进度条与燃料条。
 */
public class DryerScreen extends HandledScreen<DryerScreenHandler> {

	private static final Identifier TEXTURE = XuesFruitMod.id("textures/gui/dryer.png");

	public DryerScreen(DryerScreenHandler handler, PlayerInventory inventory, Text title) {
		super(handler, inventory, title);
		this.backgroundHeight = 166;
		this.playerInventoryTitleY = this.backgroundHeight - 94;
	}

	@Override
	protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
		int x = (this.width - this.backgroundWidth) / 2;
		int y = (this.height - this.backgroundHeight) / 2;

		context.drawTexture(TEXTURE, x, y, 0, 0, this.backgroundWidth, this.backgroundHeight);

		int progress = this.handler.getProgress();
		int totalCookTime = this.handler.getTotalCookTime();
		if (progress > 0 && totalCookTime > 0) {
			int progressHeight = (int) (progress / (float) totalCookTime * 16);
			context.drawTexture(TEXTURE, x + 86, y + 35, 176, 0, 22, progressHeight);
		}

		int burnTime = this.handler.getBurnTime();
		int maxBurnTime = this.handler.getMaxBurnTime();
		if (burnTime > 0 && maxBurnTime > 0) {
			int fuelHeight = (int) (burnTime / (float) maxBurnTime * 14);
			context.drawTexture(TEXTURE, x + 68, y + 53 + (14 - fuelHeight), 176, 30 - fuelHeight, 14, fuelHeight);
		}
	}

	@Override
	public void render(DrawContext context, int mouseX, int mouseY, float delta) {
		super.render(context, mouseX, mouseY, delta);
		this.drawMouseoverTooltip(context, mouseX, mouseY);
	}
}