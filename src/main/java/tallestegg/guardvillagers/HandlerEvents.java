package tallestegg.guardvillagers;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.merchant.villager.AbstractVillagerEntity;
import net.minecraft.entity.monster.AbstractIllagerEntity;
import net.minecraft.entity.monster.EvokerEntity;
import net.minecraft.entity.monster.IllusionerEntity;
import net.minecraft.entity.monster.RavagerEntity;
import net.minecraft.entity.monster.WitchEntity;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import tallestegg.guardvillagers.configuration.GuardConfig;
import tallestegg.guardvillagers.entities.GuardEntity;

public class HandlerEvents 
{
	@SubscribeEvent
	public void onLivingSpawned(EntityJoinWorldEvent event) 
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
	    
	    if(event.getEntity() instanceof IllusionerEntity) 
		{
	    	IllusionerEntity illusioner = (IllusionerEntity)event.getEntity();
	    	illusioner.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(illusioner, GuardEntity.class, false));   
		    if (GuardConfig.RaidAnimals == true)
		    if (illusioner.isRaidActive()) {
		    	illusioner.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(illusioner, AnimalEntity.class, false));   
		    }
		}
	}
	
  }
	
	@SubscribeEvent
    public void onEntityJoin(EntityJoinWorldEvent event) 
	{
    World world = event.getWorld();
    if (GuardConfig.IllusionerRaids == true)
	if (event.getEntity() instanceof AbstractIllagerEntity && !(event.getEntity() instanceof EvokerEntity)) 
	{

        AbstractIllagerEntity illager = (AbstractIllagerEntity) event.getEntity();
        
        if (illager.getRaid() != null && !illager.isLeader() && world.rand.nextInt(8) == 0) 
        {
         for (int i = 0; i < 1 + world.rand.nextInt(1); i++) 
         {
         IllusionerEntity illusioner = EntityType.ILLUSIONER.create(world);
        illager.getRaid().func_221317_a(illager.getRaid().getWaves(world.getDifficulty()), illusioner, illager.getPosition(), false);
         }
            
            illager.remove();
      }
    }
  }
}
