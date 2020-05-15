package tallestegg.guardvillagers;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import tallestegg.guardvillagers.items.GuardVillagerHatItem;

public class GuardItems 
{
	public static final DeferredRegister<Item> ITEMS = new DeferredRegister<>(ForgeRegistries.ITEMS, GuardVillagers.MODID);
	public static RegistryObject<Item> GUARD_VILLAGER_HAT = ITEMS.register("guard_villager_hat", () -> new GuardVillagerHatItem(new Item.Properties().group(ItemGroup.COMBAT)));
}
