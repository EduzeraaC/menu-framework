package com.github.eduzeraac.menuframework.view;

import lombok.Data;
import org.bukkit.entity.Player;

@Data
public class OpenView {

    private final Player player;
    private final View view;

}
