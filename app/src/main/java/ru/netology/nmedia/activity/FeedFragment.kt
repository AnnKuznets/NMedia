package ru.netology.nmedia.activity

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.*
import ru.netology.nmedia.databinding.FragmentFeedBinding

class FeedFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentFeedBinding.inflate(layoutInflater)

        val viewModel: PostViewModel by viewModels(::requireParentFragment)

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


        val newPostContract = registerForActivityResult(NewPostFragment.Contract()) { result ->
            result ?: return@registerForActivityResult
            viewModel.editContent(result)
            viewModel.save()
        }

        binding.add.setOnClickListener {
            findNavController().navigate(R.id.action_feedFragment_to_newPostFragment)
        }

        viewModel.edited.observe(this) { post ->
            if (post.id == 0L) {
                return@observe
            } else {
                newPostContract.launch(post.content.toString())
            }
        }

        return binding.root
    }

}




