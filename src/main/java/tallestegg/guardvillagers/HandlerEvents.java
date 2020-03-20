package tallestegg.guardvillagers;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.OpenDoorGoal;
import net.minecraft.entity.merchant.villager.AbstractVillagerEntity;
import net.minecraft.entity.monster.AbstractIllagerEntity;
import net.minecraft.entity.monster.EvokerEntity;
import net.minecraft.entity.monster.GhastEntity;
import net.minecraft.entity.monster.IllusionerEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.monster.RavagerEntity;
import net.minecraft.entity.monster.VexEntity;
import net.minecraft.entity.monster.WitchEntity;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.passive.PolarBearEntity;
import net.minecraft.world.Difficulty;
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
		if (GuardConfig.AttackAllMobs == true)
	    if(event.getEntity() instanceof MonsterEntity) 
	    {
	       MonsterEntity monster = (MonsterEntity)event.getEntity();
	       monster.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(monster, GuardEntity.class, false));
	       monster.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(monster, AbstractVillagerEntity.class, false));
	    }
	    
		if (GuardConfig.AttackAllMobs == true)
	    if(event.getEntity() instanceof GhastEntity) 
		{
	    	GhastEntity ghast = (GhastEntity)event.getEntity();
	    	ghast.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(ghast, GuardEntity.class, true));   
		}
	    
		  if(event.getEntity() instanceof AbstractIllagerEntity) 
		   {
			 AbstractIllagerEntity illager = (AbstractIllagerEntity)event.getEntity();
		     illager.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(illager, GuardEntity.class, false));   
		     illager.goalSelector.addGoal(1, new OpenDoorGoal(illager, true));
		     illager.goalSelector.addGoal(3, new AvoidEntityGoal<>(illager, PolarBearEntity.class, 6.0F, 1.0D, 1.2D)); //be real here you'd probably be scared too if you saw a polar bear running at you
		     if (GuardConfig.RaidAnimals == true)
		     if (illager.isRaidActive()) 
		     {
		    	illager.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(illager, AnimalEntity.class, false));   
		     }
		   }
		   
		   if(event.getEntity() instanceof AbstractVillagerEntity) 
		   {
			 AbstractVillagerEntity villager = (AbstractVillagerEntity)event.getEntity();
		     villager.goalSelector.addGoal(3, new AvoidEntityGoal<>(villager, PolarBearEntity.class, 6.0F, 1.0D, 1.2D)); //no really its common sense if you saw a polar bear you'd probably run too
		   }
		   
	
	if(event.getEntity() instanceof IronGolemEntity) 
	{
	   IronGolemEntity golem = (IronGolemEntity)event.getEntity();
	   golem.getAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(100.0D);
	   golem.getAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(10.0D);
	   golem.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(golem, WitchEntity.class, false));  
	}
	
	if(event.getEntity() instanceof VexEntity) 
	{
	   VexEntity vex = (VexEntity)event.getEntity();
	   vex.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(vex, GuardEntity.class, false));   
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
	     if (ravager.isRaidActive())
          {
	       ravager.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(ravager, AnimalEntity.class, false));   
	      }
	}
	if(event.getEntity() instanceof WitchEntity) 
	{
		WitchEntity witch = (WitchEntity)event.getEntity();
		 witch.goalSelector.addGoal(4, new OpenDoorGoal(witch, true));
		 if (GuardConfig.WitchesVillager == true)
		  if (witch.isRaidActive()) 
		  {
            witch.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(witch, AbstractVillagerEntity.class, false));   
	        witch.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(witch, GuardEntity.class, false));  
	        witch.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(witch, IronGolemEntity.class, false));   
		  }
	    if (GuardConfig.RaidAnimals == true)
	    if (witch.isRaidActive()) 
	    {
	      witch.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(witch, AnimalEntity.class, false));   
	    }
	    
	    if (event.getEntity() instanceof IllusionerEntity) 
		{
	    	IllusionerEntity illusioner = (IllusionerEntity)event.getEntity();
	    	illusioner.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(illusioner, GuardEntity.class, false));  
		    if (GuardConfig.RaidAnimals == true)
		    if (illusioner.isRaidActive()) 
		    {
		      illusioner.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(illusioner, AnimalEntity.class, false));   
		    }
		}   
	}
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
             illager.getRaid().joinRaid(illager.getRaid().getWaves(Difficulty.HARD), illusioner, illager.getPosition(), false);
           } 
           }
       }
   }	
 }




