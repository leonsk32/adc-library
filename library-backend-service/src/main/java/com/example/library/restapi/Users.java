package com.example.library.restapi;

import com.example.library.app_service.UserService;
import com.example.library.domain.user.User;
import com.example.library.restapi.dto.UserDto;
import com.example.library.restapi.dto.UsersDto;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.NOT_IMPLEMENTED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class Users {

    private final UserService service;

    @GetMapping("users")
    @CrossOrigin
    public ResponseEntity<UsersDto> search() {
        List<User> users = service.searchAll();
        return new ResponseEntity<>(convert(users), OK);
    }

    @GetMapping("users/{userId}")
    @CrossOrigin
    public ResponseEntity<UserDto> search(@PathVariable("userId") String userId) {
        User user = service.searchById(userId);
        UserDto userDto = new UserDto();
        userDto.setUserId(user.getUserId());
        userDto.setEmail(user.getEmail());
        userDto.setSimei(user.getSimei());
        userDto.setNamae(user.getNamae());
        return new ResponseEntity<>(userDto, OK);
    }

    @PutMapping("users/{userId}")
    @CrossOrigin
    public ResponseEntity<Void> register(@PathVariable("userId") String userId, @RequestBody @Valid RequestParam body) {

        return new ResponseEntity<>(NOT_IMPLEMENTED);
    }

    @DeleteMapping("users/{userId}")
    @CrossOrigin
    public ResponseEntity<Void> delete(@PathVariable("userId") String userId) {
        return new ResponseEntity<>(NOT_IMPLEMENTED);
    }

    @Data
    private static class RequestParam {
        @NotNull
        private String userId;
        @NotNull
        private String email;
        @NotNull
        private String namae;
        @NotNull
        private String simei;

    }

    private UsersDto convert(List<User> users) {
        UsersDto result = new UsersDto();
        List<UserDto> userDtoList = new ArrayList<>();

        for (User user : users) {
            UserDto userDto = new UserDto();
            userDto.setUserId(user.getUserId());
            userDto.setEmail(user.getEmail());
            userDto.setSimei(user.getSimei());
            userDto.setNamae(user.getNamae());
            userDtoList.add(userDto);
        }

        result.setUsers(userDtoList);
        return result;
    }
}
