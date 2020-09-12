package tallestegg.guardvillagers;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.world.raid.Raid;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import tallestegg.guardvillagers.configuration.GuardConfig;
import tallestegg.guardvillagers.entities.GuardEntity;
import tallestegg.guardvillagers.items.DeferredSpawnEggItem;
import tallestegg.guardvillagers.renderer.GuardRenderer;

@Mod(GuardVillagers.MODID)
public class GuardVillagers {
    public static final String MODID = "guardvillagers";

    public GuardVillagers() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, GuardConfig.COMMON_SPEC);

        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new HandlerEvents());
        MinecraftForge.EVENT_BUS.register(new VillagerToGuard());
        GuardEntityType.ENTITIES.register(FMLJavaModLoadingContext.get().getModEventBus());
        GuardItems.ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        GuardSpawner.inject();
    }

    private void setup(final FMLCommonSetupEvent event) {
        if (GuardConfig.IllusionerRaids) {
            Raid.WaveMember.create("thebluemengroup", EntityType.ILLUSIONER, new int[] { 0, 0, 0, 0, 0, 1, 1, 2 });
        }
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        RenderingRegistry.registerEntityRenderingHandler(GuardEntityType.GUARD.get(), GuardRenderer::new);
    }

    private void enqueueIMC(final InterModEnqueueEvent event) {

    }

    private void processIMC(final InterModProcessEvent event) {

    }

    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) {

    }

    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        @SubscribeEvent
        public static void registerSpawnEggs(final RegistryEvent.Register<Item> event) {
            event.getRegistry().registerAll(new SpawnEggItem(EntityType.ILLUSIONER, 9804699, 4547222, new Item.Properties().group(ItemGroup.MISC)).setRegistryName(GuardVillagers.MODID, "illusioner_spawn_egg"),
                    new SpawnEggItem(EntityType.IRON_GOLEM, 12960449, 16769484, new Item.Properties().group(ItemGroup.MISC)).setRegistryName(GuardVillagers.MODID, "iron_golem_spawn_egg"),
                    new SpawnEggItem(EntityType.SNOW_GOLEM, 15663103, 16753185, new Item.Properties().group(ItemGroup.MISC)).setRegistryName(GuardVillagers.MODID, "snow_golem_spawn_egg"));
        }

        @SubscribeEvent(priority = EventPriority.LOWEST)
        public static void imstuff(final RegistryEvent.Register<EntityType<?>> event) {
            GlobalEntityTypeAttributes.put(GuardEntityType.GUARD.get(), GuardEntity.func_234200_m_().func_233813_a_());
            DeferredSpawnEggItem.initUnaddedEggs(); //credit : Cadiboo
        }
    }
}
