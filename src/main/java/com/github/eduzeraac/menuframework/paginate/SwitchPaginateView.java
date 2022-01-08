package com.github.eduzeraac.menuframework.paginate;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;

@RequiredArgsConstructor
@Getter
public class SwitchPaginateView {

    private final PaginateView paginateView;
    private final Player player;
}
