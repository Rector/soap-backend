package ru.kir.soap.backend.utils;

import ru.kir.soap.backend.entities.RoleEntity;
import ru.kir.soap.backend.entities.UserEntity;
import ru.kir.soap.backend.generated_data.roles.Role;
import ru.kir.soap.backend.generated_data.users.User;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Mapper {
    public static final Function<UserEntity, User> USER_ENTITY_TO_USER_FUNCTION = userEntity -> {
        User user = new User();
        user.setName(userEntity.getName());
        user.setLogin(userEntity.getLogin());
        return user;
    };

    public static final Function<RoleEntity, Role> ROLE_ENTITY_TO_ROLE_FUNCTION = roleEntity -> {
        Role role = new Role();
        role.setName(roleEntity.getName());
        return role;
    };

    public static User userEntityToUserWithRoles(UserEntity userEntity) {
        User user = new User();
        user.setName(userEntity.getName());
        user.setLogin(userEntity.getLogin());

        List<Role> roleList = userEntity.getRoles()
                .stream()
                .map(ROLE_ENTITY_TO_ROLE_FUNCTION)
                .collect(Collectors.toList());
        user.getRoles().addAll(roleList);
        return user;
    }

    private Mapper() {
    }

}
