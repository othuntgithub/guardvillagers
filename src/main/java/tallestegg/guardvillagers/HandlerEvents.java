package tallestegg.guardvillagers;

import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.merchant.villager.AbstractVillagerEntity;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.monster.AbstractIllagerEntity;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.monster.IllusionerEntity;
import net.minecraft.entity.monster.RavagerEntity;
import net.minecraft.entity.monster.WitchEntity;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.passive.PolarBearEntity;
import net.minecraft.pathfinding.GroundPathNavigator;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import tallestegg.guardvillagers.configuration.GuardConfig;
import tallestegg.guardvillagers.entities.GuardEntity;
import tallestegg.guardvillagers.entities.goals.HealGolemGoal;
import tallestegg.guardvillagers.entities.goals.HealGuardAndPlayerGoal;

public class HandlerEvents {
	@SubscribeEvent
	public void onLivingSpawned(EntityJoinWorldEvent event) {
		if (GuardConfig.AttackAllMobs)
		{
			if (event.getEntity() instanceof IMob)
			{
				MobEntity mob = (MobEntity) event.getEntity();
				mob.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(mob, GuardEntity.class, false));
			}
		}
		if (event.getEntity() instanceof AbstractIllagerEntity)
		{
			AbstractIllagerEntity illager = (AbstractIllagerEntity) event.getEntity();
			illager.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(illager, GuardEntity.class, false));
			if (GuardConfig.IllagersOpenDoors)
			{
				((GroundPathNavigator) illager.getNavigator()).setBreakDoors(true);
			}
			if (GuardConfig.IllagersRunFromPolarBears)
			{
				illager.goalSelector.addGoal(2, new AvoidEntityGoal<>(illager, PolarBearEntity.class, 6.0F, 1.0D, 1.2D));
			}  //common sense.
			if (GuardConfig.RaidAnimals)
			{
				if (illager.isRaidActive())
				{
					illager.targetSelector.addGoal(5, new NearestAttackableTargetGoal<>(illager, AnimalEntity.class, false));
				}
			}
		}
		if (event.getEntity() instanceof AbstractVillagerEntity)
		{
			AbstractVillagerEntity villager = (AbstractVillagerEntity)event.getEntity();
			if (GuardConfig.VillagersRunFromPolarBears)
			{
				villager.goalSelector.addGoal(2, new AvoidEntityGoal<>(villager, PolarBearEntity.class, 6.0F, 1.0D, 1.2D)); //common sense.
			}
		}
		if (event.getEntity() instanceof VillagerEntity)
		{
			VillagerEntity villager = (VillagerEntity) event.getEntity();
			villager.goalSelector.addGoal(1, new HealGolemGoal(villager));
			villager.goalSelector.addGoal(1, new HealGuardAndPlayerGoal(villager, 5.0D, 60, 0, 10.0F));
		}
		if (event.getEntity() instanceof IronGolemEntity)
		{
			IronGolemEntity golem = (IronGolemEntity) event.getEntity();
			golem.getAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(100.0D);
			golem.getAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(30.0D);
		}
		if (event.getEntity() instanceof ZombieEntity)
		{
			ZombieEntity zombie = (ZombieEntity) event.getEntity();
			zombie.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(zombie, GuardEntity.class, false));
		}
		if (event.getEntity() instanceof RavagerEntity)
		{
			RavagerEntity ravager = (RavagerEntity) event.getEntity();
			ravager.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(ravager, GuardEntity.class, false));
			if (GuardConfig.RaidAnimals)
			{
				if (ravager.isRaidActive())
				{
					ravager.targetSelector.addGoal(5, new NearestAttackableTargetGoal<>(ravager, AnimalEntity.class, false));
					}
				}
			}
		
		if (event.getEntity() instanceof WitchEntity)
		{
			WitchEntity witch = (WitchEntity) event.getEntity();
			if (GuardConfig.IllagersOpenDoors)
			{
				((GroundPathNavigator) witch.getNavigator()).setBreakDoors(true);
			}
			if (GuardConfig.WitchesVillager) {
				witch.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(witch, AbstractVillagerEntity.class, true));
				witch.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(witch, GuardEntity.class, false));
			}
			if (GuardConfig.RaidAnimals)
			{
				if (witch.isRaidActive()) {
					{
						witch.targetSelector.addGoal(5, new NearestAttackableTargetGoal<>(witch, AnimalEntity.class, false));
					}
				}
			}
		}
		if (event.getEntity() instanceof CatEntity)
		{
			CatEntity cat = (CatEntity) event.getEntity();
			cat.goalSelector.addGoal(1, new AvoidEntityGoal<>(cat, AbstractIllagerEntity.class, 12.0F, 1.0D, 1.2D));
		}
		
		if (event.getEntity() instanceof IllusionerEntity)
		{
			IllusionerEntity illusioner = (IllusionerEntity) event.getEntity();
			illusioner.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(illusioner, GuardEntity.class, false));
			if (GuardConfig.RaidAnimals)
			{
				if (illusioner.isRaidActive())
				{
					{
						illusioner.targetSelector.addGoal(5, new NearestAttackableTargetGoal<>(illusioner, AnimalEntity.class, false));
					}
				}
			}
		}
	}
	//TODO find a better way to implement adding more mobs into raids.
}
