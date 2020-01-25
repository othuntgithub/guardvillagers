package tallestegg.guardvillagers;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;
import tallestegg.guardvillagers.entities.GuardEntity;

@ObjectHolder(GuardVillagers.MODID)
@Mod.EventBusSubscriber(modid = GuardVillagers.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class GuardEntityType 
{
	public static final EntityType<GuardEntity> GUARD = EntityType.Builder.create(GuardEntity::new, EntityClassification.MISC).size(0.6F, 1.95F).setShouldReceiveVelocityUpdates(true).build("guard");

	@SubscribeEvent
    public static void registerEntity(RegistryEvent.Register<EntityType<?>> event)
	{
		event.getRegistry().register(GUARD.setRegistryName("guard"));
	}
}

