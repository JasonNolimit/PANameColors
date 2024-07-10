package com.of21.panamecolors;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PANameColors extends JavaPlugin {

    private static final List<ChatColor> ALLOWED_COLORS = Arrays.asList(
            ChatColor.DARK_GREEN, ChatColor.DARK_AQUA, ChatColor.DARK_RED, ChatColor.DARK_PURPLE,
            ChatColor.GOLD, ChatColor.GRAY, ChatColor.DARK_GRAY, ChatColor.BLUE,
            ChatColor.GREEN, ChatColor.AQUA, ChatColor.RED, ChatColor.LIGHT_PURPLE,
            ChatColor.YELLOW, ChatColor.WHITE
    );

    @Override
    public void onEnable() {
        getLogger().info("PANameColors plugin has been enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("PANameColors plugin has been disabled!");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("namecolor") || command.getName().equalsIgnoreCase("nc")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                if (args.length == 1) {
                    try {
                        ChatColor color = ChatColor.valueOf(args[0].toUpperCase());
                        if (!ALLOWED_COLORS.contains(color)) {
                            player.sendMessage(ChatColor.RED + "Invalid color! Use /namecolor to see available colors.");
                            return true;
                        }

                        if (player.hasPermission("namecolor.all") || player.hasPermission("namecolor." + color.name().toLowerCase())) {
                            player.setDisplayName(color + player.getName() + ChatColor.RESET);
                            player.sendMessage(ChatColor.GREEN + "Your name color has been changed to " + color + args[0].toUpperCase());
                        } else {
                            player.sendMessage(ChatColor.RED + "You don't have permission to use this color.");
                        }
                    } catch (IllegalArgumentException e) {
                        player.sendMessage(ChatColor.RED + "Invalid color! Use /namecolor to see available colors.");
                    }
                } else {
                    String colors = ALLOWED_COLORS.stream()
                            .map(color -> color + color.name())
                            .collect(Collectors.joining(", "));
                    player.sendMessage(ChatColor.GOLD + "Available colors: " + colors);
                }
                return true;
            } else {
                sender.sendMessage("Only players can use this command.");
                return true;
            }
        }
        return false;
    }
}
