package com.github.eduzeraac.menuframework.paginate;

import com.github.eduzeraac.menuframework.view.ItemView;
import com.github.eduzeraac.menuframework.view.OpenView;
import com.github.eduzeraac.menuframework.view.SlotView;
import com.github.eduzeraac.menuframework.view.View;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;

@Getter
public class PaginateView extends View {

    private final int[] slots;
    private final int size;
    private List<List<ItemView>> pages;
    @Setter
    private int currentPage;
    private ItemView backItem;
    private ItemView nextItem;

    public PaginateView(String title, int rows, int size, int... slots) {
        super(title, rows);
        this.size = size;
        this.slots = slots;
        this.currentPage = 0;
    }

    public ItemView backItem(int slot, ItemStack itemStack) {
        this.backItem = item(itemStack, slot).paginateItem(true);
        return backItem;
    }

    public ItemView nextItem(int slot, ItemStack itemStack) {
        this.nextItem = item(itemStack, slot).paginateItem(true);
        return nextItem;
    }

    public void renderNavigation(Inventory inventory) {
        render(nextItem, inventory);
        render(backItem, inventory);
    }

    private void resolve(Inventory inventory) {
        final List<ItemView> items = pages.get(currentPage);
        for (int index = 0; index < items.size(); index++) {

            final ItemView itemView = items.get(index);
            final int slot = slots[index];
            itemView.withSlot(slot);

            render(itemView, slot, inventory);
        }
        renderNavigation(inventory);
    }

    public void switchPage(Inventory inventory, Player player) {
        inventory.clear();
        resolve(inventory);
        onSwitch(new SwitchPaginateView(this, player));
        player.openInventory(inventory);
    }

    @Override
    public void open(Player player) {
        final Inventory inventory = getInventory();
        this.pages = Lists.partition(ImmutableList.copyOf(getContent()), size);
        addItem(nextItem);
        addItem(backItem);
        resolve(inventory);

        player.openInventory(inventory);
        onOpen(new OpenView(player, this));
    }

    public void onSwitch(SwitchPaginateView switchPaginateView) {

    }

    @Override
    public void onClick(SlotView slotView) {
        final ItemView itemView = slotView.getItemView();
        if (itemView.isPaginateItem()) {
            final Player player = slotView.getPlayer();
            final Inventory inventory = slotView.getOrigin().getInventory();
            if (itemView.equals(nextItem)) {
                onNext();
                switchPage(inventory, player);
                return;
            }
            if (itemView.equals(backItem)) {
                onBack();
                switchPage(inventory, player);
            }
        }
    }

    public void onNext() {
        currentPage = currentPage
          >= pages.size() - 1
          ? 0
          : currentPage + 1;
    }

    public void onBack() {
        currentPage = currentPage < 1 ? 0 : currentPage - 1;
    }
}
