package tallestegg.guardvillagers.models;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.IHasArm;
import net.minecraft.client.renderer.entity.model.IHasHead;
import net.minecraft.client.renderer.entity.model.SegmentedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.HandSide;
import net.minecraft.util.math.MathHelper;
import tallestegg.guardvillagers.entities.GuardEntity;

public class VillagerGuard <T extends GuardEntity> extends SegmentedModel<T> implements IHasArm, IHasHead {
	public ModelRenderer Torso;
	public ModelRenderer LegL;
	public ModelRenderer LegR;
	public ModelRenderer ArmL;
	public ModelRenderer ArmR;
	public ModelRenderer Head;
	public ModelRenderer LegDagger;
	public ModelRenderer ArmShoulderPadL;
	public ModelRenderer ArmShoulderPadR;
	public ModelRenderer Nose;
	public ModelRenderer HelmetDetail;

	public VillagerGuard() {
		this.textureWidth = 64;
		this.textureHeight = 64;
		this.Head = new ModelRenderer(this, 0, 0);
		this.Head.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.Head.func_228301_a_(-4.0F, -10.0F, -4.0F, 8, 10, 8, 0.0F);
		this.LegL = new ModelRenderer(this, 16, 48);
		this.LegL.setRotationPoint(1.9F, 12.0F, 0.0F);
		this.LegL.func_228301_a_(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F);
		this.Nose = new ModelRenderer(this, 54, 0);
		this.Nose.setRotationPoint(0.0F, -4.0F, -4.0F);
		this.Nose.func_228301_a_(-1.0F, 0.0F, -2.0F, 2, 4, 2, 0.0F);
		this.ArmShoulderPadL = new ModelRenderer(this, 40, 20);
		this.ArmShoulderPadL.setRotationPoint(0.0F, -3.5F, 0.0F);
		this.ArmShoulderPadL.func_228301_a_(-7.0F, 0.0F, -3.5F, 7, 0, 7, 0.0F);
		this.setRotateAngle(ArmShoulderPadL, 0.0F, 0.0F, -0.3490658503988659F);
		this.ArmShoulderPadR = new ModelRenderer(this, 40, 20);
		this.ArmShoulderPadR.setRotationPoint(0.0F, -3.5F, 0.0F);
		this.ArmShoulderPadR.func_228301_a_(0.1F, 0.0F, -3.5F, 7, 0, 7, 0.0F);
		this.setRotateAngle(ArmShoulderPadR, 0.0F, 0.0F, 0.3490658503988659F);
		this.HelmetDetail = new ModelRenderer(this, -10, 20);
		this.HelmetDetail.setRotationPoint(0.0F, -6.5F, 0.0F);
		this.HelmetDetail.func_228301_a_(-7.0F, 0.0F, -7.0F, 14, 0, 14, 0.0F);
		this.LegR = new ModelRenderer(this, 16, 48);
		this.LegR.mirror = true;
		this.LegR.setRotationPoint(-1.9F, 12.0F, 0.0F);
		this.LegR.func_228301_a_(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F);
		this.ArmL = new ModelRenderer(this, 32, 48);
		this.ArmL.mirror = true;
		this.ArmL.setRotationPoint(-5.0F, 2.0F, 0.0F);
		this.ArmL.func_228301_a_(-3.0F, -2.0F, -2.0F, 4, 12, 4, 0.0F);
		this.Torso = new ModelRenderer(this, 36, 30);
		this.Torso.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.Torso.func_228301_a_(-4.0F, 0.0F, -2.0F, 8, 12, 4, 0.25F);
		this.ArmR = new ModelRenderer(this, 32, 48);
		this.ArmR.setRotationPoint(5.0F, 2.0F, 0.0F);
		this.ArmR.func_228301_a_(-1.0F, -2.0F, -2.0F, 4, 12, 4, 0.0F);
		this.LegDagger = new ModelRenderer(this, -6, 55);
		this.LegDagger.setRotationPoint(2.0F, -2.0F, 0.0F);
		this.LegDagger.func_228301_a_(0.0F, 0.0F, -3.5F, 7, 0, 7, 0.0F);
		this.setRotateAngle(LegDagger, 0.0F, 0.0F, 1.2217304763960306F);
		this.Torso.addChild(this.Head);
		this.Torso.addChild(this.LegL);
		this.Head.addChild(this.Nose);
		this.ArmL.addChild(this.ArmShoulderPadL);
		this.ArmR.addChild(this.ArmShoulderPadR);
		this.Head.addChild(this.HelmetDetail);
		this.Torso.addChild(this.LegR);
		this.Torso.addChild(this.ArmL);
		this.Torso.addChild(this.ArmR);
		this.LegL.addChild(this.LegDagger);
	}

	public Iterable<ModelRenderer> func_225601_a_()
	{
		return ImmutableList.of(this.Head, this.Torso, this.LegL, this.LegR, this.ArmR, this.ArmL);
	}


	public void setRotateAngle(ModelRenderer ModelRenderer, float x, float y, float z) {
		ModelRenderer.rotateAngleX = x;
		ModelRenderer.rotateAngleY = y;
		ModelRenderer.rotateAngleZ = z;
	}

	@Override
	public void func_225597_a_(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch)
	{

		this.LegL.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
		this.LegR.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
		this.ArmR.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 2.0F * limbSwingAmount * 0.5F;
		this.ArmL.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 2.0F * limbSwingAmount * 0.5F;
		this.ArmR.rotateAngleZ = 0.0F;
		this.ArmL.rotateAngleZ = 0.0F;

		this.Head.rotateAngleY = netHeadYaw * 0.017453292F;
		this.Head.rotateAngleX = headPitch * 0.017453292F;
	}

	@Override
	public ModelRenderer func_205072_a() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void func_225599_a_(HandSide p_225599_1_, MatrixStack p_225599_2_) {
		// TODO Auto-generated method stub
		
	}


	

}
