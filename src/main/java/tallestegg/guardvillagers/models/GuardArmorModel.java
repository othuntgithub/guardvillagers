package tallestegg.guardvillagers.models;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.util.math.MathHelper;
import tallestegg.guardvillagers.entities.GuardEntity;

public class GuardArmorModel extends BipedModel<GuardEntity>
{
    public float floatthing2;
    
	public GuardArmorModel(float p_i1148_1_) {
		super(p_i1148_1_);
	    this.bipedHead = new ModelRenderer(this, 0, 0);
	    this.bipedHead.addBox(-4.0F, -10.0F, -4.0F, 8.0F, 8.0F, 8.0F, p_i1148_1_);
	    this.bipedHead.setRotationPoint(0.0F, 0.0F, 0.0F);
	}
	
	public void setRotationAngles(GuardEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netbipedHeadYaw, float bipedHeadPitch) 
	{
		super.setRotationAngles(entityIn, limbSwing, limbSwingAmount, ageInTicks, netbipedHeadYaw, bipedHeadPitch);
        if (((GuardEntity) entityIn).isKicking() && (entityIn.getItemStackFromSlot(EquipmentSlotType.LEGS).getItem() instanceof ArmorItem))
        {
        	 float f4 = MathHelper.clamp(this.floatthing2,  0.0F, 25.0F);
        	 this.bipedRightLeg.rotateAngleX = MathHelper.lerp(f4 / 25.0F, -1.40F, 1.05F);
        }
    }
	
    public void setLivingAnimations(GuardEntity entityIn, float limbSwing, float limbSwingAmount, float partialTick) {
        this.floatthing2 = (float)((GuardEntity) entityIn).getKickTicks();
        super.setLivingAnimations(entityIn, limbSwing, limbSwingAmount, partialTick);
     }
}
