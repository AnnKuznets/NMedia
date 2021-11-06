package ru.netology.nmedia

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val post = Post(
            id = 1,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
            published = "21 мая в 18:36",
        )

        with(binding) {
            author.text = post.author
            content.text = post.content
            published.text = post.published
            likesCount?.text = post.like.toString()
            sharesCount?.text = post.share.toString()
            viewCount?.text = post.view.toString()

            if (post.likedByMe) {
                likes?.setImageResource(R.drawable.ic_liked)
            }

            likes.setOnClickListener {
                post.likedByMe = !post.likedByMe
                likes.setImageResource(if (post.likedByMe) R.drawable.ic_liked else R.drawable.ic_like)
                if (post.likedByMe) post.like++ else post.like--
                likesCount?.text = post.like.toString()
            }
            shares.setOnClickListener {
                post.share++
                sharesCount?.text = post.share.toString()
            }
            view.setOnClickListener {
                post.view++
                viewCount?.text = post.view.toString()
            }
        }
    }
}