package ru.netology.nmedia



import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.core.content.ContextCompat.startActivity
import androidx.core.view.isVisible
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.activity.NewPostFragment.Companion.longArg
import ru.netology.nmedia.databinding.CardPostBinding

interface PostActionListener{
    fun edit(post: Post)
    fun like(post: Post)
    fun remove(post: Post)
    fun share(post: Post)
}

class PostAdapter(
    private val listener: PostActionListener
) : ListAdapter<Post, PostAdapter.PostViewHolder>(PostDiffItemCallback()) {
    var posts: List<Post> = emptyList()
        set(value) {
            notifyDataSetChanged()
            field = value
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = CardPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
    class PostViewHolder(
        private val binding: CardPostBinding,
        private val listener: PostActionListener
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(post: Post) {
            with(binding) {
                author.text = post.author
                content.text = post.content
                published.text = post.published
                likes?.text = post.like.toString()
                shares?.text = post.shares.toString()
                likes.isChecked = post.likedByMe
                shares.isChecked = post.shared
                video.isVisible = post.video != null


                likes.setOnClickListener {
                    listener.like(post)
                }
                menu.setOnClickListener {
                    PopupMenu(it.context,it).apply {
                        inflate(R.menu.posts_menu)
                        setOnMenuItemClickListener { item ->
                            when(item.itemId) {
                                R.id.remove -> {
                                    listener.remove(post)
                                    true
                                }
                                R.id.edit -> {
                                    listener.edit(post)
                                    true
                                }
                                else -> false
                            }
                        }
                        show()
                    }
                }
                shares.setOnClickListener {
                    listener.share(post)
                }

                video.setOnClickListener {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(post.video.toString()))
                    startActivity(it.context, intent, Bundle())
                }
                content.setOnClickListener {
                    findNavController().navigate(R.id.action_feedFragment_to_wallFragment)
                }
            }
        }
    }

    class PostDiffItemCallback : DiffUtil.ItemCallback<Post>() {
        override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean =
            oldItem == newItem
    }
}