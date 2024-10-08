package com.example.demo.webservice

import com.example.demo.dto.UserDTO
import com.example.demo.model.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import com.example.demo.request.UserRequest
import com.example.demo.service.UserService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.web.bind.annotation.*

@RestController
class UserController {

    @Autowired
    lateinit var service: UserService

    @Operation(summary = "Register a new user")
    @PostMapping("/register")
    fun saveUser(@RequestBody userRequest: UserRequest): ResponseEntity<UserDTO> {
        val user =
        User.UserBuilder()
            .name( userRequest.name)
            .lastName(userRequest.lastName)
            .email(userRequest.email)
            .address(userRequest.address)
            .password(userRequest.password)
            .cvuMercadoPago(userRequest.cvuMercadoPago)
            .cryptoAddress(userRequest.cryptoAddress)
            .point(0)
            .mountCompletedTransactions(0)
            .build()
        service.createUser(user)
        val userDTO = UserDTO(user.id.toString(),user.name!!,
                              user.lastName!!,
                              user.reputation())
        return ResponseEntity(userDTO, HttpStatus.CREATED)
    }

    @Operation(summary = "Get all users")
    @GetMapping("/users")
    fun getUsers(): List<UserDTO> {
        val users = service.allUsers().map { currentUser: User<Any?> -> UserDTO(currentUser.id.toString(),
                                                                                currentUser.name!!,currentUser.lastName!!,
                                                                                currentUser.reputation()) }
        return users
    }
}