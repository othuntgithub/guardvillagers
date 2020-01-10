package tallestegg.guardvillagers.renderer;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.HeadLayer;
import net.minecraft.client.renderer.entity.model.VillagerModel;
import net.minecraft.util.ResourceLocation;
import tallestegg.guardvillagers.entities.GuardEntity;

public class GuardRenderer2 extends MobRenderer<GuardEntity, VillagerModel<GuardEntity>>
{
	 private static final ResourceLocation VILLAGER_TEXTURES = new ResourceLocation("textures/entity/villager/villager.png");
	 
	 public GuardRenderer2 (EntityRendererManager manager) 
		{
		 super(manager, new VillagerModel<>(0.0F), 0.5F);
		      this.addLayer(new HeadLayer<>(this));
		      this.addLayer(new GuardHeldItemLayerRender<>(this));
		}
	 
	 protected ResourceLocation getEntityTexture(GuardEntity entity) 
	 {
	      return VILLAGER_TEXTURES;
	 }
}
