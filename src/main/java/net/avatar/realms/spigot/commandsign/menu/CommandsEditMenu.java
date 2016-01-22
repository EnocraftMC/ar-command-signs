package net.avatar.realms.spigot.commandsign.menu;

import net.avatar.realms.spigot.commandsign.utils.Messages;
import org.bukkit.ChatColor;

import net.avatar.realms.spigot.commandsign.controller.EditingConfiguration;
import net.avatar.realms.spigot.commandsign.model.CommandBlock;

public class CommandsEditMenu extends EditionMenu {

	public CommandsEditMenu(EditionMenu parent) {
		super(parent, Messages.get("menu.edit"));
	}

	@Override
	public void display(EditingConfiguration<CommandBlock> config) {
		config.getEditor().sendMessage(c + Messages.get("info.commands") + " : ");
		int cpt = 1;
		for (String cmd : config.getEditingData().getCommands()) {
			config.getEditor().sendMessage(ChatColor.GRAY + "---" + cpt++ + ". " + cmd);
		}
		config.getEditor().sendMessage(c + Messages.get("menu.edit_command"));
	}

	@Override
	public void input(EditingConfiguration<CommandBlock> config, String message) {
		try {
			config.setCurrentMenu(getParent());
			String[] args = message.split(" ", 2);
			int index = Integer.parseInt(args[0]);
			config.getEditingData().editCommand(index - 1, args[1]);
		}
		catch (Exception e) {
		}
	}

}
