package ru.netology.nmedia

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.databinding.CardPostBinding

class PostAdapter(
    private val onLikeListener: (Post) -> Unit,
    private val onShareListener: (Post) -> Unit
) : ListAdapter<Post, PostAdapter.PostViewHolder>(PostDiffItemCallback()) {
    var posts: List<Post> = emptyList()
        set(value) {
            notifyDataSetChanged()
            field = value
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = CardPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding, onLikeListener, onShareListener)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
    class PostViewHolder(
        private val binding: CardPostBinding,
        private val onLikeListener: (Post) -> Unit,
        private val onShareListener: (Post) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(post: Post) {
            with(binding) {
                author.text = post.author
                content.text = post.content
                published.text = post.published
                likesCount?.text = post.like.toString()
                sharesCount?.text = post.share.toString()
                viewCount?.text = post.view.toString()

                likes?.setImageResource(
                    if (post.likedByMe) {
                        R.drawable.ic_liked
                    } else {
                        R.drawable.ic_like
                    }
                )
                likes.setOnClickListener {
                    onLikeListener(post)
                }
                shares.setOnClickListener {
                    onShareListener(post)
                }

                likesCount?.text = post.like.toString()
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