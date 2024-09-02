package webservice

import dto.UserDTO
import model.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import request.UserRequest
import service.UserService

@RestController
class UserController {

    @Autowired
    lateinit var service: UserService

    @GetMapping("/")
    fun holaMundo(): String {
        return "Hola Mundo"
    }

    @PostMapping("/register")
    fun saveUser(@RequestBody userRequest: UserRequest): ResponseEntity<UserDTO> {
        val user = User(
            userRequest.name,
            userRequest.lastName,
            userRequest.email,
            userRequest.address,
            userRequest.password,
            userRequest.cvuMercadoPago,
            userRequest.cryptoAddress
        )
        service.createUser(user)
        val userDTO = UserDTO(user.name!!, user.point)
        return ResponseEntity(userDTO, HttpStatus.CREATED)
    }

    @GetMapping("/users")
    fun getUsers(): List<User> {
        return service.allUsers()
    }
}