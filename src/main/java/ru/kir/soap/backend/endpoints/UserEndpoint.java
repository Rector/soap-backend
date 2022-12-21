package ru.kir.soap.backend.endpoints;

import lombok.RequiredArgsConstructor;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import ru.kir.soap.backend.entities.UserEntity;
import ru.kir.soap.backend.error_handling.InvalidDataException;
import ru.kir.soap.backend.error_handling.ResourceNotFoundException;
import ru.kir.soap.backend.generated_data.users.*;
import ru.kir.soap.backend.services.UserService;
import ru.kir.soap.backend.utils.Mapper;
import ru.kir.soap.backend.utils.Validator;

import java.util.List;
import java.util.stream.Collectors;

import static ru.kir.soap.backend.utils.Mapper.USER_ENTITY_TO_USER_FUNCTION;

@Endpoint
@RequiredArgsConstructor
public class UserEndpoint {
    private final UserService userService;

    @PayloadRoot(namespace = "http://www.kir.ru/soap/backend/users", localPart = "getAllUsersRequest")
    @ResponsePayload
    public GetAllUsersResponse findAllUsers() {
        GetAllUsersResponse response = new GetAllUsersResponse();

        List<User> userList = userService.findAllUsers()
                .stream()
                .map(USER_ENTITY_TO_USER_FUNCTION)
                .collect(Collectors.toList());

        response.getUsers()
                .addAll(userList);

        return response;
    }

    @PayloadRoot(namespace = "http://www.kir.ru/soap/backend/users", localPart = "getUserByLoginRequest")
    @ResponsePayload
    public GetUserByLoginResponse findUserByLogin(@RequestPayload GetUserByLoginRequest request) {
        boolean checker = Validator.checkValidString(request.getLogin());

        if (!checker) {
            throw new InvalidDataException("Login not specified");
        }

        GetUserByLoginResponse response = new GetUserByLoginResponse();

        UserEntity userEntity = userService.findUserByLogin(request.getLogin())
                .orElseThrow(() -> new ResourceNotFoundException(String.format("User with login: '%s' not found", request.getLogin())));

        User user = Mapper.userEntityToUserWithRoles(userEntity);
        response.setUser(user);

        return response;
    }

    @PayloadRoot(namespace = "http://www.kir.ru/soap/backend/users", localPart = "addUserRequest")
    @ResponsePayload
    public AddUserResponse addNewUser(@RequestPayload AddUserRequest request) {
        boolean checker = Validator.checkValidString(request.getLogin());

        if (!checker) {
            throw new InvalidDataException("Login not specified");
        }

        checker = Validator.checkValidString(request.getName());

        if (!checker) {
            throw new InvalidDataException("Name not specified");
        }

        checker = Validator.checkValidString(request.getPassword());

        if (request.getRoles() == null || request.getRoles().isEmpty()) {
            throw new InvalidDataException("Roles not specified");
        }

        if (!checker) {
            throw new InvalidDataException("Password not specified");
        }

        checker = Validator.checkValidPassword(request.getPassword());

        if (!checker) {
            throw new InvalidDataException(String.format("Password must contain an uppercase letter and a number. Specified password: '%s'",
                    request.getPassword()));
        }

        AddUserResponse response = new AddUserResponse();
        UserEntity userEntity = userService.addNewUser(request.getLogin(), request.getName(),
                request.getPassword(), request.getRoles())
                .orElseThrow(() -> new InvalidDataException("User creation error"));

        User user = Mapper.userEntityToUserWithRoles(userEntity);
        response.setUser(user);

        return response;
    }


    @PayloadRoot(namespace = "http://www.kir.ru/soap/backend/users", localPart = "updateUserRequest")
    @ResponsePayload
    public UpdateUserResponse updateUser(@RequestPayload UpdateUserRequest request) {
        if (request.getId() <= 0) {
            throw new InvalidDataException("Id must be a number and greater than 0");
        }

        boolean checker = Validator.checkValidString(request.getLogin());

        if (!checker) {
            throw new InvalidDataException("Login not specified");
        }

        checker = Validator.checkValidString(request.getName());

        if (!checker) {
            throw new InvalidDataException("Name not specified");
        }

        checker = Validator.checkValidString(request.getPassword());

        if (request.getRoles() == null || request.getRoles().isEmpty()) {
            throw new InvalidDataException("Roles not specified");
        }

        if (!checker) {
            throw new InvalidDataException("Password not specified");
        }

        checker = Validator.checkValidPassword(request.getPassword());

        if (!checker) {
            throw new InvalidDataException(String.format("Password must contain an uppercase letter and a number. Specified password: '%s'",
                    request.getPassword()));
        }

        UpdateUserResponse response = new UpdateUserResponse();
        UserEntity userEntity = userService.updateUser(request.getId(), request.getLogin(), request.getName(),
                request.getPassword(), request.getRoles())
                .orElseThrow(() -> new InvalidDataException("User update error"));

        User user = Mapper.userEntityToUserWithRoles(userEntity);
        response.setUser(user);

        return response;
    }

    @PayloadRoot(namespace = "http://www.kir.ru/soap/backend/users", localPart = "deleteUserByLoginRequest")
    @ResponsePayload
    public void deleteUserByLogin(@RequestPayload DeleteUserByLoginRequest request) {
        userService.deleteUserByLogin(request.getLogin());
    }

}