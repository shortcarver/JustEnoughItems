package mezz.jei.config;

import com.google.common.base.Preconditions;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;

import javax.annotation.Nullable;

public final class ServerConfig implements IServerConfig {
	@Nullable
	private static IServerConfig instance;

	// Forge config
	private final ForgeConfigSpec.BooleanValue enableCheatModeForOp;
	private final ForgeConfigSpec.BooleanValue enableCheatModeForCreative;
	private final ForgeConfigSpec.BooleanValue enableCheatModeForGive;
	private final ForgeConfigSpec.BooleanValue enableRecipeTransfer;

	public static void register(IEventBus modEventBus, ModLoadingContext modLoadingContext) {
		modEventBus.register(ServerConfig.class);
		ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
		instance = new ServerConfig(builder);
		ForgeConfigSpec config = builder.build();
		modLoadingContext.registerConfig(ModConfig.Type.SERVER, config);
	}

	public ServerConfig(ForgeConfigSpec.Builder builder) {
		ServerConfigValues defaultValues = new ServerConfigValues();

		builder.push("cheat mode");
		{
			builder.comment("Enable Cheat Mode for Operators (/op)");
			enableCheatModeForOp = builder.define("enableCheatModeForOp", defaultValues.enableCheatModeForOp);

			builder.comment("Enable Cheat Mode for users in Creative Mode");
			enableCheatModeForCreative = builder.define("enableCheatModeForCreative", defaultValues.enableCheatModeForCreative);

			builder.comment("Enable Cheat Mode for users who can use /give");
			enableCheatModeForGive = builder.define("enableCheatModeForGive", defaultValues.enableCheatModeForGive);
		}
		builder.pop();

		builder.push("helpers");
		{
			builder.comment("Enable Recipe Transfer");
			enableRecipeTransfer = builder.define("enableRecipeTransfer", defaultValues.enableRecipeTransfer);
		}
		builder.pop();
	}

	public static IServerConfig getInstance() {
		Preconditions.checkNotNull(instance);
		return instance;
	}

	@Override
	public boolean isCheatModeEnabledForOp() {
		return enableCheatModeForOp.get();
	}

	@Override
	public boolean isCheatModeEnabledForCreative() {
		return enableCheatModeForCreative.get();
	}

	@Override
	public boolean isCheatModeEnabledForGive() {
		return enableCheatModeForGive.get();
	}

	@Override
	public boolean isRecipeTransferEnabled() {
		return enableRecipeTransfer.get();
	}
}