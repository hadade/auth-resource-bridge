package org.resource.api.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.resource.model.Asset;
import org.resource.model.AssetType;

@Data
@AllArgsConstructor
public class AssetDto {
    private Long id;
    private String name;
    private String symbol;
    private String description;
    private String type;
    private double price;
    private String owner;

    public AssetDto() {
    }

    public AssetDto(Asset asset) {
        this.id = asset.getId();
        this.name = asset.getName();
        this.symbol = asset.getSymbol();
        this.description = asset.getDescription();
        this.type = asset.getType().getDisplayName();
        this.price = asset.getPrice();
        this.owner = asset.getOwner() != null ? asset.getOwner().getUsername() : null;
    }

    public static Asset toAsset(AssetDto assetDto) {
        Asset asset = new Asset();
        if (assetDto.getId() != null) {
            asset.setId(assetDto.getId());
        }
        asset.setName(assetDto.getName());
        asset.setSymbol(assetDto.getSymbol());
        asset.setDescription(assetDto.getDescription());
        asset.setType(AssetType.valueOf(assetDto.getType()));
        asset.setPrice(assetDto.getPrice());
        return asset;
    }
}
