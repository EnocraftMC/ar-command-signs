package net.bendercraft.spigot.commandsigns.menu;

import net.bendercraft.spigot.commandsigns.controller.EditingConfiguration;
import net.bendercraft.spigot.commandsigns.model.CommandBlock;
import net.bendercraft.spigot.commandsigns.utils.Messages;

public class DisableMenu extends EditionMenu
{
    public DisableMenu(EditionMenu parent)
    {
        super(parent, Messages.get("menu.disabled"));
    }

    @Override
    public void display(EditingConfiguration<CommandBlock> config)
    {
        String msg = Messages.get("menu.disabled_edit");
        config.getEditor().sendMessage(msg);
    }

    @Override
    public void input(EditingConfiguration<CommandBlock> config, String message)
    {
        try
        {
            String[] args = message.split(" ");
            String val = args[0].toLowerCase();
            if (val.equals("yes") || val.equals("true"))
            {
                config.getEditingData().setDisabled(true);
            }
            else
            {
                config.getEditingData().setDisabled(false);
            }
        }
        catch (Exception ignored)
        {
        }
        config.setCurrentMenu(getParent());
    }

    @Override
    public String formatName(CommandBlock cmdB) {
        String msg = getName();
        msg = msg.replace("{VALUE}", String.valueOf(cmdB.isDisabled()));
        return msg;
    }
}
