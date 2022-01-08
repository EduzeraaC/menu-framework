package com.github.eduzeraac.menuframework.view;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;

@RequiredArgsConstructor
@Getter
public class OpenView {

    private final Player player;
    private final View view;
}
