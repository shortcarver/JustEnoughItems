package mezz.jei.common.gui.elements;

import com.mojang.blaze3d.systems.RenderSystem;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.ingredients.IIngredientRenderer;
import mezz.jei.api.ingredients.ITypedIngredient;
import mezz.jei.api.runtime.IIngredientManager;
import mezz.jei.common.util.SafeIngredientUtil;
import net.minecraft.client.gui.GuiGraphics;

public class DrawableIngredient<V> implements IDrawable {
	private final IIngredientManager ingredientManager;
	private final ITypedIngredient<V> typedIngredient;
	private final IIngredientRenderer<V> ingredientRenderer;

	public DrawableIngredient(IIngredientManager ingredientManager, ITypedIngredient<V> typedIngredient, IIngredientRenderer<V> ingredientRenderer) {
		this.ingredientManager = ingredientManager;
		this.typedIngredient = typedIngredient;
		this.ingredientRenderer = ingredientRenderer;
	}

	@Override
	public int getWidth() {
		return this.ingredientRenderer.getWidth();
	}

	@Override
	public int getHeight() {
		return this.ingredientRenderer.getHeight();
	}

	@Override
	public void draw(GuiGraphics guiGraphics) {
		RenderSystem.enableDepthTest();
		SafeIngredientUtil.render(ingredientManager, ingredientRenderer, guiGraphics, typedIngredient);
		RenderSystem.disableDepthTest();
	}

	@Override
	public void draw(GuiGraphics guiGraphics, int xOffset, int yOffset) {
		var poseStack = guiGraphics.pose();
		poseStack.pushPose();
		{
			poseStack.translate(xOffset, yOffset, 0);
			draw(guiGraphics);
		}
		poseStack.popPose();
	}
}
