package com.fluxchat.fluxchat.controllers;

import com.fluxchat.fluxchat.controllers.dtos.CreateValidationGroup;
import com.fluxchat.fluxchat.controllers.dtos.UserChatParticipantDto;
import com.fluxchat.fluxchat.controllers.dtos.UserDto;
import com.fluxchat.fluxchat.enteties.User;
import com.fluxchat.fluxchat.services.interfaces.AuthService;
import com.fluxchat.fluxchat.services.interfaces.UserService;
import jakarta.validation.groups.Default;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(path = "registration")
    public User registration(@RequestBody @Validated(value = { Default.class, CreateValidationGroup.class }) UserDto userDto) {
        User user = new User();
        BeanUtils.copyProperties(userDto, user);
        User created = userService.create(user);
        return created;
    }

    @GetMapping()
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Page<User>> getAll(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
            @RequestParam(name = "sort", defaultValue = "firstName") String sort,
            @RequestParam(name = "mafId", required = false) Long mafId) {

        Page<User> userPage = userService.getAllPages(page, pageSize, sort, mafId );
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Total-Count", String.valueOf(userPage.getTotalElements()));

        return new ResponseEntity<>(userPage, headers, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public UserDto getUser( @PathVariable("id") Long id ) {
       User user = userService.findById( id );
       UserDto userDto = new UserDto();
       BeanUtils.copyProperties(user,userDto);

       return userDto;
    }

    @PutMapping("/{id}")
    public User editUser(@PathVariable("id") Long id, @RequestBody UserDto userDto) {
        User updateUser = new User();
        BeanUtils.copyProperties(userDto, updateUser);
        return userService.update(id, updateUser);
    }

    @GetMapping("/out-of-chat/{id}")
    public List<UserChatParticipantDto> findAllUsersOutOfChat(@PathVariable("id") Long chatId) {
        return userService.findAllUsersOutOfChat(chatId);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.delete(id);
    }
}
