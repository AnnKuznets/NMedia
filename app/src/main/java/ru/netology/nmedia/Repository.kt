package ru.netology.nmedia

import androidx.lifecycle.LiveData
import ru.netology.nmedia.Post

interface PostRepository {
    fun likedById(id: Long)
    fun removeById(id: Long)
    fun share(id: Long)
    fun get(): LiveData<List<Post>>
    fun save(post: Post)
    fun cancel(id: Long)

}