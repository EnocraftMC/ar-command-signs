package be.nokorbis.spigot.commandsigns.controller;

import be.nokorbis.spigot.commandsigns.CommandSignsPlugin;
import be.nokorbis.spigot.commandsigns.api.addons.Addon;
import be.nokorbis.spigot.commandsigns.api.addons.AddonConfigurationData;
import be.nokorbis.spigot.commandsigns.api.addons.AddonExecutionData;
import be.nokorbis.spigot.commandsigns.api.addons.AddonLifecycleHooker;
import be.nokorbis.spigot.commandsigns.api.exceptions.CommandSignsException;
import be.nokorbis.spigot.commandsigns.api.exceptions.CommandSignsRequirementException;
import be.nokorbis.spigot.commandsigns.model.CommandBlock;
import be.nokorbis.spigot.commandsigns.tasks.ExecuteTask;
import be.nokorbis.spigot.commandsigns.utils.Messages;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;


public class NCommandBlockExecutor {

	private static NCommandSignsManager manager;

	private final Player player;
	private final CommandBlock commandBlock;

	public NCommandBlockExecutor(Player player, CommandBlock commandBlock) {
		this.player = player;
		this.commandBlock = commandBlock;
	}

	public final Player getPlayer() {
		return player;
	}

	public final CommandBlock getCommandBlock() {
		return commandBlock;
	}

	public void run() {
		long time = 0;
		if (commandBlock.hasTimer() && !player.hasPermission("commandsign.timer.bypass")) {
			time = commandBlock.getTimeBeforeExecution();
			String msg = Messages.get("info.timer_delayed");
			msg = msg.replace("{TIME}", String.valueOf(time));
			player.sendMessage(msg);
		}

		ExecuteTask exe = new ExecuteTask(this);
		exe.setInitialLocation(player.getLocation().getBlock().getLocation());
		Container.getContainer().getExecutingTasks().put(player.getUniqueId(), exe);

		BukkitScheduler scheduler = Bukkit.getServer().getScheduler();

		BukkitTask task = scheduler.runTaskLaterAsynchronously(CommandSignsPlugin.getPlugin(), exe, time * 20);
		exe.setTaskId(task.getTaskId());
	}

	public void execute() throws CommandSignsException {
		final NCommandSignsAddonLifecycleHolder lifecycleHolder = manager.getLifecycleHolder();

		try {
			processStart(lifecycleHolder);
			processRequirementsCheck(lifecycleHolder);
			processCostsWithdrawn(lifecycleHolder);
			processPreExecution(lifecycleHolder);
			processExecution(lifecycleHolder);
			processPostExecution(lifecycleHolder);
		}
		finally {
			processComplete(lifecycleHolder);
		}
	}

	private void processStart(final NCommandSignsAddonLifecycleHolder lifecycleHolder) {
		for (final Addon addon : lifecycleHolder.onStartHandlers) {
			final AddonLifecycleHooker hook = addon.getLifecycleHooker();
			final AddonConfigurationData configuration = commandBlock.getAddonConfigurationData(addon);
			final AddonExecutionData data = commandBlock.getAddonExecutionData(addon);

			hook.onStarted(player, configuration, data);
		}
	}

	private void processRequirementsCheck(final NCommandSignsAddonLifecycleHolder lifecycleHolder) throws CommandSignsRequirementException {
		for (final Addon addon : lifecycleHolder.onRequirementCheckHandlers) {
			final AddonLifecycleHooker hook = addon.getLifecycleHooker();
			final AddonConfigurationData configuration = commandBlock.getAddonConfigurationData(addon);
			final AddonExecutionData data = commandBlock.getAddonExecutionData(addon);

			hook.onRequirementCheck(player, configuration, data);
		}
	}


	private void processCostsWithdrawn(final NCommandSignsAddonLifecycleHolder lifecycleHolder) {
		for (final Addon addon : lifecycleHolder.onCostWithdrawHandlers) {
			final AddonLifecycleHooker hook = addon.getLifecycleHooker();
			final AddonConfigurationData configuration = commandBlock.getAddonConfigurationData(addon);
			final AddonExecutionData data = commandBlock.getAddonExecutionData(addon);

			hook.onCostWithdraw(player, configuration, data);
		}
	}

	private void processPreExecution(final NCommandSignsAddonLifecycleHolder lifecycleHolder) {
		for (final Addon addon : lifecycleHolder.onPreExecutionHandlers) {
			final AddonLifecycleHooker hook = addon.getLifecycleHooker();
			final AddonConfigurationData configuration = commandBlock.getAddonConfigurationData(addon);
			final AddonExecutionData data = commandBlock.getAddonExecutionData(addon);

			hook.onPreExecution(player, configuration, data);
		}
	}

	private void processExecution(final NCommandSignsAddonLifecycleHolder lifecycleHolder) {
		for (final Addon addon : lifecycleHolder.onExecutionHandlers) {
			final AddonLifecycleHooker hook = addon.getLifecycleHooker();
			final AddonConfigurationData configuration = commandBlock.getAddonConfigurationData(addon);
			final AddonExecutionData data = commandBlock.getAddonExecutionData(addon);

			hook.onExecution(player, configuration, data);
		}
	}

	private void processPostExecution(final NCommandSignsAddonLifecycleHolder lifecycleHolder) {
		for (final Addon addon : lifecycleHolder.onPostExecutionHandlers) {
			final AddonLifecycleHooker hook = addon.getLifecycleHooker();
			final AddonConfigurationData configuration = commandBlock.getAddonConfigurationData(addon);
			final AddonExecutionData data = commandBlock.getAddonExecutionData(addon);

			hook.onPostExecution(player, configuration, data);
		}
	}

	private void processComplete(final NCommandSignsAddonLifecycleHolder lifecycleHolder) {
		for (final Addon addon : lifecycleHolder.onCompletedHandlers) {
			final AddonLifecycleHooker hook = addon.getLifecycleHooker();
			final AddonConfigurationData configuration = commandBlock.getAddonConfigurationData(addon);
			final AddonExecutionData data = commandBlock.getAddonExecutionData(addon);

			hook.onCompleted(player, configuration, data);
		}
	}


	public static void setManager(final NCommandSignsManager manager) {
		NCommandBlockExecutor.manager = manager;
	}
}