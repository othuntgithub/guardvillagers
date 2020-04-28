package tallestegg.guardvillagers.renderer;

import javax.annotation.Nullable;

import net.minecraft.client.renderer.entity.BipedRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.layers.BipedArmorLayer;
import net.minecraft.client.renderer.entity.layers.HeadLayer;
import net.minecraft.client.renderer.entity.layers.HeldItemLayer;
import net.minecraft.item.CrossbowItem;
import net.minecraft.util.ResourceLocation;
import tallestegg.guardvillagers.GuardVillagers;
import tallestegg.guardvillagers.entities.GuardEntity;
import tallestegg.guardvillagers.models.GuardModel;


public class GuardRenderer extends BipedRenderer<GuardEntity, GuardModel>
{

    public GuardRenderer(EntityRendererManager manager) 
    {
        super(manager, new GuardModel(0), 0.5f);
        this.addLayer(new HeldItemLayer<>(this));
        this.addLayer(new HeadLayer<>(this));
        this.addLayer(new BipedArmorLayer<>(this, new GuardModel(0.0F), new GuardModel(0.0F)));
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(GuardEntity entity) 
    { 
    	if (!(entity.getHeldItemMainhand().getItem() instanceof CrossbowItem)) {
    	return new ResourceLocation(GuardVillagers.MODID, "textures/entity/guard/guard_" + entity.getGuardVariant() + ".png");	
    	} else {
    		return new ResourceLocation(GuardVillagers.MODID, "textures/entity/guard/archer_" + entity.getGuardVariant() + ".png");	
    	}
    }
}

