package tallestegg.guardvillagers.renderer;

import com.mojang.blaze3d.platform.GlStateManager;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.VillagerModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;

public class GuardHeldItemLayerRender<T extends LivingEntity> extends LayerRenderer<T, VillagerModel<T>> {
	   private final ItemRenderer field_215347_a = Minecraft.getInstance().getItemRenderer();

	   public GuardHeldItemLayerRender(IEntityRenderer<T, VillagerModel<T>> p_i50917_1_) {
	      super(p_i50917_1_);
	   }

	   public void render(T entityIn, float p_212842_2_, float p_212842_3_, float p_212842_4_, float p_212842_5_, float p_212842_6_, float p_212842_7_, float p_212842_8_) {
	      ItemStack itemstack = entityIn.getItemStackFromSlot(EquipmentSlotType.MAINHAND);
	      if (!itemstack.isEmpty()) {
	         Item item = itemstack.getItem();
	         Block block = Block.getBlockFromItem(item);
	         GlStateManager.pushMatrix();
	         boolean flag = this.field_215347_a.shouldRenderItemIn3D(itemstack) && block.getRenderLayer() == BlockRenderLayer.TRANSLUCENT;
	         if (flag) {
	            GlStateManager.depthMask(false);
	         }
	         GlStateManager.translatef(0.0F, 0.15F, -0.4F);
	         GlStateManager.rotatef(-480.0F, 50.0F, -50.0F, 50.0F);
	         this.field_215347_a.renderItem(itemstack, entityIn, ItemCameraTransforms.TransformType.GROUND, false);
	         if (flag) {
	            GlStateManager.depthMask(true);
	         }

	         GlStateManager.popMatrix();
	      }
	   }

	   public boolean shouldCombineTextures() {
	      return false;
	   }
	}
