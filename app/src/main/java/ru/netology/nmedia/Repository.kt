package ru.netology.nmedia

import androidx.lifecycle.LiveData
import ru.netology.nmedia.Post

interface PostRepository {
    fun like()
    fun share()
    fun view()
    fun get(): LiveData<Post>
}