package tallestegg.guardvillagers;

import java.util.List;
import java.util.Random;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.resources.IResourceManagerReloadListener;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;

@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
public class GuardSpawner {
	
	protected final Random rand = new Random();
	
	public GuardSpawner() 
	{
	     MinecraftForge.EVENT_BUS.<FMLServerStartingEvent>addListener(e -> {
	        final MinecraftServer server = e.getServer();
	        server.getResourceManager().addReloadListener((IResourceManagerReloadListener) mgr -> this.addGolem(server));
	        this.addGolem(server);
	    });
	}

	private void addGolem(final MinecraftServer server) {
	    final Template golem = server.func_71218_a(DimensionType.OVERWORLD).getSaveHandler()
	        .getStructureTemplateManager().getTemplate(new ResourceLocation("village/common/iron_golem"));
	    if (golem != null) 
	    {
	        final List<Template.EntityInfo> entities = ObfuscationReflectionHelper.getPrivateValue(Template.class, golem, "field_186271_b");
	        final CompoundNBT nbt = new CompoundNBT();
	        final ListNBT items = new ListNBT();
	        items.add(new ItemStack(Items.IRON_SWORD).write(new CompoundNBT()));
	        nbt.put("HandItems", items);
	        nbt.putString("id", "guardvillagers:guard");
	        nbt.putBoolean("InitialSpawn", true);
	        nbt.putBoolean("PersistenceRequired", true);
	        nbt.putString("id", "guardvillagers:guard");
	        entities.add(new Template.EntityInfo(new Vec3d(0.5D, 1.0D, 0.5D), new BlockPos(0, 1, 0), nbt));
	        nbt.putString("id", "guardvillagers:guard");
	        nbt.putBoolean("PersistenceRequired", true);
	        nbt.putBoolean("InitialSpawn", true);
	        nbt.putString("id", "guardvillagers:guard");
	        entities.add(new Template.EntityInfo(new Vec3d(0.5D, 1.0D, 0.5D), new BlockPos(0, 1, 0), nbt));
	        nbt.putString("id", "guardvillagers:guard");
	        nbt.putBoolean("PersistenceRequired", true);
	        nbt.putBoolean("InitialSpawn", true);
	        nbt.putString("id", "guardvillagers:guard");;
	        entities.add(new Template.EntityInfo(new Vec3d(0.5D, 1.0D, 0.5D), new BlockPos(0, 1, 0), nbt));
	        nbt.putString("id", "guardvillagers:guard");
	        nbt.putBoolean("PersistenceRequired", true);
	        nbt.putBoolean("InitialSpawn", true);
	        nbt.putString("id", "guardvillagers:guard");
	        entities.add(new Template.EntityInfo(new Vec3d(0.5D, 1.0D, 0.5D), new BlockPos(0, 1, 0), nbt));
	        nbt.putString("id", "guardvillagers:guard");
	        nbt.putBoolean("PersistenceRequired", true);
	        nbt.putBoolean("InitialSpawn", true);
	        nbt.putString("id", "guardvillagers:guard");
	        entities.add(new Template.EntityInfo(new Vec3d(0.5D, 1.0D, 0.5D), new BlockPos(0, 1, 0), nbt));
	        }
	    }
	}