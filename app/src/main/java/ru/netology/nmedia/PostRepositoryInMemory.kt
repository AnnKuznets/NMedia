package ru.netology.nmedia

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.Post

class PostRepositoryInMemory : PostRepository {

    private val posts = List(10) {
        Post(
            id = it.toLong(),
            author = "Нетология. Университет интернет-профессий будущего",
            content = "№$it Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
            published = "21 мая в 18:36",
        )
    }

    private val data = MutableLiveData(posts)

    override fun likedById(id: Long) {
        data.value = data.value?.map {
            if (id == it.id) {
                it.like++
                it.copy(likedByMe = !it.likedByMe)
            } else {
                it
            }
        }
            data.value = data.value?.map {
                if (id == it.id) {
                    it.like--
                    it.copy(likedByMe = it.likedByMe)
                } else {
                    it
                }
            }
       }

    override fun share(id: Long) {
        data.value = data.value?.map {
            if (id == it.id) {
                it.shares++
                it.copy(shared = !it.shared)
            } else {
                it
            }
        }
    }



    override fun get(): LiveData<List<Post>> = data

}