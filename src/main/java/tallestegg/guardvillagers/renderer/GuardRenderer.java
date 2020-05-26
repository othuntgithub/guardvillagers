package tallestegg.guardvillagers.renderer;

import javax.annotation.Nullable;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.BipedRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.layers.BipedArmorLayer;
import net.minecraft.client.renderer.entity.layers.HeadLayer;
import net.minecraft.client.renderer.entity.layers.HeldItemLayer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.util.Hand;
import net.minecraft.util.HandSide;
import net.minecraft.util.ResourceLocation;
import tallestegg.guardvillagers.GuardVillagers;
import tallestegg.guardvillagers.entities.GuardEntity;
import tallestegg.guardvillagers.models.GuardArmorModel;
import tallestegg.guardvillagers.models.GuardModel;


public class GuardRenderer extends BipedRenderer<GuardEntity, GuardModel>
{

    public GuardRenderer(EntityRendererManager manager) 
    {
        super(manager, new GuardModel(0), 0.5f);
        this.addLayer(new HeldItemLayer<>(this));
        this.addLayer(new HeadLayer<>(this));
        this.addLayer(new BipedArmorLayer(this, new GuardArmorModel(0.5F), new GuardArmorModel(0.5F)));
    }
    
    public void render(GuardEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        this.setModelVisibilities(entityIn);
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }
    
    private void setModelVisibilities(GuardEntity entityIn) {
        GuardModel guardmodel = this.getEntityModel();
           ItemStack itemstack = entityIn.getHeldItemMainhand();
           ItemStack itemstack1 = entityIn.getHeldItemOffhand();
           guardmodel.setVisible(true);
           BipedModel.ArmPose bipedmodel$armpose = this.getArmPose(entityIn, itemstack, itemstack1, Hand.MAIN_HAND);
           BipedModel.ArmPose bipedmodel$armpose1 = this.getArmPose(entityIn, itemstack, itemstack1, Hand.OFF_HAND);
           if (entityIn.getPrimaryHand() == HandSide.RIGHT) {
              guardmodel.rightArmPose = bipedmodel$armpose;
              guardmodel.leftArmPose = bipedmodel$armpose1;
           } else {
              guardmodel.rightArmPose = bipedmodel$armpose1;
              guardmodel.leftArmPose = bipedmodel$armpose;
           }
     }

    
    private BipedModel.ArmPose getArmPose(GuardEntity entityIn, ItemStack itemStackMain, ItemStack itemStackOff, Hand handIn) {
        BipedModel.ArmPose bipedmodel$armpose = BipedModel.ArmPose.EMPTY;
        ItemStack itemstack = handIn == Hand.MAIN_HAND ? itemStackMain : itemStackOff;
        if (!itemstack.isEmpty()) {
           if (entityIn.getItemInUseCount() > 0) {
              UseAction useaction = itemstack.getUseAction();
              if (useaction == UseAction.BLOCK) {
                 bipedmodel$armpose = BipedModel.ArmPose.BLOCK;
              } else if (useaction == UseAction.BOW) {
                 bipedmodel$armpose = BipedModel.ArmPose.BOW_AND_ARROW;
              } else if (useaction == UseAction.SPEAR) {
                 bipedmodel$armpose = BipedModel.ArmPose.THROW_SPEAR;
           }
          }
        }
        return bipedmodel$armpose;
     }

    @Nullable
    @Override
	public ResourceLocation getEntityTexture(GuardEntity entity) 
    { 
    	return new ResourceLocation(GuardVillagers.MODID, "textures/entity/guard/guard_" + entity.getGuardVariant() + ".png");	
    }
}

