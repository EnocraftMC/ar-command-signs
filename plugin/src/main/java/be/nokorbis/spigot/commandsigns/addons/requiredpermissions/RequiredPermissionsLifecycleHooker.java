package be.nokorbis.spigot.commandsigns.addons.requiredpermissions;

import be.nokorbis.spigot.commandsigns.addons.requiredpermissions.data.RequiredPermissionsConfigurationData;
import be.nokorbis.spigot.commandsigns.api.addons.*;
import be.nokorbis.spigot.commandsigns.api.exceptions.CommandSignsRequirementException;
import be.nokorbis.spigot.commandsigns.utils.Messages;
import org.bukkit.entity.Player;

import java.util.List;


public class RequiredPermissionsLifecycleHooker extends AddonLifecycleHookerBase {

	public RequiredPermissionsLifecycleHooker(RequiredPermissionsAddon addon) {
		super(addon);
	}

	@Override
	@NCSLifecycleHook
	public void onRequirementCheck(final Player player, final AddonConfigurationData configurationData, final AddonExecutionData executionData) throws CommandSignsRequirementException {
		if (player != null) {
			final RequiredPermissionsConfigurationData configuration = (RequiredPermissionsConfigurationData) configurationData;
			final List<String> requiredPermissions = configuration.getRequiredPermissions();

			for (String permission : requiredPermissions) {
				if (!player.hasPermission(permission)) {
					String err = Messages.get("usage.miss_needed_permission");
					err = err.replace("{NEEDED_PERM}", permission);
					throw new CommandSignsRequirementException(err);
				}
			}
		}
	}

}