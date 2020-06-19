package tallestegg.guardvillagers.configuration;

import org.apache.commons.lang3.tuple.Pair;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.config.ModConfig;
import tallestegg.guardvillagers.GuardVillagers;

@EventBusSubscriber(modid = GuardVillagers.MODID, bus = EventBusSubscriber.Bus.MOD)
public class GuardConfig 
{
	public static final ForgeConfigSpec COMMON_SPEC;
	public static final CommonConfig COMMON;
	static {
		final Pair<CommonConfig, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(CommonConfig::new);
		COMMON_SPEC = specPair.getRight();
		COMMON = specPair.getLeft();
	}
	
	public static boolean GuardModel;
	public static boolean RaidAnimals;
	public static boolean WitchesVillager;
	public static boolean IllusionerRaids;
	public static boolean AttackAllMobs;
	public static boolean GuardSurrender;
	public static boolean GuardHealthRegen;
	public static boolean VillagersRunFromPolarBears;
	public static boolean IllagersRunFromPolarBears;
	public static boolean GuardsRunFromPolarBears;
	public static boolean IllagersOpenDoors;
	public static boolean GuardsOpenDoors;
	public static boolean GuardAlwaysShield;
	public static boolean GuardFormation;
	public static boolean FriendlyFire;
	
	public static void bakeConfig() {
		GuardModel = COMMON.GuardModel.get();
		RaidAnimals = COMMON.RaidAnimals.get();
		WitchesVillager = COMMON.WitchesVillager.get();
		IllusionerRaids = COMMON.IllusionerRaids.get();
		AttackAllMobs = COMMON.AttackAllMobs.get();
		GuardSurrender = COMMON.GuardSurrender.get();
		GuardHealthRegen = COMMON.GuardHealthRegen.get();
		VillagersRunFromPolarBears = COMMON.VillagersRunFromPolarBears.get();
		IllagersRunFromPolarBears = COMMON.IllagersRunFromPolarBears.get();
		GuardsOpenDoors = COMMON.GuardsOpenDoors.get();
		GuardAlwaysShield = COMMON.GuardRaiseShield.get();
		GuardFormation = COMMON.GuardFormation.get();
		FriendlyFire = COMMON.FriendlyFire.get();
	}

	@SubscribeEvent
	public static void onModConfigEvent(final ModConfig.ModConfigEvent configEvent) {
		if (configEvent.getConfig().getSpec() == GuardConfig.COMMON_SPEC) {
			bakeConfig();
		}
	}
	
	public static class CommonConfig 
	{

		public final ForgeConfigSpec.BooleanValue GuardModel;
		public final ForgeConfigSpec.BooleanValue RaidAnimals;
		public final ForgeConfigSpec.BooleanValue WitchesVillager;
		public final ForgeConfigSpec.BooleanValue IllusionerRaids;
		public final ForgeConfigSpec.BooleanValue AttackAllMobs;
		public final ForgeConfigSpec.BooleanValue GuardSurrender;
		public final ForgeConfigSpec.BooleanValue GuardHealthRegen;
		public final ForgeConfigSpec.BooleanValue VillagersRunFromPolarBears;
		public final ForgeConfigSpec.BooleanValue IllagersRunFromPolarBears;
		public final ForgeConfigSpec.BooleanValue GuardsRunFromPolarBears;
		public final ForgeConfigSpec.BooleanValue GuardsOpenDoors;
		public final ForgeConfigSpec.BooleanValue GuardRaiseShield;
		public final ForgeConfigSpec.BooleanValue GuardFormation;
		public final ForgeConfigSpec.BooleanValue FriendlyFire;

		public CommonConfig(ForgeConfigSpec.Builder builder) 
		{
			GuardModel = builder
					.comment("Switch Guard Model To Vanilla Style?, (textures not completed yet)")
					.translation(GuardVillagers.MODID + ".config.GuardModel")
					.define("Vanilla Styled Model?", false);
			
			RaidAnimals = builder
					.comment("Illagers In Raids Attack Animals?")
					.translation(GuardVillagers.MODID + ".config.RaidAnimals")
					.define("Illagers In Raids Attack Animals?", false);
			
			WitchesVillager = builder
					.comment("Witches Attack Villagers?")
					.translation(GuardVillagers.MODID + ".config.WitchesVillager")
					.define("Witches Attack Villagers?", true);
			IllusionerRaids = builder
					.comment("This will make Illusioners get involved in raids")
					.translation(GuardVillagers.MODID + ".config.IllusionerRaids")
					.define("Have Illusioners in Raids?", false);
			AttackAllMobs = builder
					.comment("Guards will attack all hostiles with this option")
					.translation(GuardVillagers.MODID + ".config.AttackAllMobs")
					.define("Guards Attack All Mobs?", false);
			GuardSurrender = builder
					.comment("This option makes guards run from ravagers when low on health")
					.translation(GuardVillagers.MODID + ".config.GuardSurrender")
					.define("Have Guards be run from ravagers when on low health?", true);
			GuardHealthRegen = builder
					.comment("This makes the guard villagers have a slow natural health regeneration.")
					.translation(GuardVillagers.MODID + ".config.GuardHealthRegen")
					.define("Make Guards regenerate health?", false);
			VillagersRunFromPolarBears = builder
					.comment("This makes villagers run from polar bears, as anyone with common sense would.")
					.translation(GuardVillagers.MODID + ".config.VillagersRunFromPolarBears")
					.define("Have Villagers have some common sense?", true);
			IllagersRunFromPolarBears = builder
					.comment("This makes Illagers run from polar bears, as anyone with common sense would.")
					.translation(GuardVillagers.MODID + ".config.IllagersRunFromPolarBears")
					.define("Have Illagers have some common sense?", true);
			GuardsRunFromPolarBears = builder
					.comment("This makes Guards run from polar bears, as anyone with common sense would.")
					.translation(GuardVillagers.MODID + ".config.IllagersRunFromPolarBears")
					.define("Have Guards have some common sense?", false);
			GuardsOpenDoors = builder
					.comment("This lets Guards open doors.")
					.translation(GuardVillagers.MODID + ".config.GuardsOpenDoors")
					.define("Have Guards open doors?", true);
			GuardRaiseShield = builder
					.comment("This will make guards raise their shields all the time, on default they will only raise their shields under certain conditions")
					.translation(GuardVillagers.MODID + ".config.GuardRaiseShield")
					.define("Have Guards raise their shield all the time?", false);
			GuardFormation = builder
					.comment("This makes guards form a phalanx")
					.translation(GuardVillagers.MODID + ".config.GuardFormation")
					.define("Have guards form a phalanx?", true);
			FriendlyFire = builder
					.comment("This will make guards attempt to avoid friendly fire.")
					.translation(GuardVillagers.MODID + ".config.FriendlyFire")
					.define("Have guards avoid friendly fire? This is experimental.", true);
		}
	}
}