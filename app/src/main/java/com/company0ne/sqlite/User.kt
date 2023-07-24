package com.company0ne.sqlite

import java.io.Serializable

data class User(
    val id: Int,
    val name: String,
    val age: Int,
    val phone: String,
    val email: String
) : Serializable
