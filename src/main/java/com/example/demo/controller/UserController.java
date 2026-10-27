package com.example.demo.controller;

import com.example.demo.dto.PagebleResponse;
import com.example.demo.dto.UserProfileDto;
import com.example.demo.model.UserProfile;
import com.example.demo.service.UserProfileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/yt-console")
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost/"}, allowedHeaders = "*")
//@CrossOrigin(origins = "*") // разрешаем CORS для Angular
public class UserController {

    private final UserProfileService service;

    public UserController(UserProfileService service) {
        this.service = service;
    }

    @GetMapping("/account/test_accounts")
    public PagebleResponse<UserProfileDto> getFakeUsers(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        return service.getAll(page, size);
    }

    @GetMapping("/account/subscribers")
    public PagebleResponse<UserProfileDto> getSubscribers(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        return service.getAll(page, size);
    }

    //http://localhost:8080/yt-console/account/accounts?firstName=nbb&lastName=&stack=
    @GetMapping("/account/accounts")//filters
    public PagebleResponse<UserProfile> getAccounts(
            @RequestParam(value = "firstName", required = false) String firstName,
            @RequestParam(value = "lastName", required = false) String lastName,
            @RequestParam(value = "stack", required = false) String stack,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "5") int size
    ) {
        System.out.println("firstName " + firstName + " lastName " + lastName + " stack " + stack + " page " + page + " size " + size);
        return service.searchByFilters(firstName, lastName, stack, page, size);
    }

    @PostMapping("/account/upload_image")
    public ResponseEntity<UserProfileDto> uploadImage(@RequestParam("image") MultipartFile image) { // id
        return service.patchMyAvatar(image)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
    }

    @GetMapping("/account/me")
    public ResponseEntity<UserProfileDto> getMe() {
        return service.showMyProfile()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/account/me")
    public ResponseEntity<UserProfileDto> patchMe(@RequestBody Map<String, Object> updates) {
        //patch(id, updates)
        return service.patchMyProfile(updates)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/account/{id}")
    public ResponseEntity<UserProfileDto> getAccount(@PathVariable Long id) {
        return service.getProfileById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

/*
    @GetMapping("/account/subscribers")
    public PagebleResponse<UserProfile> getSubscribers(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "50") int totalCount) {
        List<UserProfile> allUsers = service.generateFakeUsers(totalCount); // вся коллекция
        int fromIndex = page * size;
        int toIndex = Math.min(fromIndex + size, allUsers.size());
        // Если страница выходит за пределы
        if (fromIndex >= allUsers.size()) {
            return new PagebleResponse<>(Collections.emptyList(), allUsers.size(), page, size);
        }
        List<UserProfile> pageContent = allUsers.subList(fromIndex, toIndex);
        return new PagebleResponse<>(pageContent, allUsers.size(), page, size);
    }
*/

    /// ////////////////////////////////////////////EXAMPLE/////////////////////////////////////////////////////////////

    //@GetMapping("/account/{id}")
    public ResponseEntity<UserProfile> getById(@PathVariable Long id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //@PostMapping
    public ResponseEntity<UserProfile> create(@RequestBody UserProfile profile) {
        return ResponseEntity.ok(service.create(profile));
    }

    //@PutMapping("/account/{id}")
    public ResponseEntity<UserProfile> update(@PathVariable Long id, @RequestBody UserProfile profile) {
        return service.update(id, profile)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //@PatchMapping("/account/{id}")
    public ResponseEntity<UserProfile> patch(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        return service.patch(id, updates)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //@DeleteMapping("/account/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return service.delete(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    //@RequestMapping(method = RequestMethod.OPTIONS)
    public ResponseEntity<?> options() {
        return ResponseEntity.ok().build();
    }

}
