package ru.kir.soap.backend.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kir.soap.backend.entities.RoleEntity;
import ru.kir.soap.backend.repositories.RoleRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public Optional<RoleEntity> findRoleByName(String name) {
        return roleRepository.findByName(name);
    }

}
