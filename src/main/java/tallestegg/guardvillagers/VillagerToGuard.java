package tallestegg.guardvillagers;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import tallestegg.guardvillagers.entities.GuardEntity;

@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
public class VillagerToGuard 
{
	@SubscribeEvent
	  public void onEntityInteract(PlayerInteractEvent.EntityInteract e) 
	    {
	     if ((e.getWorld()).isRemote) 
	    {
	      return;
	    }
	     ItemStack itemstack = e.getItemStack();
	     if (itemstack.getItem() instanceof SwordItem && e.getPlayer().isShiftKeyDown()) 
	     {
	       Entity target = e.getTarget();
	       if ((target instanceof VillagerEntity)) 
	       {
	         VillagerEntity villager = (VillagerEntity) e.getTarget();
	         this.VillagerConvert(villager, e);
	         if (!e.getPlayer().abilities.isCreativeMode)
	         itemstack.shrink(1);
	       } 
	} 
  }

	private void VillagerConvert(LivingEntity entity, PlayerInteractEvent.EntityInteract e) 
	{
		  if (entity instanceof VillagerEntity);
		    ItemStack itemstack = e.getItemStack();
		    GuardEntity guard = GuardEntityType.GUARD.create(entity.world);
		    VillagerEntity villager = (VillagerEntity)entity;
		    guard.copyLocationAndAnglesFrom(villager);
		    guard.setItemStackToSlot(EquipmentSlotType.MAINHAND, itemstack.copy());
		    int i = GuardEntity.getRandomTypeForBiome(guard.world, guard.getPosition());
		    guard.setGuardVariant(i);
		    if (villager.hasCustomName()) 
		    {
		      guard.setCustomName(villager.getCustomName());
		      guard.setCustomNameVisible(villager.isCustomNameVisible());
		    }
		    villager.world.addEntity(guard);
	        villager.remove();	 
	} 
}
