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
	
	public static void bakeConfig() {
		GuardModel = COMMON.GuardModel.get();
		RaidAnimals = COMMON.RaidAnimals.get();
		WitchesVillager = COMMON.WitchesVillager.get();
		IllusionerRaids = COMMON.IllusionerRaids.get();
		AttackAllMobs = COMMON.AttackAllMobs.get();
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
		}
	}
}
