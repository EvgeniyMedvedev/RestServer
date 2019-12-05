package boot.controller;

import boot.model.User;
import boot.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@org.springframework.web.bind.annotation.RestController
public class RestController {

    private final UserService service;

    private final AuthenticationManager authenticationManager;

    private static final Logger logger = LoggerFactory.getLogger(User.class);

    @Autowired
    public RestController(UserService service, AuthenticationManager authenticationManager) {
        this.service = service;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody User user){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getLogin(),user.getPassword()));
        return ResponseEntity.ok(user);
    }

    @PostMapping(value = "/edit")
    public ResponseEntity<User> edit(@RequestBody User user) {
        service.add(user);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping(value = "/users")
    public  ResponseEntity<List<User>> getAll() {

        return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<User> getUser(@PathVariable int id) {
        return new ResponseEntity<>(service.getById(id), HttpStatus.OK);
    }

    @GetMapping(path = "/admin/{login}")
    public ResponseEntity<User> getUser(@PathVariable String login) {
        return new ResponseEntity<>(service.getByLogin(login), HttpStatus.OK);
    }

    @PostMapping(value = "/add")
    public ResponseEntity<User> addUser(@RequestBody User user) {
        service.add(user);
        logger.info(user.toString() + " - Created");
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}/delete")
    public void delete(@PathVariable("id") int id) {
        service.delete(id);
        logger.info("№ " + id + " - удален");
//        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(value = "/{id}/edit")
    public ResponseEntity<User> edit(@PathVariable("id") int id, @RequestBody User user) {
        logger.info(user.toString());
        service.add(user);
        logger.info(user.toString() + " - Edited");

        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
