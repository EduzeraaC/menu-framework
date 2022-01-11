package com.github.eduzeraac.menuframework.paginate;

import lombok.Data;
import org.bukkit.entity.Player;

@Data
public class SwitchPaginateView {

    private final PaginatedView paginatedView;
    private final Player player;
}
