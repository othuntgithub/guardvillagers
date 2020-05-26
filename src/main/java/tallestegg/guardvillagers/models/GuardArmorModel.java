package tallestegg.guardvillagers.models;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.LivingEntity;

public class GuardArmorModel extends BipedModel<LivingEntity>
{

	public GuardArmorModel(float p_i1148_1_) {
		super(p_i1148_1_);
		this.bipedHead.addChild(this.bipedHeadwear);
	}
	
    public void setRotationAngles(LivingEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) 
    {
    	this.bipedHead.rotationPointY = -2;
    	this.bipedHeadwear.copyModelAngles(this.bipedHead);
    }

}
