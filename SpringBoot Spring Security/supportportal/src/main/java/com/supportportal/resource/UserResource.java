package com.supportportal.resource;

import com.supportportal.domain.HttpResponse;
import com.supportportal.domain.User;
import com.supportportal.domain.UserPrinciple;
import com.supportportal.exception.domain.EmailExistException;
import com.supportportal.exception.domain.EmailNotFoundException;
import com.supportportal.exception.domain.ExceptionHandling;
import com.supportportal.exception.domain.UsernameExistException;
import com.supportportal.service.UserService;
import com.supportportal.utility.JWTTokenProvider;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;

import static com.supportportal.constant.FileConstant.*;
import static com.supportportal.constant.SecurityConstant.JWT_TOKEN_HEADER;
import static org.springframework.http.MediaType.IMAGE_JPEG_VALUE;

@AllArgsConstructor
@RestController
@RequestMapping(path = {"/","/user"})
public class UserResource extends ExceptionHandling {
    public static final String EMAIL_SENT = "An Email with a new Password sent to: ";
    public static final String USER_DELETED_SUCCESSFULLY = "User deleted successfully";
    private UserService userService;
    private AuthenticationManager authenticationManager;
    private JWTTokenProvider jwtTokenProvider;


    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody User user){
        authenticate(user.getUsername(),user.getPassword());
        User userLogin = userService.findByUsername(user.getUsername());
        UserPrinciple userPrinciple = new UserPrinciple(userLogin);
        HttpHeaders jwtHeader = getJwtHeader(userPrinciple);
        return new ResponseEntity<>(userLogin,jwtHeader,HttpStatus.OK);
    }

    private HttpHeaders getJwtHeader(UserPrinciple userPrinciple) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(JWT_TOKEN_HEADER,jwtTokenProvider.generateJwtToken(userPrinciple));
        return headers;
    }

    private void authenticate(String username, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,password));
    }


    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) throws EmailExistException, UsernameExistException, MessagingException {
        User newUser = userService.register(user.getFirstName(),user.getLastName(),
                                                user.getUsername(),user.getEmail());
        return new ResponseEntity<>(newUser, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<User> addNewUser(@RequestParam("firstName")String firstName,
                                           @RequestParam("lastName")String lastName,
                                           @RequestParam("username")String username,
                                           @RequestParam("email")String email,
                                           @RequestParam("role")String role,
                                           @RequestParam("isActive")String isActive,
                                           @RequestParam("isNonLocked")String isNonLocked,
                                           @RequestParam(value = "profileImg", required = false) MultipartFile profileImg) throws EmailExistException, IOException, UsernameExistException {

        User newUser = userService.addNewUser(firstName,lastName,username,email,role,
                            Boolean.parseBoolean(isActive),Boolean.parseBoolean(isNonLocked),profileImg);

        return new ResponseEntity<>(newUser,HttpStatus.OK);
    }

    @GetMapping("/find/{username}")
    public ResponseEntity<User> getUser(@PathVariable("username")String username){
        User user = userService.findByUsername(username);
        return new ResponseEntity<>(user,HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<User> updateUser(@RequestParam("username")String currentUsername,
                                           @RequestParam("firstName")String firstName,
                                           @RequestParam("lastName")String lastName,
                                           @RequestParam("username")String username,
                                           @RequestParam("email")String email,
                                           @RequestParam("role")String role,
                                           @RequestParam("isActive")String isActive,
                                           @RequestParam("isNonLocked")String isNonLocked,
                                           @RequestParam(value = "profileImg", required = false) MultipartFile profileImg) throws EmailExistException, IOException, UsernameExistException {

        User updateUser = userService.updateUser(currentUsername,firstName,lastName,username,email,role,
                Boolean.parseBoolean(isActive),Boolean.parseBoolean(isNonLocked),profileImg);

        return new ResponseEntity<>(updateUser,HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<List<User>> getAllUser(){
        List<User> users = userService.getUsers();
        return new ResponseEntity<>(users,HttpStatus.OK);
    }

    @GetMapping("/resetPassword/{email}")
    public ResponseEntity<HttpResponse> resetPassword(@PathVariable("email")String email) throws EmailNotFoundException, MessagingException {
        userService.resetPassword(email);
        return response(HttpStatus.OK, EMAIL_SENT + email);
    }

    private ResponseEntity<HttpResponse> response(HttpStatus http, String message) {
        HttpResponse body = new HttpResponse(
                new Date(),http.value(),http,http.getReasonPhrase().toUpperCase(), message.toUpperCase());
        return new ResponseEntity<>(body,http);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyAuthority('user:delete')")
    public ResponseEntity<HttpResponse> deleteUser(@PathVariable("id")Long id){
        userService.deletUser(id);
        return response(HttpStatus.NO_CONTENT, USER_DELETED_SUCCESSFULLY);
    }

    @PutMapping("/updateProfileImg")
    public ResponseEntity<User> updateProfileImg(@RequestParam("username")String username,
                                           @RequestParam(value = "profileImg") MultipartFile profileImg) throws EmailExistException, IOException, UsernameExistException {

        User user = userService.updateProfileImage(username,profileImg);

        return new ResponseEntity<>(user,HttpStatus.OK);
    }
    @GetMapping(path = "/image/{username}/{fileName}", produces = IMAGE_JPEG_VALUE)
    public byte[] getProfileImage(@PathVariable("username")String username,
                                  @PathVariable("fileName")String fileName) throws IOException {

        return Files.readAllBytes(Paths.get(USER_FOLDER + username + FORWARD_SLASH + fileName));

    }

    @GetMapping(path = "/image/profile/{username}", produces = IMAGE_JPEG_VALUE)
    public byte[] getTempProfileImage(@PathVariable("username")String username) throws IOException {
        URL url = new URL(TEMP_PROFILE_IMAGE_BASE_URL + username);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try(InputStream inputStream = url.openStream()) {
            int bytesRead;
            byte[] chunk = new byte[1024];
            while ((bytesRead = inputStream.read(chunk)) > 0){
                byteArrayOutputStream.write(chunk,0,bytesRead);
            }
        }
        return byteArrayOutputStream.toByteArray();

    }


}
