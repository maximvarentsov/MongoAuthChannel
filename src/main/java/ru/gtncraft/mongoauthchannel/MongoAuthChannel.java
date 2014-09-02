package ru.gtncraft.mongoauthchannel;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.event.EventHandler;

import java.util.UUID;

public class MongoAuthChannel extends Plugin implements Listener {

    private final static String channel = "mongoauth";

    @Override
    public void onEnable() {
        getProxy().getPluginManager().registerListener(this, this);
        getProxy().registerChannel(channel);
    }

    @Override
    public void onDisable() {
        getProxy().unregisterChannel(channel);
    }

    @EventHandler
    @SuppressWarnings("unused")
    public void onPlayerDisconnect(final PlayerDisconnectEvent event) {
        ProxiedPlayer player = event.getPlayer();
        String server = player.getPendingConnection().getListener().getDefaultServer();
        UUID uuid = player.getUniqueId();
        byte[] message = uuid.toString().getBytes();
        getProxy().getServerInfo(server).sendData(channel, message);
    }
}
