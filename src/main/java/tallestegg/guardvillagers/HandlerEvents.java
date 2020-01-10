package tallestegg.guardvillagers;

import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.merchant.villager.AbstractVillagerEntity;
import net.minecraft.entity.monster.AbstractIllagerEntity;
import net.minecraft.entity.monster.RavagerEntity;
import net.minecraft.entity.monster.WitchEntity;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import tallestegg.guardvillagers.configuration.GuardConfig;
import tallestegg.guardvillagers.entities.GuardEntity;

public class HandlerEvents 
{
	@SubscribeEvent
	public static void onLivingSpawned(EntityJoinWorldEvent event) 
	{
		
	if(event.getEntity() instanceof AbstractIllagerEntity) 
	{
		AbstractIllagerEntity illager = (AbstractIllagerEntity)event.getEntity();
	    illager.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(illager, GuardEntity.class, false));   
	    if (GuardConfig.RaidAnimals == true)
	    if (illager.isRaidActive()) {
	    	illager.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(illager, AnimalEntity.class, false));   
	    }
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
	    if (GuardConfig.RaidAnimals == true)
	    if (ravager.isRaidActive()) {
	    	ravager.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(ravager, AnimalEntity.class, false));   
	    }
	}
	if(event.getEntity() instanceof WitchEntity) 
	{
		WitchEntity witch = (WitchEntity)event.getEntity();
	    witch.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(witch, GuardEntity.class, false));   
	    if (GuardConfig.WitchesVillager == true)
	    witch.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(witch, AbstractVillagerEntity.class, false));   
	    if (GuardConfig.RaidAnimals == true)
	    if (witch.isRaidActive()) {
	    	witch.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(witch, AnimalEntity.class, false));   
	    }
	}
	
  }
}
