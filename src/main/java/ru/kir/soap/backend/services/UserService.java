package ru.kir.soap.backend.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kir.soap.backend.entities.RoleEntity;
import ru.kir.soap.backend.entities.UserEntity;
import ru.kir.soap.backend.error_handling.InvalidDataException;
import ru.kir.soap.backend.error_handling.ResourceNotFoundException;
import ru.kir.soap.backend.repositories.UserRepository;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RoleService roleService;

    public List<UserEntity> findAllUsers() {
        return userRepository.findAll();
    }

    private UserEntity checkAndFindUserByLogin(String login) {
        return userRepository.findByLogin(login)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("User with login: '%s' not found", login)));
    }

    @Transactional
    private Set<RoleEntity> checkRoleListAndFormRoleSet(List<String> roleList) {
        Set<RoleEntity> roleEntitySet = new HashSet<>();

        for (String roleName : roleList) {
            RoleEntity roleEntity = roleService.findRoleByName(roleName)
                    .orElseThrow(() -> new InvalidDataException(String.format("Role: '%s' does not exist", roleName)));

            roleEntitySet.add(roleEntity);
        }

        return roleEntitySet;
    }

    @Transactional
    public Optional<UserEntity> findUserByLogin(String login) {
        UserEntity userEntity = checkAndFindUserByLogin(login);
        userEntity.getRoles().size();
        return Optional.of(userEntity);
    }

    @Transactional
    public Optional<UserEntity> addNewUser(String login, String name,
                                           String password, List<String> roleList) {
        UserEntity userEntity = new UserEntity();

        Optional<UserEntity> optionalUserEntity = userRepository.findByLogin(login);

        if (optionalUserEntity.isPresent()) {
            throw new InvalidDataException(String.format("User with login: '%s' already exists", login));
        }

        userEntity.setLogin(login);
        userEntity.setName(name);
        userEntity.setPassword(password);

        Set<RoleEntity> roleEntitySet = checkRoleListAndFormRoleSet(roleList);
        userEntity.setRoles(new ArrayList<>(roleEntitySet));

        userEntity = userRepository.save(userEntity);

        return Optional.of(userEntity);
    }


    @Transactional
    public Optional<UserEntity> updateUser(Long id, String login, String name,
                                           String password, List<String> roleList) {

        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("User with id: '%d' not found", id)));

        Optional<UserEntity> optionalUserEntity = userRepository.findByLogin(login);

        if (optionalUserEntity.isPresent()) {
            throw new InvalidDataException(String.format("User with login: '%s' already exists", login));
        }

        userEntity.setLogin(login);
        userEntity.setName(name);
        userEntity.setPassword(password);

        Set<RoleEntity> roleEntitySet = checkRoleListAndFormRoleSet(roleList);
        userEntity.setRoles(new ArrayList<>(roleEntitySet));

        return Optional.of(userEntity);
    }

    @Transactional
    public void deleteUserByLogin(String login) {
        userRepository.deleteByLogin(login);
    }

}
