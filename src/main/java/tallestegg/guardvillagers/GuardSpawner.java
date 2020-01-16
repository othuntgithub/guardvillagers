package tallestegg.guardvillagers;

import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.resources.IResourceManagerReloadListener;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;

@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
public class GuardSpawner {
	public GuardSpawner() 
	{
	     MinecraftForge.EVENT_BUS.<FMLServerStartingEvent>addListener(e -> {
	        final MinecraftServer server = e.getServer();
	        server.getResourceManager().addReloadListener((IResourceManagerReloadListener) mgr -> this.addGolem(server));
	        this.addGolem(server);
	    });
	}

	private void addGolem(final MinecraftServer server) {
	    final Template golem = server.getWorld(DimensionType.OVERWORLD).getSaveHandler()
	        .getStructureTemplateManager().getTemplate(new ResourceLocation("village/common/iron_golem"));
	    if (golem != null) {
	        final List<Template.EntityInfo> entities = ObfuscationReflectionHelper.getPrivateValue(Template.class, golem, "field_186271_b");
	        final CompoundNBT nbt = new CompoundNBT();
	        nbt.putString("id", "guardvillagers:guard");
	        nbt.putString("guard", ITextComponent.Serializer.toJson(new StringTextComponent("guard")));
	        nbt.putBoolean("PersistenceRequired", true);
	        nbt.putString("id", "guardvillagers:guard");;
	        entities.add(new Template.EntityInfo(new Vec3d(0.5D, 1.0D, 0.5D), new BlockPos(0, 1, 0), nbt));
	        final ListNBT items1 = new ListNBT();
	        items1.add(new ItemStack(Items.STONE_SWORD).write(new CompoundNBT()));
	        nbt.put("HandItems", items1);
	        nbt.putString("id", "guardvillagers:guard");
	        nbt.putString("guard", ITextComponent.Serializer.toJson(new StringTextComponent("guard")));
	        nbt.putBoolean("PersistenceRequired", true);
	        nbt.putString("id", "guardvillagers:guard");;
	        entities.add(new Template.EntityInfo(new Vec3d(0.5D, 1.0D, 0.5D), new BlockPos(0, 1, 0), nbt));
	        final ListNBT items2 = new ListNBT();
	        items2.add(new ItemStack(Items.STONE_SWORD).write(new CompoundNBT()));
	        nbt.put("HandItems", items2);
	        nbt.putString("id", "guardvillagers:guard");
	        nbt.putString("guard", ITextComponent.Serializer.toJson(new StringTextComponent("guard")));
	        nbt.putBoolean("PersistenceRequired", true);
	        nbt.putString("id", "guardvillagers:guard");;
	        entities.add(new Template.EntityInfo(new Vec3d(0.5D, 1.0D, 0.5D), new BlockPos(0, 1, 0), nbt));
	        final ListNBT items3 = new ListNBT();
	        items3.add(new ItemStack(Items.STONE_SWORD).write(new CompoundNBT()));
	        nbt.put("HandItems", items3);
	        nbt.putString("id", "guardvillagers:guard");
	        nbt.putString("guard", ITextComponent.Serializer.toJson(new StringTextComponent("guard")));
	        nbt.putBoolean("PersistenceRequired", true);
	        nbt.putString("id", "guardvillagers:guard");;
	        entities.add(new Template.EntityInfo(new Vec3d(0.5D, 1.0D, 0.5D), new BlockPos(0, 1, 0), nbt));
	        final ListNBT items4 = new ListNBT();
	        items4.add(new ItemStack(Items.STONE_SWORD).write(new CompoundNBT()));
	        nbt.put("HandItems", items4);
	        nbt.putString("id", "guardvillagers:guard");
	        nbt.putString("guard", ITextComponent.Serializer.toJson(new StringTextComponent("guard")));
	        nbt.putBoolean("PersistenceRequired", true);
	        nbt.putString("id", "guardvillagers:guard");;
	        entities.add(new Template.EntityInfo(new Vec3d(0.5D, 1.0D, 0.5D), new BlockPos(0, 1, 0), nbt));
	        final ListNBT items = new ListNBT();
	        items.add(new ItemStack(Items.STONE_SWORD).write(new CompoundNBT()));
	        nbt.put("HandItems", items);
	    }
	}
}