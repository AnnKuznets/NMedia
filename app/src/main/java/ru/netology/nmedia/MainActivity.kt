package ru.netology.nmedia

import android.os.Bundle
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: PostViewModel by viewModels()

        with(binding) {
            viewModel.get().observe(this@MainActivity) {post ->
                author.text = post.author
                content.text = post.content
                published.text = post.published
                likesCount?.text = post.like.toString()
                sharesCount?.text = post.share.toString()
                viewCount?.text = post.view.toString()

                likes?.setImageResource(if (post.likedByMe) {
                        R.drawable.ic_liked
                } else {
                R.drawable.ic_like
            })
                likesCount?.text = post.like.toString()

                sharesCount?.text = post.share.toString()

                viewCount?.text = post.view.toString()
            }

            likes.setOnClickListener {
                viewModel.like()
            }
            shares.setOnClickListener {
                viewModel.share()
            }
            view.setOnClickListener {
                viewModel.view()
            }
        }
    }
}