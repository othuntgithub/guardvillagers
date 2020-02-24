package tallestegg.guardvillagers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.SpawnEggItem;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
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
import tallestegg.guardvillagers.renderer.GuardRenderer;
import tallestegg.guardvillagers.renderer.GuardRenderer2;

@Mod("guardvillagers")
public class GuardVillagers
{  
	public static final String MODID = "guardvillagers";
	
    private static final Logger LOGGER = LogManager.getLogger();

    public GuardVillagers() 
    {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, GuardConfig.CLIENT_SPEC);

        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new HandlerEvents());
        MinecraftForge.EVENT_BUS.register(new GuardEntityType());
        MinecraftForge.EVENT_BUS.register(new VillagerToGuard());
        
        GuardSpawner.inject();
    }

    private void setup(final FMLCommonSetupEvent event)
    {
        LOGGER.info("HELLO FROM PREINIT");
        LOGGER.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
    }

    private void doClientStuff(final FMLClientSetupEvent event) 
    {
    	if (GuardConfig.GuardModel == false)
    	RenderingRegistry.registerEntityRenderingHandler(GuardEntity.class, GuardRenderer::new);
    	else
        RenderingRegistry.registerEntityRenderingHandler(GuardEntity.class, GuardRenderer2::new);
    }

    private void enqueueIMC(final InterModEnqueueEvent event)
    {
        
    }

    private void processIMC(final InterModProcessEvent event)
    {

    }
    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event)
    {
       
    }
    
    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents 
    {
    	@SubscribeEvent
        public static void registerSpawnEggs(final RegistryEvent.Register<Item> event) 
    	{
    		 event.getRegistry().registerAll
    		 (
    		   new SpawnEggItem(GuardEntityType.GUARD, 5651507, 9804699, new Item.Properties().group(ItemGroup.MISC)).setRegistryName(GuardVillagers.MODID, "guard_spawn_egg"),
    		   new SpawnEggItem(EntityType.ILLUSIONER, 9804699, 4547222, new Item.Properties().group(ItemGroup.MISC)).setRegistryName(GuardVillagers.MODID, "illusioner_spawn_egg"),
    		   new SpawnEggItem(EntityType.IRON_GOLEM, 12960449, 16769484, new Item.Properties().group(ItemGroup.MISC)).setRegistryName(GuardVillagers.MODID, "iron_golem_spawn_egg"),
    		   new SpawnEggItem(EntityType.SNOW_GOLEM, 15663103, 16753185, new Item.Properties().group(ItemGroup.MISC)).setRegistryName(GuardVillagers.MODID, "snow_golem_spawn_egg")
    		 );
    	}
    }
}



