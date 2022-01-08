package com.github.eduzeraac.menuframework;

import com.github.eduzeraac.menuframework.listener.ListenerView;
import org.bukkit.plugin.java.JavaPlugin;

public class MenuPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        new ListenerView(this);
    }
}
