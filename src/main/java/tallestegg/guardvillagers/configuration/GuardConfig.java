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
	public static final ClientConfig CLIENT;
	public static final ForgeConfigSpec CLIENT_SPEC;
	static {
		final Pair<ClientConfig, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(ClientConfig::new);
		CLIENT_SPEC = specPair.getRight();
		CLIENT = specPair.getLeft();
	}
	
	public static boolean GuardModel;
	public static boolean RaidAnimals;
	public static boolean WitchesVillager;
	public static boolean IllusionerRaids;
	public static boolean AttackAllMobs;
	
	public static void bakeConfig() {
		GuardModel = CLIENT.GuardModel.get();
		RaidAnimals = CLIENT.RaidAnimals.get();
		WitchesVillager = CLIENT.WitchesVillager.get();
		IllusionerRaids = CLIENT.IllusionerRaids.get();
		AttackAllMobs = CLIENT.AttackAllMobs.get();
	}

	@SubscribeEvent
	public static void onModConfigEvent(final ModConfig.ModConfigEvent configEvent) {
		if (configEvent.getConfig().getSpec() == GuardConfig.CLIENT_SPEC) {
			bakeConfig();
		}
	}
	
	public static class ClientConfig 
	{

		public final ForgeConfigSpec.BooleanValue GuardModel;
		public final ForgeConfigSpec.BooleanValue RaidAnimals;
		public final ForgeConfigSpec.BooleanValue WitchesVillager;
		public final ForgeConfigSpec.BooleanValue IllusionerRaids;
		public final ForgeConfigSpec.BooleanValue AttackAllMobs;

		public ClientConfig(ForgeConfigSpec.Builder builder) 
		{
			GuardModel = builder
					.comment("Switch Guard Model To Vanilla Style?")
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
