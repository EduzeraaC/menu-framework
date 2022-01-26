package com.github.eduzeraac.menuframework.listener;

import com.github.eduzeraac.menuframework.view.ItemView;
import com.github.eduzeraac.menuframework.view.ItemViewHandler;
import com.github.eduzeraac.menuframework.view.SlotView;
import com.github.eduzeraac.menuframework.view.View;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.plugin.Plugin;

public class ListenerView implements Listener {

    public ListenerView(Plugin plugin) {
        Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (!(event.getInventory().getHolder() instanceof View)) return;
        event.setCancelled(true);

        final View holder = (View) event.getInventory().getHolder();
        final ItemView itemView = holder.getItem(event.getSlot());
        if (itemView == null) return;
        final SlotView slotView = new SlotView(((Player) event.getWhoClicked()), event, itemView);

        final ItemViewHandler clickHandler = itemView.getClickHandler();
        if (clickHandler != null) clickHandler.handle(slotView);

        holder.onClick(slotView);
    }
}
