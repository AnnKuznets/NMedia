package ru.netology.nmedia

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.databinding.CardPostBinding

interface PostActionListener{
    fun edit(post: Post)
    fun like(post: Post)
    fun remove(post: Post)
    fun share(post: Post)
    fun cancel(post: Post)

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
                likesCount?.text = post.like.toString()
                sharesCount?.text = post.shares.toString()

                likes?.setImageResource(
                    if (post.likedByMe) {
                        R.drawable.ic_liked
                    } else {
                        R.drawable.ic_like
                    }
                )
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
                                R.id.cancel -> {
                                    listener.cancel(post)
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