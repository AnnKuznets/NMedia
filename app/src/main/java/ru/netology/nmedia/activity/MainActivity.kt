package ru.netology.nmedia.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.*
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.util.AndroidUtils

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: PostViewModel by viewModels()

        val adapter = PostAdapter(
            object : PostActionListener {
                override fun edit(post: Post) {
                    viewModel.edit(post)
                }

                override fun like(post: Post) {
                    viewModel.likedById(post.id)
                }

                override fun remove(post: Post) {
                    viewModel.removeById(post.id)
                }

                override fun share(post: Post) {
                    val intent = Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(Intent.EXTRA_TEXT, post.content)
                        type = "text/plain"
                    }
                    val shareIntent =
                        Intent.createChooser(intent, getString(R.string.chooser_share_post))
                    startActivity(shareIntent)
                }
            })

            binding.container.adapter = adapter
            viewModel.get().observe(this, adapter::submitList)


    val newPostContract = registerForActivityResult(NewPostActivity.Contract()) { result ->
        result ?: return@registerForActivityResult
        viewModel.editContent(result)
        viewModel.save()
        }

        binding.add.setOnClickListener {
            newPostContract.launch("")
        }

        viewModel.edited.observe(this) { post ->
            if (post.id == 0L) {
                return@observe
            } else {
                newPostContract.launch(post.content.toString())
            }
        }
    }
}




