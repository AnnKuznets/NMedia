package ru.netology.nmedia

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


private val empty = Post(
    0L,
    "",
    "",
    "",
    0,
    0,
    false,
    false,
)

class PostViewModel : ViewModel() {
    private val repository: PostRepository = PostRepositoryInMemory()
    val edited = MutableLiveData(empty)

    fun likedById(id: Long) {
        repository.likedById(id)
    }

    fun removeById(id: Long) {
        repository.removeById(id)
    }

    fun share(id: Long) {
        repository.share(id)
    }

    fun edit(post: Post) {
        edited.value = post
    }

    fun cancel() {
        edited.value = empty
    }

        fun get(): LiveData<List<Post>> = repository.get()

        fun editContent(text: String) {
            val formatted = text.trim()
            if (edited.value?.content == formatted) {
                return
            }
            edited.value = edited.value?.copy(content = formatted)
        }

        fun save() {
            edited.value?.let {
                repository.save(it)
            }
            edited.value = empty
        }
    }



