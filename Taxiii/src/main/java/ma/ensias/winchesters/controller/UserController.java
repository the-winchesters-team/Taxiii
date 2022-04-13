package ma.ensias.winchesters.controller;

import ma.ensias.winchesters.auth.IAuthenticationFacade;
import ma.ensias.winchesters.dto.SignUpFormDto;
import ma.ensias.winchesters.dto.UserResponseDto;
import ma.ensias.winchesters.model.User;
import ma.ensias.winchesters.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService, IAuthenticationFacade authenticationFacade) {
        this.userService = userService;
    }

    @GetMapping()
    public Map<String,String> authenticatedUser(){
        return userService.getAuthenticatedUser();
    }
    @GetMapping(path ="users")
    public List<UserResponseDto> getUsers(){
        return userService.getUsers();
    }

    @GetMapping(path ="user")
    public UserResponseDto getUser(){
        return userService.getUser();
    }

    @PostMapping(path = {"user"},headers={"target=adminCreateUser"})
    public UserResponseDto adminCreateUser(@RequestBody User user){
        return userService.adminCreateUser(user);
    }

    @PostMapping(path = {"signup"})
    public UserResponseDto signUp(@RequestBody SignUpFormDto signupForm){
        return userService.singUp(signupForm);
    }

    @RequestMapping(method = RequestMethod.PUT,path="user/{userId}",headers={"target=updateName"})
    public void updateName(@PathVariable Long userId,
                           @RequestBody(required = false) String firstName,
                           @RequestBody(required = false) String lastName) {
        userService.updateName(userId, firstName,lastName);
    }

    @RequestMapping(method = RequestMethod.PUT, path="user/{userId}",headers={"target=updateEmail"})
    public void updateEmail(@PathVariable Long userId,@RequestBody String email){
        userService.updateEmail(userId, email);
    }
}