package com.github.eduzeraac.menuframework.view;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class VirtualView {

    private final List<ItemView> content;

    public VirtualView() {
        this.content = new ArrayList<>();
    }

    public ItemView getItem(int slot) {
        for (ItemView itemView : content) {
            final Integer itemViewSlot = itemView.getSlot();
            if (itemViewSlot == null) continue;
            if (itemViewSlot == slot) return itemView;
        }
        return null;
    }

    public ItemView item(ItemStack itemStack) {
        return new ItemView().withItem(itemStack);
    }

    public ItemView item(ItemStack itemStack, int slot) {
        return item(itemStack).withSlot(slot);
    }

    public ItemView withItem(ItemStack itemStack) {
        final ItemView itemView = item(itemStack);
        addItem(itemView);
        return itemView;
    }

    public ItemView withItem(int slot, ItemStack itemStack) {
        return withItem(itemStack).withSlot(slot);
    }

    public void addItem(ItemView itemView) {
        content.add(itemView);
    }

    public void render(ItemView itemView, int slot, Inventory inventory) {
        inventory.setItem(slot, itemView.getItemStack());

    }

    public void render(ItemView itemView, Inventory inventory) {
        if (itemView == null) return;
        render(itemView, itemView.getSlot(), inventory);
    }

    public void render(Inventory inventory) {
        for (ItemView itemView : content) {
            if (itemView == null || itemView.getSlot() == null) continue;

            render(itemView, inventory);
        }
    }

    @Override
    public String toString() {
        return "VirtualView{" +
          "content=" + content +
          '}';
    }
}
