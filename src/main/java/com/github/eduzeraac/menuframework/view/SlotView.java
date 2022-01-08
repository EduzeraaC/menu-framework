package com.github.eduzeraac.menuframework.view;

import lombok.Data;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

@Data
public class SlotView {

    private final Player player;
    private final InventoryClickEvent origin;
    private final ItemView itemView;
}
