package org.resource.service;

import org.resource.api.rest.dto.AssetDto;
import org.resource.error.ResourceNotFoundException;
import org.resource.model.Asset;
import org.resource.model.AssetType;
import org.resource.repository.AssetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AssetService {

    private final AssetRepository assetRepository;

    @Autowired
    public AssetService(AssetRepository assetRepository) {
        this.assetRepository = assetRepository;
    }

    public List<Asset> getAllAssets() {
        return assetRepository.findAll();
    }

    public Optional<Asset> getAssetById(Long id) {
        return assetRepository.findById(id);
    }

    public Asset createAsset(AssetDto asset) {
        return assetRepository.save(AssetDto.toAsset(asset));
    }

    public Asset updateAsset(Long id, AssetDto updatedAsset) {
        return assetRepository.findById(id)
                .map(existing -> {
                    existing.setName(updatedAsset.getName());
                    existing.setSymbol(updatedAsset.getSymbol());
                    existing.setDescription(updatedAsset.getDescription());
                    existing.setType(AssetType.valueOf(updatedAsset.getType()));
                    existing.setPrice(updatedAsset.getPrice());
                    return assetRepository.save(existing);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Asset not found"));
    }

    public void deleteAsset(Long id) {
        assetRepository.deleteById(id);
    }

}
