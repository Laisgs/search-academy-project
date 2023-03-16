package co.empathy.academy.search.users.controllers;

import co.empathy.academy.search.users.entities.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserController {

    @Operation(summary = "Get user by id",
            parameters = {@Parameter(in = ParameterIn.PATH, name = "id", description = "User id, an integrer")},
            responses ={
            @ApiResponse(responseCode = "200",
            description = "operation done",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "404",
            description = "User don't exist",
            content = @Content(mediaType = "text/plain"))
    })
    ResponseEntity<User> getUser(@PathVariable int id);

    @Operation(summary = "List all users",
    responses ={
        @ApiResponse(responseCode = "200",
                description = "operation done",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class)))
    })
    ResponseEntity<List<User>> getUsers();

    @Operation(summary = "Add user",
    responses ={
        @ApiResponse(responseCode = "201",
                description = "user added",
                content = @Content(mediaType = "text/plain")),
        @ApiResponse(responseCode = "409",
                description = "User already exist",
                content = @Content(mediaType = "text/plain"))
    })
    ResponseEntity<String> addUser(@RequestBody User user);

    @Operation(summary = "Edit user",
            responses ={
                    @ApiResponse(responseCode = "200",
                            description = "information change",
                            content = @Content(mediaType = "text/plain")),
                    @ApiResponse(responseCode = "404",
                            description = "User don't exist",
                            content = @Content(mediaType = "text/plain"))
            })
    ResponseEntity<String> editUser(@RequestBody User user);

    @Operation(summary = "Delete user by id",
            parameters = {@Parameter(in = ParameterIn.PATH, name = "id", description = "User id, an integrer")},
            responses ={
                    @ApiResponse(responseCode = "200",
                            description = "user deleted",
                            content = @Content(mediaType = "text/plain")),
                    @ApiResponse(responseCode = "404",
                            description = "User don't exist",
                            content = @Content(mediaType = "text/plain"))
            })
    ResponseEntity<String> deleteUser(@PathVariable int id);

    void addUsersAsync(@RequestParam("file") MultipartFile file);
}
