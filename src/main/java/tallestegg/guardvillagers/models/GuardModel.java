package tallestegg.guardvillagers.models;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.renderer.entity.model.IHasArm;
import net.minecraft.client.renderer.entity.model.IHasHead;
import net.minecraft.client.renderer.entity.model.SegmentedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.item.Items;
import net.minecraft.util.HandSide;
import net.minecraft.util.math.MathHelper;
import tallestegg.guardvillagers.entities.GuardEntity;

public class GuardModel extends SegmentedModel<GuardEntity> implements IHasArm, IHasHead  {
    public ModelRenderer Torso;
    public ModelRenderer LegR;
    public ModelRenderer LegL;
    public ModelRenderer ArmL;
    public ModelRenderer ArmR;
    public ModelRenderer Head;
    public ModelRenderer LegRDagger;
    public ModelRenderer ArmLShoulderPad;
    public ModelRenderer ArmRShoulderPad;
    public ModelRenderer Nose;
    public ModelRenderer HelmetDetail;
    public ModelRenderer headLayer2;

    public GuardModel() {
        this.textureWidth = 128;
        this.textureHeight = 128;
        this.LegL = new ModelRenderer(this, 16, 48);
        this.LegL.mirror = true;
        this.LegL.setRotationPoint(-1.9F, 12.0F, 0.0F);
        this.LegL.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F);
        this.Nose = new ModelRenderer(this, 54, 0);
        this.Nose.setRotationPoint(0.0F, -4.0F, -4.0F);
        this.Nose.addBox(-1.0F, 0.0F, -2.0F, 2, 4, 2, 0.0F);
        this.ArmL = new ModelRenderer(this, 32, 75);
        this.ArmL.mirror = true;
        this.ArmL.setRotationPoint(-5.0F, 2.0F, 0.0F);
        this.ArmL.addBox(-3.0F, -2.0F, -2.0F, 4, 12, 4, 0.0F);
        this.LegR = new ModelRenderer(this, 16, 28);
        this.LegR.setRotationPoint(1.9F, 12.0F, 0.0F);
        this.LegR.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F);
        this.setRotateAngle(LegR, -0.045553093477052F, 0.0F, 0.0F);
        this.LegRDagger = new ModelRenderer(this, 35, 71);
        this.LegRDagger.setRotationPoint(0.5F, -4.0F, -2.5F);
        this.LegRDagger.addBox(0.0F, 0.0F, -3.5F, 16, 0, 16, 0.0F);
        this.setRotateAngle(LegRDagger, 0.0F, 0.0F, 1.2217304763960306F);
        this.HelmetDetail = new ModelRenderer(this, -10, 100);
        this.HelmetDetail.setRotationPoint(0.0F, -6.5F, 0.0F);
        this.HelmetDetail.addBox(-9.0F, 0.0F, -9.0F, 18, 0, 18, 0.0F);
        this.headLayer2 = new ModelRenderer(this, 0, 0);
        this.headLayer2.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.headLayer2.addBox(-4.5F, -11.0F, -4.5F, 9, 11, 9, 0.0F);
        this.ArmR = new ModelRenderer(this, 33, 48);
        this.ArmR.setRotationPoint(5.0F, 2.0F, 0.0F);
        this.ArmR.addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4, 0.0F);
        this.Head = new ModelRenderer(this, 49, 99);
        this.Head.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Head.addBox(-4.0F, -10.0F, -4.0F, 8, 10, 8, 0.0F);
        this.ArmLShoulderPad = new ModelRenderer(this, 72, 33);
        this.ArmLShoulderPad.setRotationPoint(0.0F, -3.5F, 0.0F);
        this.ArmLShoulderPad.addBox(-6.0F, 0.0F, -3.0F, 6, 4, 6, 0.0F);
        this.setRotateAngle(ArmLShoulderPad, 0.0F, 0.0F, -0.3490658503988659F);
        this.ArmRShoulderPad = new ModelRenderer(this, 40, 20);
        this.ArmRShoulderPad.setRotationPoint(0.0F, -3.5F, 0.0F);
        this.ArmRShoulderPad.addBox(0.0F, 0.0F, -3.0F, 6, 4, 6, 0.0F);
        this.setRotateAngle(ArmRShoulderPad, 0.0F, 0.0F, 0.3490658503988659F);
        this.Torso = new ModelRenderer(this, 52, 50);
        this.Torso.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Torso.addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4, 0.25F);
        this.Torso.addChild(this.LegL);
        this.Head.addChild(this.Nose);
        this.Torso.addChild(this.ArmL);
        this.Torso.addChild(this.LegR);
        this.LegR.addChild(this.LegRDagger);
        this.Head.addChild(this.HelmetDetail);
        this.Head.addChild(this.headLayer2);
        this.Torso.addChild(this.ArmR);
        this.Torso.addChild(this.Head);
        this.ArmL.addChild(this.ArmLShoulderPad);
        this.ArmR.addChild(this.ArmRShoulderPad);
    }

	public Iterable<ModelRenderer> getParts()
	{
		return ImmutableList.of(this.Head, this.Torso, this.LegL, this.LegR, this.ArmR, this.ArmL);
	}


	public void setRotateAngle(ModelRenderer ModelRenderer, float x, float y, float z) 
	{
		ModelRenderer.rotateAngleX = x;
		ModelRenderer.rotateAngleY = y;
		ModelRenderer.rotateAngleZ = z;
	}

    
	public void setRotationAngles(GuardEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) 
	{
		float f = MathHelper.sin(this.swingProgress * (float) Math.PI);
        float f1 = MathHelper.sin((1.0F - (1.0F - this.swingProgress) * (1.0F - this.swingProgress)) * (float) Math.PI);
        this.Head.rotateAngleY = netHeadYaw * ((float)Math.PI / 180F);
        this.Head.rotateAngleX = headPitch * ((float)Math.PI / 180F);
        if (this.isSitting) 
        {
            this.ArmR.rotateAngleX = (-(float)Math.PI / 5F);
            this.ArmR.rotateAngleY = 0.0F;
            this.ArmR.rotateAngleZ = 0.0F;
            this.ArmL.rotateAngleX = (-(float)Math.PI / 5F);
            this.ArmL.rotateAngleY = 0.0F;
            this.ArmL.rotateAngleZ = 0.0F;
            this.LegL.rotateAngleX = -1.4137167F;
            this.LegL.rotateAngleY = ((float)Math.PI / 10F);
            this.LegL.rotateAngleZ = 0.07853982F;
            this.LegR.rotateAngleX = -1.4137167F;
            this.LegR.rotateAngleY = (-(float)Math.PI / 10F);
            this.LegR.rotateAngleZ = -0.07853982F;
        } 
         else 
         {
           this.ArmR.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 2.0F * limbSwingAmount * 0.5F;
           this.ArmR.rotateAngleY = 0.0F;
           this.ArmR.rotateAngleZ = 0.0F;
           this.ArmL.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 2.0F * limbSwingAmount * 0.5F;
           this.ArmL.rotateAngleY = 0.0F;
           this.ArmL.rotateAngleZ = 0.0F;
           this.LegL.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount * 0.5F;
           this.LegL.rotateAngleY = 0.0F;
           this.LegL.rotateAngleZ = 0.0F;
           this.LegR.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount * 0.5F;
           this.LegR.rotateAngleY = 0.0F;
           this.LegR.rotateAngleZ = 0.0F;
           this.ArmR.rotateAngleZ += MathHelper.cos(ageInTicks * 0.09F) * 0.01F;
           this.ArmL.rotateAngleZ -= MathHelper.cos(ageInTicks * 0.09F) * 0.05F;
           this.ArmR.rotateAngleX += MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
           this.ArmL.rotateAngleX -= MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
         if (entityIn.getPrimaryHand() == HandSide.RIGHT) 
         {
           this.ArmR.rotateAngleX -= f * 2.2F - f1 * 0.4F;
         }
          else if (entityIn.getPrimaryHand() == HandSide.LEFT) 
          {
           this.ArmL.rotateAngleX -= f * 2.2F - f1 * 0.4F;
          }
        }
        if (entityIn.isHolding(Items.CROSSBOW)) 
        {
        	 if (entityIn.getPrimaryHand() == HandSide.RIGHT) 
	          {
        		 this.ArmR.rotateAngleY = 0.3F;
	             this.ArmL.rotateAngleY = -0.6F;
	             this.ArmR.rotateAngleX = (-(float)Math.PI / 2F) + this.Head.rotateAngleX + 0.1F;
	             this.ArmL.rotateAngleX = -1.5F + this.Head.rotateAngleX;
	          }
        	   else if (entityIn.getPrimaryHand() == HandSide.LEFT) 
	           {
        		     this.ArmR.rotateAngleY = -5.6F;
		             this.ArmL.rotateAngleY = -0.3F;
		             this.ArmR.rotateAngleX = (-(float)Math.PI / 2F) + this.Head.rotateAngleX + 0.1F;
		             this.ArmL.rotateAngleX = -1.5F + this.Head.rotateAngleX;
	           }
        }
        if (entityIn.isCharging()) {
           if (entityIn.getPrimaryHand() == HandSide.RIGHT) 
           {
            this.ArmR.rotateAngleY = -100.0F;
            this.ArmR.rotateAngleX = -0.97079635F;
            this.ArmL.rotateAngleX = -0.97079635F;
            this.ArmL.rotateAngleY =  MathHelper.cos(ageInTicks * 0.2F); //what shows the reloading animation
           }
           if (entityIn.getPrimaryHand() == HandSide.LEFT) 
           {
            this.ArmR.rotateAngleY = -100.0F;
            this.ArmR.rotateAngleX = -0.97079635F;
            this.ArmL.rotateAngleX = -0.97079635F;
            this.ArmR.rotateAngleY =  MathHelper.cos(ageInTicks * 0.2F); //what shows the reloading animation
           }
         }
       }
    
      public ModelRenderer getModelHead() 
      {
	    return this.Head;
	  }
	
	 public void translateHand(HandSide p_225599_1_, MatrixStack p_225599_2_) {
		  float f = p_225599_1_ == HandSide.RIGHT ? 2.0F : -1.0F;
	      ModelRenderer modelrenderer = this.getArmForSide(p_225599_1_);
	      modelrenderer.rotationPointX += f;
	      modelrenderer.translateRotate(p_225599_2_);
	      modelrenderer.rotationPointX -= f;
	  }

	   protected ModelRenderer getArmForSide(HandSide p_187074_1_) {
	      return p_187074_1_ == HandSide.LEFT ? this.ArmL : this.ArmR;
	   }
}