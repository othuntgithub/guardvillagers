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
	
	public static void bakeConfig() {
		GuardModel = COMMON.GuardModel.get();
		RaidAnimals = COMMON.RaidAnimals.get();
		WitchesVillager = COMMON.WitchesVillager.get();
		IllusionerRaids = COMMON.IllusionerRaids.get();
		AttackAllMobs = COMMON.AttackAllMobs.get();
		GuardSurrender = COMMON.GuardSurrender.get();
		GuardHealthRegen = COMMON.GuardHealthRegen.get();
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
					.comment("Illusioners in Raids?")
					.translation(GuardVillagers.MODID + ".config.IllusionerRaids")
					.define("Illusioners in Raids?", true);
			AttackAllMobs = builder
					.comment("Guards Attack All Mobs?")
					.translation(GuardVillagers.MODID + ".config.AttackAllMobs")
					.define("Guards Attack All Mobs?", false);
			GuardSurrender = builder
					.comment("This option makes guards run from ravagers when low on health")
					.translation(GuardVillagers.MODID + ".config.GuardSurrender")
					.define("Make Guards Run From Ravagers When Low On Health?", true);
			GuardHealthRegen = builder
					.comment("This makes the guard villagers have a slow natural health regeneration.")
					.translation(GuardVillagers.MODID + ".config.GuardHealthRegen")
					.define("Have Natural Health Regen For The Guards? ", false);
		}
	}
}