package com.example.firebaseimageandtextsample

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.firebaseimageandtextsample.data.Post
import com.example.firebaseimageandtextsample.databinding.ProfileItemBinding

class ItemListAdapter : ListAdapter<Post, ItemListAdapter.ItemViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemViewHolder {
        return ItemViewHolder(
            ProfileItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current)
    }


    class ItemViewHolder(private val binding: ProfileItemBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(post: Post) {
            //binding.author.text = post.author.toString()
            //binding.time.text = post.createTime.toString()
            binding.title.text = post.title
            binding.body.text = post.body
            post.image?.getBytes(1024 * 1024)
                ?.addOnSuccessListener { imageData ->
                    val bitmap = BitmapFactory.decodeByteArray(imageData, 0, imageData.size)
                    binding.image.setImageBitmap(bitmap)
                }
            binding.likeCount.text = post.likeCount.toString()

        }

            /*
            val MAX_SIZE_BYTES: Long = 1024 * 1024
            profile.Image?.getBytes(MAX_SIZE_BYTES)
                ?.addOnSuccessListener { imageData ->
                    val bitmap = BitmapFactory.decodeByteArray(imageData, 0, imageData.size)
                    binding.userImage.setImageBitmap(bitmap).toString()
                    }

             */
        }

    companion object {
        private val DiffCallback = object: DiffUtil.ItemCallback<Post>() {
            override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
                return oldItem.title == newItem.title
            }
        }
    }
}