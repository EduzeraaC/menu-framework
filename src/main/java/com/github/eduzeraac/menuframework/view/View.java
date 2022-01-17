package com.github.eduzeraac.menuframework.view;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;


@Getter
public class View extends VirtualView implements InventoryHolder {

    private final String title;
    private final int rows;
    private final Inventory inventory;

    public View(String title, int rows) {
        this.title = title;
        this.rows = rows * 9;
        this.inventory = Bukkit.createInventory(this, this.rows, title);
    }

    public void open(final Player player) {
        onOpen(new OpenView(player, this));
        render(inventory);
        player.openInventory(inventory);
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }

    public void onOpen(OpenView openView) {

    }

    public void onClick(SlotView slotView) {

    }

    @Override
    public String toString() {
        return "View{" +
          "title='" + title + '\'' +
          ", rows=" + rows +
          ", inventory=" + inventory +
          '}';
    }
}
