package ru.netology.nmedia


import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class PostRepositoryFileImpl (val context: Context): PostRepository {

    private val gson = Gson()
    private val type = TypeToken.getParameterized(List::class.java, Post::class.java).type
    private val filename = "post.json"
    private var nextId = 1L
    private var posts = emptyList<Post>()
    private val data = MutableLiveData(posts)

    init {
        val file = context.filesDir.resolve(filename)
        if (file.exists()) {
            context.openFileInput(filename).bufferedReader().use {
                posts = gson.fromJson(it, type)
                nextId = posts.maxOfOrNull { post -> post.id }?.inc() ?:1L
                data.value = posts
            }
        } else {
            sync()
        }
    }


    override fun likedById(id: Long) {
        posts = posts.map {
            if (it.id != id) it else it.copy(
                likedByMe = !it.likedByMe,
                like = if (it.likedByMe) it.like - 1 else it.like + 1
            )
        }
        data.value = posts
        sync()
        }

    override fun share(id: Long) {
        data.value = data.value?.map {
            if (id != it.id) it else it.copy(
                shared = !it.shared,
                shares = it.shares + 1
            )
        }
        sync()
    }
    override fun removeById(id: Long) {
        posts = posts.filter { it.id != id }
        data.value = posts
        sync()
    }

    override fun save(post: Post) {
        if (post.id == 0L) {
            posts = listOf(post.copy(id = posts.firstOrNull()?.id?.inc() ?: 0)) + posts
            data.value = posts
            sync()
            return
        }
        posts = posts.map {
            if(it.id == post.id) {
                it.copy(content = post.content)
            }
            else {
                it
            }
        }
        data.value = posts
        sync()
    }
        override fun get(): LiveData<List<Post>> = data

    private fun sync() {
        context.openFileOutput(filename, Context.MODE_PRIVATE).bufferedWriter().use {
            it.write(gson.toJson(posts))
        }
    }
}