package ru.netology.nmedia.dao

import android.content.Context
import ru.netology.nmedia.Post

interface PostDao {
    abstract val context: Context

    fun getAll(): List<Post>
    fun save(post: Post): Post
    fun likeById(id: Long)
    fun removeById(id: Long)
    fun share(id: Long)
}