package tallestegg.guardvillagers.models;
 
 
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.IHasArm;
import net.minecraft.client.renderer.entity.model.IHasHead;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.util.HandSide;
import net.minecraft.util.math.MathHelper;
import tallestegg.guardvillagers.entities.GuardEntity;
 
public class GuardModel <T extends GuardEntity> extends EntityModel<T> implements IHasArm, IHasHead {
    public RendererModel Torso;
    public RendererModel LegL;
    public RendererModel LegR;
    public RendererModel ArmL;
    public RendererModel ArmR;
    public RendererModel Head;
    public RendererModel LegDagger;
    public RendererModel ArmShoulderPadL;
    public RendererModel ArmShoulderPadR;
    public RendererModel Nose;
    public RendererModel HelmetDetail;
    
 
    public GuardModel() {
        this.textureWidth = 64;
        this.textureHeight = 64;
        this.Head = new RendererModel(this, 0, 0);
        this.Head.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Head.addBox(-4.0F, -10.0F, -4.0F, 8, 10, 8, 0.0F);
        this.LegL = new RendererModel(this, 16, 48);
        this.LegL.setRotationPoint(1.9F, 12.0F, 0.0F);
        this.LegL.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F);
        this.Nose = new RendererModel(this, 54, 0);
        this.Nose.setRotationPoint(0.0F, -4.0F, -4.0F);
        this.Nose.addBox(-1.0F, 0.0F, -2.0F, 2, 4, 2, 0.0F);
        this.ArmShoulderPadL = new RendererModel(this, 40, 20);
        this.ArmShoulderPadL.setRotationPoint(0.0F, -3.5F, 0.0F);
        this.ArmShoulderPadL.addBox(-7.0F, 0.0F, -3.5F, 7, 0, 7, 0.0F);
        this.setRotateAngle(ArmShoulderPadL, 0.0F, 0.0F, -0.3490658503988659F);
        this.ArmShoulderPadR = new RendererModel(this, 40, 20);
        this.ArmShoulderPadR.setRotationPoint(0.0F, -3.5F, 0.0F);
        this.ArmShoulderPadR.addBox(0.1F, 0.0F, -3.5F, 7, 0, 7, 0.0F);
        this.setRotateAngle(ArmShoulderPadR, 0.0F, 0.0F, 0.3490658503988659F);
        this.HelmetDetail = new RendererModel(this, -10, 20);
        this.HelmetDetail.setRotationPoint(0.0F, -6.5F, 0.0F);
        this.HelmetDetail.addBox(-7.0F, 0.0F, -7.0F, 14, 0, 14, 0.0F);
        this.LegR = new RendererModel(this, 16, 48);
        this.LegR.mirror = true;
        this.LegR.setRotationPoint(-1.9F, 12.0F, 0.0F);
        this.LegR.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F);
        this.ArmL = new RendererModel(this, 32, 48);
        this.ArmL.mirror = true;
        this.ArmL.setRotationPoint(-5.0F, 2.0F, 0.0F);
        this.ArmL.addBox(-3.0F, -2.0F, -2.0F, 4, 12, 4, 0.0F);
        this.Torso = new RendererModel(this, 36, 30);
        this.Torso.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Torso.addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4, 0.25F);
        this.ArmR = new RendererModel(this, 32, 48);
        this.ArmR.setRotationPoint(5.0F, 2.0F, 0.0F);
        this.ArmR.addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4, 0.0F);
        this.LegDagger = new RendererModel(this, -6, 55);
        this.LegDagger.setRotationPoint(2.0F, -2.0F, 0.0F);
        this.LegDagger.addBox(0.0F, 0.0F, -3.5F, 7, 0, 7, 0.0F);
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
    
    
 
    @Override
    public void render(T entityIn, float f, float f1, float f2, float f3, float f4, float f5) {
        this.setRotationAngles(entityIn, f, f1, f2, f3, f4, f5);
        this.Torso.render(f5);
        this.LegL.render(f5);
        this.LegR.render(f5);
        this.ArmL.render(f5);
        this.ArmR.render(f5);
        this.Head.render(f5);
    }
 
    public void setRotateAngle(RendererModel RendererModel, float x, float y, float z)
    {
        RendererModel.rotateAngleX = x;
        RendererModel.rotateAngleY = y;
        RendererModel.rotateAngleZ = z;
    }
   
    @Override
    public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor)
    {
        float f = MathHelper.sin(this.swingProgress * (float) Math.PI);
        float f1 = MathHelper.sin((1.0F - (1.0F - this.swingProgress) * (1.0F - this.swingProgress)) * (float) Math.PI);
        this.LegL.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        this.LegR.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
        this.ArmR.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 2.0F * limbSwingAmount * 0.5F;
        this.ArmL.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 2.0F * limbSwingAmount * 0.5F;
        this.ArmR.rotateAngleZ = 0.0F;
        this.ArmL.rotateAngleZ = 0.0F;
        this.Head.rotateAngleY = netHeadYaw * 0.017453292F;
        this.Head.rotateAngleX = headPitch * 0.017453292F;
       
        if (entityIn.getPrimaryHand() == HandSide.RIGHT) 
        {
          this.ArmR.rotateAngleX -= f * 2.2F - f1 * 0.4F;
        }
        else 
        {
          this.ArmL.rotateAngleX -= f * 2.2F - f1 * 0.4F;
        }
    }
 
    @Override
    public void postRenderArm(float scale, HandSide side) 
    {
        this.getArm(side).postRender(0.0870F);
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
}