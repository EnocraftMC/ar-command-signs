package net.avatar.realms.spigot.commandsign.menu;

import org.bukkit.ChatColor;

import net.avatar.realms.spigot.commandsign.model.CommandBlock;
import net.avatar.realms.spigot.commandsign.model.EditingConf;

public class TemporaryPermissionsRemoveMenu extends EditionMenu {
	
	public TemporaryPermissionsRemoveMenu(EditionMenu parent) {
		super(parent, "Remove");
	}

	@Override
	public void display(EditingConf<CommandBlock> config) {
		config.getEditor().sendMessage(c + "Temporary permissions : ");
		int cpt = 1;
		for (String perm : config.getEditingData().getPermissions()) {
			config.getEditor().sendMessage(ChatColor.GRAY + "---" + cpt++ + ". " + perm);
		}
		config.getEditor().sendMessage(c + "Enter the index of the permission you want to remove : ");
	}
	
	@Override
	public void input(EditingConf<CommandBlock> config, String message) {
		try {
			config.setCurrentMenu(getParent());
			String[] args = message.split(" ", 2);
			int index = Integer.parseInt(args[0]);
			config.getEditingData().removePermission(index - 1);
		}
		catch (Exception e) {
		}
	}
	
}
