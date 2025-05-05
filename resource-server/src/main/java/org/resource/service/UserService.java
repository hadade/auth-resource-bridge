package org.resource.service;

import org.resource.api.rest.dto.UserDto;
import org.resource.error.ConflictException;
import org.resource.error.ResourceNotFoundException;
import org.resource.model.User;
import org.resource.repository.AssetRepository;
import org.resource.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final AssetRepository assetRepository;

    @Autowired
    public UserService(UserRepository userRepository, AssetRepository assetRepository) {
        this.userRepository = userRepository;
        this.assetRepository = assetRepository;
    }

    public User saveUser(UserDto user) {
        return userRepository.save(UserDto.toUser(user));
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public void assignAssetsByUserId(Long userId, Long assetId) {
        userRepository.findById(userId)
                .ifPresentOrElse(user -> assetRepository.findById(assetId)
                                .ifPresentOrElse(asset -> {
                                            if (asset.getOwner() != null) {
                                                throw new ConflictException("Asset already owned");
                                            }
                                            user.getAssets().add(asset);
                                            asset.setOwner(user);
                                            userRepository.save(user);
                                        },
                                        () -> {
                                            throw new ResourceNotFoundException("Asset not found");
                                        }),
                        () -> {
                            throw new ResourceNotFoundException("User not found");
                        });
    }

}
