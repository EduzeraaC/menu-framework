package com.github.eduzeraac.menuframework.paginate;

import com.github.eduzeraac.menuframework.view.*;
import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

@Getter
public class PaginatedView extends View {

    private final int[] slots;
    private final int size;
    private List<List<ItemView>> pages;
    @Setter
    private int currentPage;
    private ItemView backItem;
    private ItemView nextItem;

    public PaginatedView(String title, int rows, int size, int... slots) {
        super(title, rows);
        this.size = size;
        this.slots = slots;
        this.currentPage = 0;
    }

    public ItemView setBackItem(int slot, ItemStack itemStack, ItemViewHandler itemViewHandler) {
        this.backItem = item(itemStack, slot).onClick(itemViewHandler);
        return backItem;
    }

    public ItemView setBackItem(int slot, ItemStack itemStack) {
        setBackItem(slot, itemStack, this::openBackPage);
        return backItem;
    }

    public ItemView setNextItem(int slot, ItemStack itemStack, ItemViewHandler itemViewHandler) {
        this.nextItem = item(itemStack, slot).onClick(itemViewHandler);
        return nextItem;
    }

    public ItemView setNextItem(int slot, ItemStack itemStack) {
        setNextItem(slot, itemStack, this::openNextPage);
        return nextItem;
    }

    private void resolve(Inventory inventory) {
        final List<ItemView> items = pages.get(currentPage);

        for (int index = 0; index < size; index++) {
            final int slot = slots[index];
            final ItemView oldItem = getItem(slot);
            if (oldItem != null) oldItem.withSlot(null);

            if (index >= items.size()) continue;
            final ItemView newItem = items.get(index);
            if (newItem.equals(nextItem) || newItem.equals(backItem)) continue;

            newItem.withSlot(slot);
            render(newItem, slot, inventory);
        }
        render(nextItem, inventory);
        render(backItem, inventory);
    }

    public void switchPage(Inventory inventory, Player player) {
        inventory.clear();
        onSwitch(new SwitchPaginateView(this, player));
        resolve(inventory);
        player.openInventory(inventory);
    }

    @Override
    public void open(Player player) {
        final Inventory inventory = getInventory();
        this.pages = Lists.partition(getContent(), size);

        addItem(nextItem);
        addItem(backItem);
        resolve(inventory);
        player.openInventory(inventory);
        onOpen(new OpenView(player, this));
    }

    public void openNextPage(SlotView slotView) {
        currentPage = currentPage
          >= pages.size() - 1
          ? 0
          : currentPage + 1;
        switchPage(slotView.getOrigin().getInventory(), slotView.getPlayer());
    }

    public void openBackPage(SlotView slotView) {
        currentPage = currentPage < 1 ? 0 : currentPage - 1;
        switchPage(slotView.getOrigin().getInventory(), slotView.getPlayer());
    }

    public void onSwitch(SwitchPaginateView switchPaginateView) {

    }

    @Override
    public String toString() {
        return "PaginatedView{" +
          "slots=" + Arrays.toString(slots) +
          ", size=" + size +
          ", pages=" + pages +
          ", currentPage=" + currentPage +
          ", backItem=" + backItem +
          ", nextItem=" + nextItem +
          '}';
    }
}
