package ru.netology.nmedia

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import ru.netology.nmedia.PostRepositoryInMemory
import ru.netology.nmedia.PostRepository

class PostViewModel : ViewModel() {
    private val repository : PostRepository = PostRepositoryInMemory()

    fun likedById(id: Long) {
        repository.likedById(id)
    }
    fun share(id: Long){
        repository.share(id)
    }

    fun view(id: Long){
      repository.view(id)
    }

    fun get(): LiveData<List<Post>> = repository.get()
}