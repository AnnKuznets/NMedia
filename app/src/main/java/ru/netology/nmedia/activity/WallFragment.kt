package ru.netology.nmedia.activity

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.*
import ru.netology.nmedia.activity.NewPostFragment.Companion.longArg
import ru.netology.nmedia.activity.NewPostFragment.Companion.textArg
import ru.netology.nmedia.databinding.FragmentNewPostBinding
import ru.netology.nmedia.databinding.FragmentPostBinding

class WallFragment() : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentPostBinding.inflate(layoutInflater)
        val viewModel: PostViewModel by viewModels(::requireParentFragment)

        val postId = arguments?.longArg ?: -1
        viewModel.data.observe(viewLifecycleOwner) { posts ->
            val post = posts.find { it.id == postId } ?: return@observe
            with(binding.postFragment) {
                author.text = post.author
                content.text = post.content
                published.text = post.published
                likes?.text = post.like.toString()
                shares?.text = post.shares.toString()
                likes.isChecked = post.likedByMe
                shares.isChecked = post.shared
                video.isVisible = post.video != null

                likes.setOnClickListener {
                    viewModel.likedById(post.id)
                }
                shares.setOnClickListener {
                    viewModel.share(post.id)
                }
                menu.setOnClickListener {
                    PopupMenu(it.context,it).apply {
                        inflate(R.menu.posts_menu)
                        setOnMenuItemClickListener { item ->
                            when(item.itemId) {
                                R.id.remove -> {
                                    viewModel.removeById(post.id)
                                    findNavController().navigateUp()
                                    true
                                }
                                R.id.edit -> {
                                    viewModel.edit(post)
                                    findNavController().navigate(R.id.action_wallFragment_to_newPostFragment,
                                        Bundle().apply {textArg = post.content})
                                    true
                                }
                                else -> false
                            }
                        }
                        show()
                    }
                }
            }
        }
        return binding.root
    }
}