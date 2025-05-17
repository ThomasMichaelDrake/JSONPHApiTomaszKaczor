package com.example.jsonphapitomaszkaczor.data.repository

import com.example.jsonphapitomaszkaczor.data.models.Post
import com.example.jsonphapitomaszkaczor.data.models.Todo
import com.example.jsonphapitomaszkaczor.data.models.User
import com.example.jsonphapitomaszkaczor.data.network.ApiService

class MainRepository(private val api: ApiService) {

    suspend fun getPosts() = api.getPosts()
    suspend fun getPost(id: Int) = api.getPost(id)
    suspend fun getUsers() = api.getUsers()
    suspend fun getUser(id: Int) = api.getUser(id)
    suspend fun getTodos(userId: Int) = api.getTodos(userId)
}