package tallestegg.guardvillagers.renderer;

import javax.annotation.Nullable;

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
        super(manager, new VillagerGuard<GuardEntity>(), 0.5F);
        this.addLayer(new HeldItemLayer<>(this));
    }
    
    

    @Nullable
    @Override
	public ResourceLocation getEntityTexture(GuardEntity entity) {
        return GUARD_TEXTURES;
    }
}
