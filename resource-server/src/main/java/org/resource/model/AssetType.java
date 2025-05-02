package org.resource.model;

import lombok.Getter;

@Getter
public enum AssetType {
    CRYPTOCURRENCY("Cryptocurrency"),
    COMMODITY("Commodity"),
    REAL_ESTATE("Real Estate");

    private final String displayName;

    AssetType(String displayName) {
        this.displayName = displayName;
    }

}
