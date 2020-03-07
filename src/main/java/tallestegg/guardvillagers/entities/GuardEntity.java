package tallestegg.guardvillagers.entities;

import javax.annotation.Nullable;

import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
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
import net.minecraft.entity.monster.VexEntity;
import net.minecraft.entity.monster.WitchEntity;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.SwordItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.Category;
import tallestegg.guardvillagers.configuration.GuardConfig;

public class GuardEntity extends CreatureEntity
{ 
	private static final DataParameter<Integer> GUARD_VARIANT = EntityDataManager.createKey(GuardEntity.class, DataSerializers.VARINT);
	 
	public GuardEntity(EntityType<? extends GuardEntity> type, World world)
	{
		super(type, world);
		enablePersistence();
	}

	@Override
	public ILivingEntityData onInitialSpawn(IWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) 
	{
	  int type = GuardEntity.getRandomTypeForBiome(world, this.getPosition());
	  if (spawnDataIn instanceof GuardEntity.GuardData) 
	  {
        type = ((GuardEntity.GuardData)spawnDataIn).variantData;
	    spawnDataIn = new GuardEntity.GuardData(type);
	  }
	   this.setGuardVariant(type);
	   this.setEquipmentBasedOnDifficulty(difficultyIn);
	   
	   return super.onInitialSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
	}
	
	   protected SoundEvent getAmbientSound() 
	   {
	     return SoundEvents.ENTITY_VILLAGER_AMBIENT;
	   }

	   protected SoundEvent getHurtSound(DamageSource damageSourceIn) 
	   {
	      return SoundEvents.ENTITY_VILLAGER_HURT;
	   }

	   protected SoundEvent getDeathSound() 
	   {
	      return SoundEvents.ENTITY_VILLAGER_DEATH;
	   }
	   
	@Override
	public void readAdditional(CompoundNBT compound) 
	{
		super.readAdditional(compound);
		this.setGuardVariant(compound.getInt("Type"));
	}
	
	
	public void livingTick() 
	{
	    this.updateArmSwingProgress();
	    super.livingTick();
	}
	
	@Override
	protected void registerData() 
	{
		super.registerData();
		this.dataManager.register(GUARD_VARIANT, 0);
	}
	
	protected void setEquipmentBasedOnDifficulty(DifficultyInstance difficulty) 
	{
	      this.setItemStackToSlot(EquipmentSlotType.MAINHAND, new ItemStack(Items.IRON_SWORD));
	      this.inventoryHandsDropChances[EquipmentSlotType.MAINHAND.getIndex()] = 50.0F;
	}
	
	public int getGuardVariant()
    {
		return this.dataManager.get(GUARD_VARIANT);
	}
	
	public void setGuardVariant(int typeId)
	{
		this.dataManager.set(GUARD_VARIANT, typeId);
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
	      this.goalSelector.addGoal(1, new OpenDoorGoal(this, true));
	      this.goalSelector.addGoal(1, new AvoidEntityGoal<>(this, RavagerEntity.class, 12.0F, 0.5D, 0.5D));
	      this.goalSelector.addGoal(10, new LookAtGoal(this, MobEntity.class, 8.0F));
	      this.goalSelector.addGoal(10, new LookAtGoal(this, PlayerEntity.class, 8.0F));
	      this.targetSelector.addGoal(2, new HurtByTargetGoal(this));
	      this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, ZombieEntity.class, true));
	      this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractIllagerEntity.class, true));
	      this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, WitchEntity.class, true));
	      this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IllusionerEntity.class, true));
	      this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, VexEntity.class, true));
	     if (GuardConfig.AttackAllMobs == true) 
	      {
	        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, MonsterEntity.class, true));
	        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, GhastEntity.class, true));
	      }
	 }
	
	
	
	@Override
	public void writeAdditional(CompoundNBT compound) 
	{
		super.writeAdditional(compound);
		compound.putInt("Type", this.getGuardVariant());
	}
	
	public static int getRandomTypeForBiome(IWorld world, BlockPos pos) {
		    Biome biome = world.getBiome(pos);
			if(biome.getCategory() == Category.PLAINS) 
			{
					return 0;
			}
			
			if (biome.getCategory() == Category.DESERT) 
			{
					return 1;
			}
			
			if (biome.getCategory() == Category.SAVANNA) 
			{
					return 2;
			}
			
			if (biome.getCategory() == Category.SWAMP) 
			{
					return 3;
			}
			
			if (biome.getCategory() == Category.JUNGLE) 
			{
					return 4;
			}
			
			if (biome.getCategory() == Category.TAIGA) 
			{
					return 5;
			}
			
			if (biome.getCategory() == Category.ICY) 
			{
					return 6;
			}
			else 
			{
			  return 0;
			}
	}
	
	public boolean processInteract(PlayerEntity player, Hand hand)
	{
	        ItemStack heldStack = player.getHeldItem(hand);
	        if (heldStack.getItem() instanceof SwordItem && player.isShiftKeyDown())
	        {
	            this.setItemStackToSlot(EquipmentSlotType.MAINHAND, heldStack.copy());
	            if (!player.abilities.isCreativeMode)
	                heldStack.shrink(1);
	            }
			    return true;
	        }
	public static String getNameByType(int id) 
	{
		switch(id) 
		{
			case 0:
				return "plains";
			case 1:
				return "desert";
			case 2:
				return "savanna";
			case 3:
				return "swamp";
			case 4:
				return "jungle";
			case 5:
				return "taiga";
			case 6:
				return "snow";
		}
		return "";
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
	
	public static class GuardData implements ILivingEntityData 
	{
		public final int variantData;

		public GuardData(int type) 
		{
			this.variantData = type;
		}
	}



	/**
	 * Credit - SmellyModder for Biome Specific Textures
	 */
}