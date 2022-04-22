package ma.ensias.winchesters.controller;

import ma.ensias.winchesters.auth.IAuthenticationFacade;
import ma.ensias.winchesters.dto.SignUpFormDto;
import ma.ensias.winchesters.dto.UserResponseDto;
import ma.ensias.winchesters.model.User;
import ma.ensias.winchesters.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static ma.ensias.winchesters.utils.Format.formatRequest;
import static org.springframework.http.HttpMethod.*;

@RestController
@RequestMapping(path = "/")
public class UserController {
    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;


    @Autowired
    public UserController(UserService userService, IAuthenticationFacade authenticationFacade) {
        this.userService = userService;
    }

    @GetMapping()
    public Map<String,String> authenticatedUser(){
        LOG.debug(formatRequest(GET,"authenticatedUser"));
        return userService.getAuthenticatedUser();
    }
    @GetMapping(path ="users")
    public List<UserResponseDto> getUsers(){
        LOG.debug(formatRequest(GET,"getUsers"));
        return userService.getUsers();
    }

    @GetMapping(path ="user")
    public UserResponseDto getUser(){
        LOG.debug(formatRequest(GET,"getUser"));
        return userService.getUser();
    }

    @PostMapping(path = {"user"},headers={"target=adminCreateUser"})
    public UserResponseDto adminCreateUser(@RequestBody User user){
        LOG.debug(formatRequest(POST,"adminCreateUser",user.toString()));
        return userService.adminCreateUser(user);
    }

    @PostMapping(path = {"signup"})
    public UserResponseDto signUp(@RequestBody SignUpFormDto signupForm){
        LOG.debug(formatRequest(POST,"signUp",signupForm.toString()));
        return userService.singUp(signupForm);
    }

    @RequestMapping(method = RequestMethod.PUT,path="user/{userId}",headers={"target=updateName"})
    public void updateName(@PathVariable Long userId,
                           @RequestBody(required = false) String firstName,
                           @RequestBody(required = false) String lastName) {
        LOG.debug(formatRequest(PUT,"updateName",userId.toString() + " - "+firstName + " " + lastName));
        userService.updateName(userId, firstName,lastName);
    }

    @RequestMapping(method = RequestMethod.PUT, path="user/{userId}",headers={"target=updateEmail"})
    public void updateEmail(@PathVariable Long userId,@RequestBody String email){
        LOG.debug(formatRequest(PUT,"updateEmail",userId.toString() + " - "+email   ));
        userService.updateEmail(userId, email);
    }
}