package tallestegg.guardvillagers.renderer;

import javax.annotation.Nullable;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.HeldItemLayer;
import net.minecraft.util.ResourceLocation;
import tallestegg.guardvillagers.entities.GuardEntity;
import tallestegg.guardvillagers.models.VillagerGuard;


public class GuardRenderer extends MobRenderer<GuardEntity, VillagerGuard<GuardEntity>>
{
    private static final ResourceLocation GUARD_TEXTURES = new ResourceLocation("guardvillagers:textures/entity/guard/guard.png");

    public GuardRenderer(EntityRendererManager manager) 
    {
        super(manager, new VillagerGuard<GuardEntity>(), 0.5f);
        this.addLayer(new HeldItemLayer<>(this));
    }
    
    protected void func_225620_a_(VillagerGuard<GuardEntity> p_225620_1_, MatrixStack p_225620_2_, float p_225620_3_) {
        float f = 0.9375F;
        p_225620_2_.func_227862_a_(0.9375F, 0.9375F, 0.9375F);
    }

    @Nullable
    @Override
	public ResourceLocation getEntityTexture(GuardEntity entity) {
        return GUARD_TEXTURES;
    }
}
