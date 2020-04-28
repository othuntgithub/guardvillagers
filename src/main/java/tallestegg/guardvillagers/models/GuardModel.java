package tallestegg.guardvillagers.models;


import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.entity.model.IHasArm;
import net.minecraft.client.renderer.entity.model.IHasHead;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShieldItem;
import net.minecraft.util.HandSide;
import net.minecraft.util.math.MathHelper;
import tallestegg.guardvillagers.entities.GuardEntity;

/**
 * VillagerGuard - HadeZ
 * Created using Tabula 7.1.0
 */
public class GuardModel extends BipedModel<GuardEntity> implements IHasArm, IHasHead  {
    public RendererModel Torso;
    public RendererModel LegR;
    public RendererModel LegL;
    public RendererModel ArmL;
    public RendererModel ArmR;
    public RendererModel Head;
    public RendererModel LegRDagger;
    public RendererModel ArmLShoulderPad;
    public RendererModel ArmRShoulderPad;
    public RendererModel Nose;
    public RendererModel HelmetDetail;
    public RendererModel headLayer2;
    public float floatthing;

    public GuardModel(float f) {
        this.textureWidth = 128;
        this.textureHeight = 128;
        this.LegL = new RendererModel(this, 16, 48);
        this.LegL.mirror = true;
        this.LegL.setRotationPoint(-1.9F, 12.0F, 0.0F);
        this.LegL.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F);
        this.Nose = new RendererModel(this, 54, 0);
        this.Nose.setRotationPoint(0.0F, -3.0F, -4.0F);
        this.Nose.addBox(-1.0F, 0.0F, -2.0F, 2, 4, 2, 0.0F);
        this.ArmR = new RendererModel(this, 32, 75);
        this.ArmR.mirror = true;
        this.ArmR.setRotationPoint(-5.0F, 2.0F, 0.0F);
        this.ArmR.addBox(-3.0F, -2.0F, -2.0F, 4, 12, 4, 0.0F);
        this.LegR = new RendererModel(this, 16, 28);
        this.LegR.setRotationPoint(1.9F, 12.0F, 0.0F);
        this.LegR.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F);
        this.setRotateAngle(LegR, -0.045553093477052F, 0.0F, 0.0F);
        this.LegRDagger = new RendererModel(this, 35, 71);
        this.LegRDagger.setRotationPoint(0.5F, -4.0F, -2.5F);
        this.LegRDagger.addBox(0.0F, 0.0F, -3.5F, 16, 0, 16, 0.0F);
        this.setRotateAngle(LegRDagger, 0.0F, 0.0F, 1.2217304763960306F);
        this.HelmetDetail = new RendererModel(this, -10, 100);
        this.HelmetDetail.setRotationPoint(0.0F, -6.5F, 0.0F);
        this.HelmetDetail.addBox(-9.0F, 0.0F, -9.0F, 18, 0, 18, 0.0F);
        this.headLayer2 = new RendererModel(this, 0, 0);
        this.headLayer2.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.headLayer2.addBox(-4.5F, -11.0F, -4.5F, 9, 11, 9, 0.0F);
        this.ArmL = new RendererModel(this, 33, 48);
        this.ArmL.setRotationPoint(5.0F, 2.0F, 0.0F);
        this.ArmL.addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4, 0.0F);
        this.Head = new RendererModel(this, 49, 99);
        this.Head.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Head.addBox(-4.0F, -10.0F, -4.0F, 8, 10, 8, 0.0F);
        this.ArmRShoulderPad = new RendererModel(this, 72, 33);
        this.ArmRShoulderPad.setRotationPoint(0.0F, -3.5F, 0.0F);
        this.ArmRShoulderPad.addBox(-6.0F, 0.0F, -3.0F, 6, 4, 6, 0.0F);
        this.setRotateAngle(ArmRShoulderPad, 0.0F, 0.0F, -0.3490658503988659F);
        this.ArmLShoulderPad = new RendererModel(this, 40, 20);
        this.ArmLShoulderPad.setRotationPoint(0.0F, -3.5F, 0.0F);
        this.ArmLShoulderPad.addBox(0.0F, 0.0F, -3.0F, 6, 4, 6, 0.0F);
        this.setRotateAngle(ArmLShoulderPad, 0.0F, 0.0F, 0.3490658503988659F);
        this.Torso = new RendererModel(this, 52, 50);
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

    public void render(GuardEntity entity, float f, float f1, float f2, float f3, float f4, float f5) 
    { 
        this.Torso.render(f5);
    }

    public void setRotateAngle(RendererModel RendererModel, float x, float y, float z) {
        RendererModel.rotateAngleX = x;
        RendererModel.rotateAngleY = y;
        RendererModel.rotateAngleZ = z;
    }
    
    public void setRotationAngles(GuardEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor)
    {
    	ItemStack itemstack = entityIn.getHeldItem(entityIn.getActiveHand());
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
           this.ArmR.rotateAngleZ -= f * 1.0 - f1 * 1.0F;
          }
          if (entityIn.getPrimaryHand() == HandSide.LEFT) 
          {
           this.ArmL.rotateAngleX -= f * 2.2F - f1 * 0.4F;
           this.ArmL.rotateAngleZ -= f * 1.0 - f1 * 1.0F;
          }
          
          if (entityIn.isKicking())
          {
              this.LegL.rotateAngleX = -1.0F - 0.8F * MathHelper.sin(ageInTicks * 0.5F) * 0.01F;
          }
          
        if (itemstack.getItem() instanceof CrossbowItem && entityIn.isAggressive())
        {
            if (entityIn.getPrimaryHand() == HandSide.RIGHT) 
       	    {
              this.ArmR.rotateAngleY = -0.3F;
              this.ArmL.rotateAngleY = 0.6F;
              this.ArmR.rotateAngleX = (-(float)Math.PI / 2F) + this.Head.rotateAngleX + 0.1F;
              this.ArmL.rotateAngleX = -1.5F + this.Head.rotateAngleX;
       	    }
            if (entityIn.getPrimaryHand() == HandSide.LEFT) 
       	    {
                 this.ArmR.rotateAngleY = -0.6F;
                 this.ArmL.rotateAngleY = 0.3F;
                 this.ArmR.rotateAngleX = -1.5F + this.Head.rotateAngleX;
                 this.ArmL.rotateAngleX = (-(float)Math.PI / 2F) + this.Head.rotateAngleX + 0.1F;
       	    }
        }
        
        if (entityIn.isCharging() && itemstack.getItem() instanceof CrossbowItem) {
        	float f3 = (float)CrossbowItem.getChargeTime(entityIn.getActiveItemStack());
            if (entityIn.getPrimaryHand() == HandSide.RIGHT) 
            {
          	  this.ArmR.rotateAngleY = -0.8F;
                this.ArmR.rotateAngleX = -0.97079635F;
                this.ArmL.rotateAngleX = -0.97079635F;
                float f2 = MathHelper.clamp(this.floatthing, 0.0F, 25.0F);
                this.ArmL.rotateAngleY = MathHelper.lerp(f2 / f3, 0.4F, 0.85F);
                this.ArmL.rotateAngleX = MathHelper.lerp(f2 / f3, this.ArmL.rotateAngleX, (-(float)Math.PI / 2F));
            }
            if (entityIn.getPrimaryHand() == HandSide.LEFT) 
            {
                this.ArmL.rotateAngleY = 0.8F;
                this.ArmR.rotateAngleX = -0.97079635F;
                this.ArmL.rotateAngleX = -0.97079635F;
                float f2 = MathHelper.clamp(this.floatthing, 0.0F, 25.0F);
                this.ArmR.rotateAngleY = MathHelper.lerp(f2 / 25.0F, -0.4F, -0.85F);
                this.ArmR.rotateAngleX = MathHelper.lerp(f2 / 25.0F, this.ArmR.rotateAngleX, (-(float)Math.PI / 2F));
            }
        }
        
        if (itemstack.getItem() instanceof ShieldItem)
        {
            if (entityIn.getPrimaryHand() == HandSide.RIGHT) 
            {
              this.ArmL.rotateAngleX = this.ArmL.rotateAngleX * 0.5F - 0.9424779F;
              this.ArmL.rotateAngleY = ((float)Math.PI / 15F);
            }
            if (entityIn.getPrimaryHand() == HandSide.LEFT) 
            {
            	this.ArmR.rotateAngleX = this.ArmR.rotateAngleX * 0.5F - 0.9424779F;
                this.ArmR.rotateAngleY = (-(float)Math.PI / 15F);
            }
        }
       }
    
    public void setLivingAnimations(GuardEntity entityIn, float limbSwing, float limbSwingAmount, float partialTick) {
        this.floatthing = (float)entityIn.getItemInUseMaxCount();
        super.setLivingAnimations(entityIn, limbSwing, limbSwingAmount, partialTick);
     }
    
    private RendererModel getArm(HandSide p_191216_1_) 
    {
      return p_191216_1_ == HandSide.LEFT ? this.ArmL: this.ArmR;
    }

	@Override
	public RendererModel func_205072_a() 
	{
		return this.Head;
	}

	@Override
	   public void postRenderArm(float scale, HandSide side) {
		float f = side == HandSide.RIGHT ? 0.0F : 0.0F;
	      RendererModel renderermodel = this.getArm(side);
	      renderermodel.rotationPointX += f;
	      renderermodel.postRender(scale);
	      renderermodel.rotationPointX -= f;
	   }
}
