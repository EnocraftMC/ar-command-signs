package net.bendercraft.spigot.commandsigns.tasks;

import net.bendercraft.spigot.commandsigns.controller.CommandBlockExecutor;
import net.bendercraft.spigot.commandsigns.controller.Container;
import net.bendercraft.spigot.commandsigns.model.CommandBlock;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class ExecuteTask implements Runnable{

	private CommandBlockExecutor executor;
	private int taskId;
	private Location location;

	public ExecuteTask(CommandBlockExecutor cmd) {
		this.executor = cmd;
	}

	@Override
	public void run () {
		if ((this.executor.getPlayer() == null) || !this.executor.getPlayer().isOnline() || this.executor.getPlayer().isDead()) {
			Container.getContainer().getExecutingTasks().remove(this.executor.getPlayer().getUniqueId());
			return;
		}
		this.executor.execute();
		Container.getContainer().getExecutingTasks().remove(this.executor.getPlayer().getUniqueId());
	}

	public CommandBlock getCommandBlock() {
		return this.executor.getCommandBlock();
	}

	public int getTaskId () {
		return this.taskId;
	}

	public void setTaskId (int taskId) {
		this.taskId = taskId;
	}

	public Location getLocation () {
		return this.location;
	}

	public void setLocation (Location location) {
		this.location = location;
	}

	public Player getPlayer() {
		return this.executor.getPlayer();
	}
}