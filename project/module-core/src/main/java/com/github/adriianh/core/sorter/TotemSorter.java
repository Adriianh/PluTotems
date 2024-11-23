package com.github.adriianh.core.sorter;

import com.github.adriianh.common.totem.Totem;
import com.github.adriianh.core.common.EnumFilter;
import lombok.Getter;

import java.util.Comparator;

@Getter
public enum TotemSorter implements EnumFilter<TotemSorter> {
    NAME_ASCENDING("Name (A-Z)", Comparator.comparing(Totem::getId)),
    NAME_DESCENDING("Name (Z-A)", Comparator.comparing(Totem::getId).reversed());

    private final String name;
    private final Comparator<Totem> comparator;

    TotemSorter(String name, Comparator<Totem> comparator) {
        this.name = name;
        this.comparator = comparator;
    }

    public Comparator<Totem> getComparator() {
        return comparator;
    }
}