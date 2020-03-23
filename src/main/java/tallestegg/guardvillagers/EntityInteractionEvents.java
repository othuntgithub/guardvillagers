package tallestegg.guardvillagers;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.SwordItem;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import tallestegg.guardvillagers.entities.GuardEntity;

public class EntityInteractionEvents 
{
	 @SubscribeEvent
	 public void onEntityInteract(PlayerInteractEvent.EntityInteract e) 
	 {
	     ItemStack itemstack = e.getItemStack();
	     if (itemstack.getItem() instanceof SwordItem && e.getPlayer().isSneaking() || itemstack.getItem() instanceof CrossbowItem && e.getPlayer().isSneaking()) 
	     {
	       Entity target = e.getTarget();
	       if (target instanceof VillagerEntity)
	       {
	    	   VillagerEntity villager = (VillagerEntity) e.getTarget();
		         if (villager.isChild() == false) 
		         {
		          this.VillagerConvert(villager, e);
		          if (!e.getPlayer().abilities.isCreativeMode)
		          itemstack.shrink(1);
		         }
	           } 
	       }
           if (itemstack.getItem() == Items.IRON_INGOT) 
	       {
        	 Entity target = e.getTarget();
	    	 if (target instanceof IronGolemEntity) {
	    		IronGolemEntity golem = (IronGolemEntity) e.getTarget();
	    		golem.heal(25.0F);
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
