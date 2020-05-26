package tallestegg.guardvillagers.models;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;

public class GuardArmorModel extends BipedModel<LivingEntity>
{

	public GuardArmorModel(float p_i1148_1_) {
		super(p_i1148_1_);
	    this.bipedHead = new ModelRenderer(this, 0, 0);
	    this.bipedHead.addBox(-4.0F, -10.0F, -4.0F, 8.0F, 8.0F, 8.0F, p_i1148_1_);
	    this.bipedHead.setRotationPoint(0.0F, 0.0F, 0.0F);
	    this.bipedBody = new ModelRenderer(this, 16, 16);
	    this.bipedBody.addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, p_i1148_1_);
	    this.bipedBody.setRotationPoint(0.0F, 0.0F, 0.0F);
	}
}
