package me.a8kj.lootbox.internal.command;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.a8kj.lootbox.parent.entity.lootbox.Lootbox;
import me.a8kj.lootbox.parent.entity.lootbox.LootboxFactory;
import me.a8kj.lootbox.parent.entity.lootbox.LootboxPreview;
import me.a8kj.lootbox.parent.plugin.LootboxFacade;
import me.a8kj.lootbox.parent.structure.Registry;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@RequiredArgsConstructor
@Getter
public class LootboxCommand implements CommandExecutor {

    private final LootboxFacade facade;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "This command can only be executed by a player.");
            return false;
        }

        Player player = (Player) sender;

        if (args.length == 0) {
            player.sendMessage(ChatColor.RED + "Usage: /lootbox <addlocation|create|remove>");
            return false;
        }

        switch (args[0].toLowerCase()) {
            case "addlocation":
                if (!player.hasPermission("lootbox.addlocation")) {
                    player.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
                    return false;
                }
                if (getFacade().getLocationManager().hasLocation(player.getLocation())) {
                    player.sendMessage(ChatColor.RED + "This location already exist!");
                    return false;
                }
                getFacade().getLocationManager().addLocation(player.getLocation());
                player.sendMessage(ChatColor.GREEN + "Lootbox location added successfully.");
                break;

            case "create":
                if (!player.hasPermission("lootbox.create")) {
                    player.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
                    return false;
                }
                if (args.length < 2) {
                    player.sendMessage(ChatColor.RED + "Usage: /lootbox create <name>");
                    return false;
                }
                String name = args[1].toLowerCase();
                Registry<Lootbox> lootboxRegistry = getFacade().getLootboxRegistry();
                Lootbox lootbox = lootboxRegistry.get(name);
                if (lootbox == null) {
                    player.sendMessage(ChatColor.RED + "Lootbox with the name '" + name + "' not found.");
                    return false;
                }
                LootboxPreview lootboxPreview = LootboxFactory.createPreviewLootbox(name, lootbox);
                lootboxPreview.spawn(player.getLocation());
                player.sendMessage(ChatColor.GREEN + "Lootbox preview created and spawned at your location.");
                break;

            case "remove":
                if (!player.hasPermission("lootbox.remove")) {
                    player.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
                    return false;
                }
                if (facade.getPlayersInRemoveMode().contains(player.getUniqueId())) {
                    facade.getPlayersInRemoveMode().remove(player.getUniqueId());
                    player.sendMessage(ChatColor.GOLD + "You have been left remove-mode");
                } else {
                    facade.getPlayersInRemoveMode().add(player.getUniqueId());
                    player.sendMessage(ChatColor.DARK_GREEN + "You have been entered remove-mode");
                }
                break;

            default:
                player.sendMessage(ChatColor.RED + "Invalid command. Usage: /lootbox <addlocation|create|remove>");
                break;
        }

        return true;
    }

}