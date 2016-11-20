package net.bendercraft.spigot.commandsigns.menu;

import net.bendercraft.spigot.commandsigns.utils.Messages;

import net.bendercraft.spigot.commandsigns.controller.EditingConfiguration;
import net.bendercraft.spigot.commandsigns.model.CommandBlock;

public class NeededPermissionsRemoveMenu extends EditionMenu {
	
	public NeededPermissionsRemoveMenu(EditionMenu parent) {
		super(parent, Messages.get("menu.remove"));
	}

	@Override
	public void display(EditingConfiguration<CommandBlock> config) {
		config.getEditor().sendMessage(Messages.get("info.needed_permissions"));
		int cpt = 1;
		String format = Messages.get("info.permission_format");
		String msg;
		for (String perm : config.getEditingData().getNeededPermissions()) {
			msg = format.replace("{NUMBER}", String.valueOf(cpt++)).replace("{PERMISSION}", perm);
			config.getEditor().sendMessage(msg);
		}
		config.getEditor().sendMessage(Messages.get("menu.remove_permission"));
		
	}
	
	@Override
	public void input(EditingConfiguration<CommandBlock> config, String message) {
		try {
			config.setCurrentMenu(getParent());
			String[] args = message.split(" ", 2);
			int index = Integer.parseInt(args[0]);
			config.getEditingData().removeNeededPermission(index - 1);
		}
		catch (Exception ignored) {
		}
	}
}
