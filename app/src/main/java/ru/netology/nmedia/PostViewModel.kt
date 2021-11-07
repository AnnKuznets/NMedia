package ru.netology.nmedia

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import ru.netology.nmedia.PostRepositoryInMemory
import ru.netology.nmedia.PostRepository

class PostViewModel : ViewModel() {
    private val repository : PostRepository = PostRepositoryInMemory()

    fun like() {
        repository.like()
    }
    fun share(){
        repository.share()
    }

    fun view(){
        repository.view()
    }

    fun get(): LiveData<Post> = repository.get()
}