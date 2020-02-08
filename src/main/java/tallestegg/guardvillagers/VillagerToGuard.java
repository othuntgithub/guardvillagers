package tallestegg.guardvillagers;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
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
	     if (itemstack.getItem() instanceof SwordItem) {
	       Entity target = e.getTarget();
	       if ((target instanceof VillagerEntity)) {
	         PlayerEntity player = e.getPlayer();
	         VillagerEntity villager = (VillagerEntity) e.getTarget();
	        
	         if (player.inventory.getFirstEmptyStack() < 0) 
	        {
	           return;
	        }
	         this.VillagerConvert(villager);
	         
	         itemstack.shrink(1);
	   } 
	} 
  }

	private void VillagerConvert(LivingEntity entity) 
	{
	  if (entity instanceof VillagerEntity);
	   GuardEntity guard = GuardEntityType.GUARD.create(entity.world);
	   VillagerEntity villagerentity = (VillagerEntity)entity;
	   guard.copyLocationAndAnglesFrom(villagerentity);
	   guard.setItemStackToSlot(EquipmentSlotType.MAINHAND, new ItemStack(Items.STONE_SWORD));
	   villagerentity.world.addEntity(guard);
       villagerentity.remove();	 
	} 
}
