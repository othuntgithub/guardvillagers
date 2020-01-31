package tallestegg.guardvillagers.entities;

import javax.annotation.Nullable;

import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.MoveTowardsTargetGoal;
import net.minecraft.entity.ai.goal.MoveTowardsVillageGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.OpenDoorGoal;
import net.minecraft.entity.ai.goal.RandomWalkingGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.monster.AbstractIllagerEntity;
import net.minecraft.entity.monster.GhastEntity;
import net.minecraft.entity.monster.IllusionerEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.monster.RavagerEntity;
import net.minecraft.entity.monster.WitchEntity;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.SwordItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Hand;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import tallestegg.guardvillagers.configuration.GuardConfig;

public class GuardEntity extends CreatureEntity
{

	public GuardEntity(EntityType<? extends GuardEntity> type, World world)
	{
		super(type, world);
	}

	@Override
	   public ILivingEntityData onInitialSpawn(IWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
		  this.setEquipmentBasedOnDifficulty(difficultyIn);
	      return super.onInitialSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
	}
	
	protected void setEquipmentBasedOnDifficulty(DifficultyInstance difficulty) 
	{
	      this.setItemStackToSlot(EquipmentSlotType.MAINHAND, new ItemStack(Items.STONE_SWORD));
	}
	
	protected void registerGoals() 
	{
	      this.goalSelector.addGoal(1, new SwimGoal(this));
	      this.goalSelector.addGoal(6, new LookRandomlyGoal(this));
	      this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.0D, true));
	      this.goalSelector.addGoal(2, new MoveTowardsTargetGoal(this, 0.9D, 32.0F));
	      this.goalSelector.addGoal(2, new MoveTowardsVillageGoal(this, 0.6D));
	      this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.0D, false));
	      this.goalSelector.addGoal(8, new RandomWalkingGoal(this, 0.6D));
	      this.goalSelector.addGoal(8, new OpenDoorGoal(this, true));
	      this.targetSelector.addGoal(2, new HurtByTargetGoal(this));
	      this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, ZombieEntity.class, true));
	      this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractIllagerEntity.class, true));
	      this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, RavagerEntity.class, true));
	      this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, WitchEntity.class, true));
	      this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IllusionerEntity.class, true));
	      if (GuardConfig.AttackAllMobs == true)
	      this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, MonsterEntity.class, true));
	      this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, GhastEntity.class, true));
	 }
	
	public boolean processInteract(PlayerEntity player, Hand hand)
	{
	    if (!this.world.isRemote) 
	    {
	        ItemStack heldStack = player.getHeldItem(hand);
	        if (heldStack.getItem() instanceof SwordItem)
	        {
	            this.setItemStackToSlot(EquipmentSlotType.MAINHAND, heldStack.copy());
	            if (!player.abilities.isCreativeMode)
	                heldStack.shrink(1);
	        }
	    }
	    return true;
	}

	 
	protected void registerAttributes() 
	{
	      super.registerAttributes();
	      this.getAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(50.0D);
	      this.getAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(5.0D);
	      this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20.0D);
	      this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.5D);
	      this.getAttributes().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(2.0D);
	}
}