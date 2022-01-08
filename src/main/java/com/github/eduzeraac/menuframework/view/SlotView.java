package com.github.eduzeraac.menuframework.view;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

@RequiredArgsConstructor
@Getter
public class SlotView {

    private final Player player;
    private final InventoryClickEvent origin;
    private final ItemView itemView;
}
