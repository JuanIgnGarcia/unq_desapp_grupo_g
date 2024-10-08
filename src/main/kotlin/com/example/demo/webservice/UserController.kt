package com.example.demo.webservice

import com.example.demo.dto.UserDTO
import com.example.demo.model.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import com.example.demo.request.UserRequest
import com.example.demo.service.UserService
import org.springframework.web.bind.annotation.*

@RestController
class UserController {

    @Autowired
    lateinit var service: UserService

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
            .build()
        service.createUser(user)
        val userDTO = UserDTO(user.name!!,user.lastName!!, user.point)
        return ResponseEntity(userDTO, HttpStatus.CREATED)
    }

    @GetMapping("/users")
    fun getUsers(): List<UserDTO> {
        val users = service.allUsers().map { currentUser: User -> UserDTO(currentUser.name!!,currentUser.lastName!!,currentUser.point) }
        return users
    }
}