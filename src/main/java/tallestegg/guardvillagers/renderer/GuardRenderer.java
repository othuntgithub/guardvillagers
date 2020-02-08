package tallestegg.guardvillagers.renderer;

import javax.annotation.Nullable;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.HeldItemLayer;
import net.minecraft.util.ResourceLocation;
import tallestegg.guardvillagers.GuardVillagers;
import tallestegg.guardvillagers.entities.GuardEntity;
import tallestegg.guardvillagers.models.GuardModel;


public class GuardRenderer extends MobRenderer<GuardEntity, GuardModel<GuardEntity>>
{

    public GuardRenderer(EntityRendererManager manager) 
    {
        super(manager, new GuardModel<GuardEntity>(), 0.5f);
        this.addLayer(new HeldItemLayer<>(this));
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(GuardEntity entity) 
    {
    	return new ResourceLocation(GuardVillagers.MODID, "textures/entity/guard/guard_" + entity.getGuardVariant() + ".png");	
    }
}

