package com.github.eduzeraac.menuframework.paginate;

import com.github.eduzeraac.menuframework.view.ItemView;
import com.github.eduzeraac.menuframework.view.OpenView;
import com.github.eduzeraac.menuframework.view.SlotView;
import com.github.eduzeraac.menuframework.view.View;
import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

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

    public ItemView backItem(int slot, ItemStack itemStack) {
        this.backItem = item(itemStack, slot).onClick(this::openBackPage);
        return backItem;
    }

    public ItemView nextItem(int slot, ItemStack itemStack) {
        this.nextItem = item(itemStack, slot).onClick(this::openNextPage);
        return nextItem;
    }

    public void renderNavigation(Inventory inventory) {
        render(nextItem, inventory);
        render(backItem, inventory);
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
        renderNavigation(inventory);
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
}
