package tallestegg.guardvillagers;

import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.monster.AbstractIllagerEntity;
import net.minecraft.entity.monster.RavagerEntity;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import tallestegg.guardvillagers.entities.GuardEntity;

public class HandlerEvents 
{
	@SubscribeEvent
	public static void onLivingSpawned(EntityJoinWorldEvent event) 
	{
		
	if(event.getEntity() instanceof AbstractIllagerEntity) 
	{
		AbstractIllagerEntity monster = (AbstractIllagerEntity)event.getEntity();
	    monster.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(monster, GuardEntity.class, false));   
	}
	
	if(event.getEntity() instanceof ZombieEntity) 
	{
		ZombieEntity zombie = (ZombieEntity)event.getEntity();
	    zombie.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(zombie, GuardEntity.class, false));   
	}
	if(event.getEntity() instanceof RavagerEntity) 
	{
		RavagerEntity ravager = (RavagerEntity)event.getEntity();
	    ravager.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(ravager, GuardEntity.class, false));   
	}
  }
}
