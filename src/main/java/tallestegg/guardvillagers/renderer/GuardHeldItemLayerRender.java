package tallestegg.guardvillagers.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.VillagerModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;

public class GuardHeldItemLayerRender<T extends LivingEntity> extends LayerRenderer<T, VillagerModel<T>> {
	   public GuardHeldItemLayerRender(IEntityRenderer<T, VillagerModel<T>> p_i226037_1_) {
		      super(p_i226037_1_);
		   }

		   public void render(MatrixStack p_225628_1_, IRenderTypeBuffer p_225628_2_, int p_225628_3_, T p_225628_4_, float p_225628_5_, float p_225628_6_, float p_225628_7_, float p_225628_8_, float p_225628_9_, float p_225628_10_) {
		      p_225628_1_.push();
		      p_225628_1_.translate(0.0D, (double)0.10F, (double)-0.3F);
		      p_225628_1_.rotate(Vector3f.ZP.rotationDegrees(450.0F));
		         p_225628_1_.rotate(Vector3f.XP.rotationDegrees(90.0F));
		         p_225628_1_.rotate(Vector3f.ZP.rotationDegrees(10.0F));
		         p_225628_1_.rotate(Vector3f.ZP.rotationDegrees(150.0F));
		      ItemStack itemstack = p_225628_4_.getItemStackFromSlot(EquipmentSlotType.MAINHAND);
		      Minecraft.getInstance().getFirstPersonRenderer().renderItemSide(p_225628_4_, itemstack, ItemCameraTransforms.TransformType.GROUND, false, p_225628_1_, p_225628_2_, p_225628_3_);
		      p_225628_1_.pop();
		   }
		}


	      
	     
