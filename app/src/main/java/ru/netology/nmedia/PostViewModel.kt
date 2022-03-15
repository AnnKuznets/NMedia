package ru.netology.nmedia

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.db.AppDb


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

class PostViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: PostRepository = PostRepositorySQLiteImpl(AppDb.getInstance(application).postDao
    )
    val data = repository.get()
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



