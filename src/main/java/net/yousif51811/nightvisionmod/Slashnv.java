package net.yousif51811.nightvisionmod;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.resources.Identifier;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.server.level.ServerPlayer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Slashnv implements ModInitializer {
	public static final String MOD_ID = "slashnv";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	@Override
	public void onInitialize() {
		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
			dispatcher.register(
					net.minecraft.commands.Commands.literal("nv")
							.executes(context -> {
								ServerPlayer player = context.getSource().getPlayerOrException();
								giveNightVision(player);
								LOGGER.info("{} used /nv!", player.getName().getString());
								return 1;
							})
			);

		});

	}

	private static void giveNightVision(ServerPlayer player) {

		if (player.hasEffect(MobEffects.NIGHT_VISION)) {
			player.removeEffect(MobEffects.NIGHT_VISION);
			return;
		}

		MobEffectInstance nightVision = new MobEffectInstance(
				MobEffects.NIGHT_VISION,
				Integer.MAX_VALUE,
				255,
				false,
				false,
				true
		);
		player.addEffect(nightVision);
	}

	public static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}
}
