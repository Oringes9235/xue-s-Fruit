package com.xuefengpeng.fruit.screen;

import com.xuefengpeng.fruit.XuesFruitMod;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

/**
 * ============================================================
 * 榨汁机 GUI 渲染（Minecraft 1.20.1 API）
 * ============================================================
 * 采用原版熔炉风格布局：
 *   - 输入槽 (56,17)、燃料槽 (56,53)、输出槽 (116,35)
 *   - 进度箭头（水平填充）、燃料火焰（竖直填充）
 */
public class JuicerScreen extends HandledScreen<JuicerScreenHandler> {

	private static final Identifier TEXTURE = XuesFruitMod.id("textures/gui/juicer.png");

	/** 加工总进度（与 JuicerBlockEntity.PROCESS_TIME 保持一致） */
	private static final int PROCESS_TIME = 100;

	public JuicerScreen(JuicerScreenHandler handler, PlayerInventory inventory, Text title) {
		super(handler, inventory, title);
		this.backgroundHeight = 166;
		this.playerInventoryTitleY = this.backgroundHeight - 94;
	}

	@Override
	protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
		int x = (this.width - this.backgroundWidth) / 2;
		int y = (this.height - this.backgroundHeight) / 2;

		context.drawTexture(TEXTURE, x, y, 0, 0, this.backgroundWidth, this.backgroundHeight);

		// 处理进度箭头（水平填充，22x16，位于输入槽与输出槽之间）
		int progress = this.handler.getProgress();
		if (progress > 0) {
			int progressWidth = (int) (progress / (float) PROCESS_TIME * 22);
			context.drawTexture(TEXTURE, x + 79, y + 34, 176, 14, progressWidth, 16);
		}

		// 燃料条（竖直填充，14x14，位于燃料槽上方）
		int burnTime = this.handler.getBurnTime();
		int maxBurnTime = this.handler.getMaxBurnTime();
		if (burnTime > 0 && maxBurnTime > 0) {
			int fuelHeight = (int) (burnTime / (float) maxBurnTime * 14);
			context.drawTexture(TEXTURE, x + 56, y + 36 + (14 - fuelHeight), 176, 14 - fuelHeight, 14, fuelHeight);
		}
	}

	@Override
	public void render(DrawContext context, int mouseX, int mouseY, float delta) {
		super.render(context, mouseX, mouseY, delta);
		this.drawMouseoverTooltip(context, mouseX, mouseY);
	}
}