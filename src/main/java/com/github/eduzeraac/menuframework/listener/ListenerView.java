package com.github.eduzeraac.menuframework.listener;

import com.github.eduzeraac.menuframework.MenuPlugin;
import com.github.eduzeraac.menuframework.view.ItemView;
import com.github.eduzeraac.menuframework.view.SlotView;
import com.github.eduzeraac.menuframework.view.View;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class ListenerView implements Listener {

    public ListenerView(MenuPlugin plugin) {
        Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    private void onClick(InventoryClickEvent event) {
        if (!(event.getInventory().getHolder() instanceof View) || event.getCurrentItem() == null) return;

        final View holder = (View) event.getInventory().getHolder();
        final ItemView itemView = holder.getItem(event.getSlot());
        if (itemView == null) return;

        if (itemView.isCancelClick()) {
            event.setCancelled(true);
        }
        holder.onClick(new SlotView(((Player) event.getWhoClicked()), event, itemView));
    }
}
