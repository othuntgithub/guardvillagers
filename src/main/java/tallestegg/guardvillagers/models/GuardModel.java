package tallestegg.guardvillagers.models;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShootableItem;
import net.minecraft.util.HandSide;
import net.minecraft.util.math.MathHelper;
import tallestegg.guardvillagers.entities.GuardEntity;

public class GuardModel extends BipedModel<GuardEntity> 
{
    public ModelRenderer quiver;
    public ModelRenderer ArmLShoulderPad;
    public ModelRenderer ArmRShoulderPad;
    public ModelRenderer Nose;
    public float floatthing;
	public float floatthing2;

    public GuardModel(float f) {
    	super(f);
        this.textureWidth = 128;
        this.textureHeight = 128;
        this.quiver = new ModelRenderer(this, 100, 0);
        this.quiver.setRotationPoint(0.5F, 3.0F, 2.1F);
        this.quiver.addBox(-2.5F, -2.0F, 0.0F, 5, 10, 5, 0.0F);
        this.setRotateAngle(quiver, 0.0F, 0.0F, 0.2617993877991494F);
        this.bipedHeadwear = new ModelRenderer(this, 0, 0);
        this.bipedHeadwear.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.bipedHeadwear.addBox(-4.5F, -11.0F, -4.5F, 9, 11, 9, 0.0F);
        this.bipedRightLeg = new ModelRenderer(this, 16, 48);
        this.bipedRightLeg.mirror = true;
        this.bipedRightLeg.setRotationPoint(-1.9F, 12.0F, 0.0F);
        this.bipedRightLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F);
        this.ArmLShoulderPad = new ModelRenderer(this, 72, 33);
        this.ArmLShoulderPad.setRotationPoint(0.5F, -3.5F, 0.0F);
        this.ArmLShoulderPad.addBox(-5.0F, 0.0F, -3.0F, 5, 3, 6, 0.0F);
        this.setRotateAngle(ArmLShoulderPad, 0.0F, 0.0F, -0.3490658503988659F);
        this.bipedLeftLeg = new ModelRenderer(this, 16, 28);
        this.bipedLeftLeg.setRotationPoint(1.9F, 12.0F, 0.0F);
        this.bipedLeftLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F);
        this.bipedRightArm = new ModelRenderer(this, 32, 75);
        this.bipedRightArm.mirror = true;
        this.bipedRightArm.setRotationPoint(-5.0F, 2.0F, 0.0F);
        this.bipedRightArm.addBox(-3.0F, -2.0F, -2.0F, 4, 12, 4, 0.0F);
        this.bipedHead = new ModelRenderer(this, 49, 99);
        this.bipedHead.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.bipedHead.addBox(-4.0F, -10.0F, -4.0F, 8, 10, 8, 0.0F);
        this.ArmRShoulderPad = new ModelRenderer(this, 40, 20);
        this.ArmRShoulderPad.setRotationPoint(-0.5F, -3.5F, 0.0F);
        this.ArmRShoulderPad.addBox(0.0F, 0.0F, -3.0F, 5, 3, 6, 0.0F);
        this.setRotateAngle(ArmRShoulderPad, 0.0F, 0.0F, 0.3490658503988659F);
        this.bipedLeftArm = new ModelRenderer(this, 33, 48);
        this.bipedLeftArm.setRotationPoint(5.0F, 2.0F, 0.0F);
        this.bipedLeftArm.addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4, 0.0F);
        this.Nose = new ModelRenderer(this, 54, 0);
        this.Nose.setRotationPoint(0.0F, -3.0F, -4.0F);
        this.Nose.addBox(-1.0F, 0.0F, -2.0F, 2, 4, 2, 0.0F);
        this.bipedBody = new ModelRenderer(this, 52, 50);
        this.bipedBody.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.bipedBody.addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4, 0.25F);
        this.bipedBody.addChild(this.quiver);
        this.bipedRightArm.addChild(this.ArmLShoulderPad);
        this.bipedLeftArm.addChild(this.ArmRShoulderPad);
        this.bipedHead.addChild(this.Nose);
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
		boolean flag = itemstack.getItem() instanceof ShootableItem;
		this.quiver.showModel = flag;
		boolean flag2 = entityIn.getItemStackFromSlot(EquipmentSlotType.CHEST).getItem() instanceof ArmorItem;
		this.ArmLShoulderPad.showModel = !flag2;
		this.ArmRShoulderPad.showModel = !flag2;
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
        
		float f3 = (float)CrossbowItem.getChargeTime(entityIn.getActiveItemStack());
        if (entityIn.isCharging() && itemstack.getItem() instanceof CrossbowItem) {
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
                this.bipedRightArm.rotateAngleY = MathHelper.lerp(f2 / f3, -0.4F, -0.85F);
                this.bipedRightArm.rotateAngleX = MathHelper.lerp(f2 / f3, this.bipedRightArm.rotateAngleX, (-(float)Math.PI / 2F));
            }
        }
        
        if (entityIn.isKicking())
        {
        	 float f4 = MathHelper.clamp(this.floatthing2,  0.0F, 25.0F);
        	 this.bipedRightLeg.rotateAngleX = MathHelper.lerp(f4 / 25.0F, -1.40F, 1.05F);
        }
    }
    
    public void setLivingAnimations(GuardEntity entityIn, float limbSwing, float limbSwingAmount, float partialTick) {
        this.floatthing = (float)entityIn.getItemInUseMaxCount();
        this.floatthing2 = (float)entityIn.getKickTicks();
        super.setLivingAnimations(entityIn, limbSwing, limbSwingAmount, partialTick);
     }
}