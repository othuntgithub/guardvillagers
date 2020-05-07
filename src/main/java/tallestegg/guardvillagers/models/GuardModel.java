package tallestegg.guardvillagers.models;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.entity.model.IHasArm;
import net.minecraft.client.renderer.entity.model.IHasHead;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.HandSide;
import net.minecraft.util.math.MathHelper;
import tallestegg.guardvillagers.entities.GuardEntity;

public class GuardModel extends BipedModel<GuardEntity> implements IHasArm, IHasHead  {
    public ModelRenderer bipedRightLegDagger;
    public ModelRenderer bipedLeftArmShoulderPad;
    public ModelRenderer bipedRightArmShoulderPad;
    public ModelRenderer Nose;
    public ModelRenderer HelmetDetail;
    public float floatthing;

    public GuardModel(float f) {
    	super(f);
        this.textureWidth = 128;
        this.textureHeight = 128;
        this.bipedLeftLeg = new ModelRenderer(this, 16, 48);
        this.bipedLeftLeg.mirror = true;
        this.bipedLeftLeg.setRotationPoint(-1.9F, 12.0F, 0.0F);
        this.bipedLeftLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F);
        this.Nose = new ModelRenderer(this, 54, 0);
        this.Nose.setRotationPoint(0.0F, -3.0F, -4.0F);
        this.Nose.addBox(-1.0F, 0.0F, -2.0F, 2, 4, 2, 0.0F);
        this.bipedRightArm = new ModelRenderer(this, 32, 75);
        this.bipedRightArm.mirror = true;
        this.bipedRightArm.setRotationPoint(-5.0F, 2.0F, 0.0F);
        this.bipedRightArm.addBox(-3.0F, -2.0F, -2.0F, 4, 12, 4, 0.0F);
        this.bipedRightLeg = new ModelRenderer(this, 16, 28);
        this.bipedRightLeg.setRotationPoint(1.9F, 12.0F, 0.0F);
        this.bipedRightLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F);
        this.setRotateAngle(bipedRightLeg, -0.045553093477052F, 0.0F, 0.0F);
        this.bipedRightLegDagger = new ModelRenderer(this, 35, 71);
        this.bipedRightLegDagger.setRotationPoint(0.5F, -4.0F, -2.5F);
        this.bipedRightLegDagger.addBox(0.0F, 0.0F, -3.5F, 16, 0, 16, 0.0F);
        this.setRotateAngle(bipedRightLegDagger, 0.0F, 0.0F, 1.2217304763960306F);
        this.HelmetDetail = new ModelRenderer(this, -10, 100);
        this.HelmetDetail.setRotationPoint(0.0F, -6.5F, 0.0F);
        this.HelmetDetail.addBox(-9.0F, 0.0F, -9.0F, 18, 0, 18, 0.0F);
        this.bipedHeadwear = new ModelRenderer(this, 0, 0);
        this.bipedHeadwear.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.bipedHeadwear.addBox(-4.5F, -11.0F, -4.5F, 9, 11, 9, 0.0F);
        this.bipedLeftArm = new ModelRenderer(this, 33, 48);
        this.bipedLeftArm.setRotationPoint(5.0F, 2.0F, 0.0F);
        this.bipedLeftArm.addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4, 0.0F);
        this.bipedHead = new ModelRenderer(this, 49, 99);
        this.bipedHead.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.bipedHead.addBox(-4.0F, -10.0F, -4.0F, 8, 10, 8, 0.0F);
        this.bipedLeftArmShoulderPad = new ModelRenderer(this, 72, 33);
        this.bipedLeftArmShoulderPad.setRotationPoint(0.0F, -3.5F, 0.0F);
        this.bipedLeftArmShoulderPad.addBox(-6.0F, 0.0F, -3.0F, 6, 4, 6, 0.0F);
        this.setRotateAngle(bipedLeftArmShoulderPad, 0.0F, 0.0F, -0.3490658503988659F);
        this.bipedRightArmShoulderPad = new ModelRenderer(this, 40, 20);
        this.bipedRightArmShoulderPad.setRotationPoint(0.0F, -3.5F, 0.0F);
        this.bipedRightArmShoulderPad.addBox(0.0F, 0.0F, -3.0F, 6, 4, 6, 0.0F);
        this.setRotateAngle(bipedRightArmShoulderPad, 0.0F, 0.0F, 0.3490658503988659F);
        this.bipedBody = new ModelRenderer(this, 52, 50);
        this.bipedBody.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.bipedBody.addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4, 0.25F);
        this.bipedHead.addChild(this.Nose);
        this.bipedRightLeg.addChild(this.bipedRightLegDagger);
        this.bipedHead.addChild(this.HelmetDetail);
        this.bipedLeftArm.addChild(this.bipedRightArmShoulderPad);
        this.bipedRightArm.addChild(this.bipedLeftArmShoulderPad);
    }

	public void setRotateAngle(ModelRenderer ModelRenderer, float x, float y, float z) 
	{
		ModelRenderer.rotateAngleX = x;
		ModelRenderer.rotateAngleY = y;
		ModelRenderer.rotateAngleZ = z;
	}

    
	public void setRotationAngles(GuardEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netbipedHeadYaw, float bipedHeadPitch) 
	{
		super.setRotationAngles(entityIn, limbSwing, limbSwingAmount, ageInTicks, netbipedHeadYaw, bipedHeadPitch);
		ItemStack itemstack = entityIn.getHeldItem(entityIn.getActiveHand());
		if (itemstack.getItem() instanceof CrossbowItem && entityIn.isAggressive())
        {
            if (entityIn.getPrimaryHand() == HandSide.RIGHT) 
       	    {
                this.bipedRightArm.rotateAngleY = -0.6F;
                this.bipedLeftArm.rotateAngleY = 0.3F;
                this.bipedRightArm.rotateAngleX = -1.5F + this.bipedHead.rotateAngleX;
                this.bipedLeftArm.rotateAngleX = (-(float)Math.PI / 2F) + this.bipedHead.rotateAngleX + 0.1F;
       	    }
            if (entityIn.getPrimaryHand() == HandSide.LEFT) 
       	    {
                this.bipedRightArm.rotateAngleY = -0.3F;
                this.bipedLeftArm.rotateAngleY = 0.6F;
                this.bipedRightArm.rotateAngleX = (-(float)Math.PI / 2F) + this.bipedHead.rotateAngleX + 0.1F;
                this.bipedLeftArm.rotateAngleX = -1.5F + this.bipedHead.rotateAngleX;
       	    }
        }
        
        if (entityIn.isCharging() && itemstack.getItem() instanceof CrossbowItem) {
        	float f3 = (float)CrossbowItem.getChargeTime(entityIn.getActiveItemStack());
            if (entityIn.getPrimaryHand() == HandSide.RIGHT) 
            {
          	    this.bipedRightArm.rotateAngleY = -0.8F;
                this.bipedRightArm.rotateAngleX = -0.97079635F;
                this.bipedLeftArm.rotateAngleX = -0.97079635F;
                float f2 = MathHelper.clamp(this.floatthing, 0.0F, 25.0F);
                this.bipedLeftArm.rotateAngleY = MathHelper.lerp(f2 / f3, 0.4F, 0.85F);
                this.bipedLeftArm.rotateAngleX = MathHelper.lerp(f2 / f3, this.bipedLeftArm.rotateAngleX, (-(float)Math.PI / 2F));
            }
            
            if (entityIn.getPrimaryHand() == HandSide.LEFT) 
            {
                this.bipedLeftArm.rotateAngleY = 0.8F;
                this.bipedRightArm.rotateAngleX = -0.97079635F;
                this.bipedLeftArm.rotateAngleX = -0.97079635F;
                float f2 = MathHelper.clamp(this.floatthing, 0.0F, 25.0F);
                this.bipedRightArm.rotateAngleY = MathHelper.lerp(f2 / 25.0F, -0.4F, -0.85F);
                this.bipedRightArm.rotateAngleX = MathHelper.lerp(f2 / 25.0F, this.bipedRightArm.rotateAngleX, (-(float)Math.PI / 2F));
            }
        }
        
        if (entityIn.isKicking())
        {
            this.bipedLeftLeg.rotateAngleX = -1.0F - 0.8F * MathHelper.sin(ageInTicks * 0.5F) * 0.01F;
        }
    }
    
    public void setLivingAnimations(GuardEntity entityIn, float limbSwing, float limbSwingAmount, float partialTick) {
        this.floatthing = (float)entityIn.getItemInUseMaxCount();
        super.setLivingAnimations(entityIn, limbSwing, limbSwingAmount, partialTick);
     }
}