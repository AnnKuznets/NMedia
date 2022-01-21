package ru.netology.nmedia

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.databinding.CardPostBinding
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
                    viewModel.share(post.id)
                }
            }

        )

        binding.container.adapter = adapter
        viewModel.get().observe(this, adapter::submitList)


        with(binding) {
            save.setOnClickListener {
                val text = content.text?.toString()
                if (text.isNullOrBlank()) {
                    Toast.makeText(
                        this@MainActivity,
                        getString(R.string.blank_content_error),
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }
                viewModel.editContent(text)
                viewModel.save()
                groupSave.visibility = View.VISIBLE


                content.setText("")
                content.clearFocus()
                AndroidUtils.hydeKeyBoard(content)
            }

            viewModel.edited.observe(this@MainActivity) {
                if (it.id != 0L) {
                    group.visibility = View.VISIBLE
                    cancel.setOnClickListener {
                        viewModel.cancel()
                        return@setOnClickListener
                    }
                        editContent.setText(it.content)

                        content.requestFocus()
                        content.setText(it.content)
                    return@observe

                } else {
                    group.visibility = View.GONE
                    return@observe
                }
            }
        }
    }
}




