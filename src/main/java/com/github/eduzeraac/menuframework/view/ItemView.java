package com.github.eduzeraac.menuframework.view;

import lombok.Getter;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

@Getter
public class ItemView {

    private boolean cancelClick;
    private boolean paginateItem;
    private Integer slot;
    private ItemStack itemStack;
    private final Map<String, Object> data;

    public ItemView() {
        this.data = new HashMap<>();
        this.cancelClick = true;
    }

    public ItemView withItem(ItemStack itemStack) {
        this.itemStack = itemStack;
        return this;
    }

    public ItemView withSlot(int slot) {
        this.slot = slot;
        return this;
    }

    public ItemView withData(String key, Object value) {
        data.put(key, value);
        return this;
    }

    public void setData(String key, Object value) {
        withData(key, value);
    }

    public void removeData(String key) {
        data.remove(key);
    }

    public Object getData(String key) {
        return data.get(key);
    }

    public ItemView cancelClick(boolean cancelClick) {
        this.cancelClick = cancelClick;
        return this;
    }

    public ItemView paginateItem(boolean paginateItem) {
        this.paginateItem = paginateItem;
        return this;
    }

    @Override
    public String toString() {
        return "ItemView{" +
          "cancelClick=" + cancelClick +
          ", paginateItem=" + paginateItem +
          ", slot=" + slot +
          ", itemStack=" + itemStack +
          ", data=" + data +
          '}';
    }
}