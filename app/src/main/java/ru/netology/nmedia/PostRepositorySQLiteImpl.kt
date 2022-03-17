package ru.netology.nmedia


import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.dao.PostDao

class PostRepositorySQLiteImpl (
    private val dao: PostDao
): PostRepository {
    private var posts = emptyList<Post>()
    private val data = MutableLiveData(posts)

    init {
        posts = dao.getAll()
        data.value = posts
    }

    override fun likedById(id: Long) {
        dao.likeById(id)
        posts = posts.map {
            if (it.id != id) it else it.copy(
                likedByMe = !it.likedByMe,
                like = if (it.likedByMe) it.like - 1 else it.like + 1
            )
        }
        data.value = posts
        }

    override fun share(id: Long) {
        dao.share(id)
        data.value = data.value?.map {
            if (id != it.id) it else it.copy(
                shared = !it.shared,
                shares = it.shares + 1
            )
        }
    }
    override fun removeById(id: Long) {
        dao.removeById(id)
        posts = posts.filter { it.id != id }
        data.value = posts
    }

    override fun save(post: Post) {
        val saved = dao.save(post)
        val id = post.id

        posts = if (id == 0L) {
            listOf(saved) + posts
            } else {
                posts.map {
                    if (it.id != id) it else saved
                }
        }
        data.value = posts
    }
        override fun get(): LiveData<List<Post>> = data

//    private fun sync() {
//        context.openFileOutput(filename, Context.MODE_PRIVATE).bufferedWriter().use {
//            it.write(gson.toJson(posts))
//        }
//    }
}