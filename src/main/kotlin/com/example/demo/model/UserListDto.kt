package com.example.demo.model


class UserListDto(user: User<*>) {
    private val name: String
    private val lastName: String
    private val operationsPerformed: Int
    private val reputation: Int

    init {
        this.name = user.userName()
        this.lastName = user.userLastName()
        this.operationsPerformed = user.mountCompletedTransactions
        this.reputation = user.point
    }
}