package com.pkdev.awigntask.adapter

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pkdev.awigntask.databinding.ListItemBinding
import com.pkdev.awigntask.models.Item
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class RepoAdapter(val list: List<Item>, val context: Context): RecyclerView.Adapter<RepoAdapter.RepoViewHolder>() {

    class RepoViewHolder(val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        val b = ListItemBinding.inflate(
            LayoutInflater.from(context),
            parent,
            false
        )
        return RepoViewHolder(b)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        holder.binding
        val viewHolder = holder.binding
        val currentItem = list[position]


        viewHolder.title.text = currentItem.name
        viewHolder.desc.text = currentItem.description
        viewHolder.stars.text = currentItem.stargazersCount.toString()
        viewHolder.watchersCount.text = currentItem.watcherCount.toString()
        viewHolder.scoreCount.text = currentItem.score.toString()

        val initialFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")
        val targetFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        var date = LocalDate.parse(currentItem.createdAt, initialFormat)
        val createDate: String = targetFormat.format(date)
        date = LocalDate.parse(currentItem.updatedAt, initialFormat)
        val updateDate: String = targetFormat.format(date)

        viewHolder.publishedAtText.text = createDate
        viewHolder.updatedAtText.text = updateDate
        viewHolder.languages.text = currentItem.language

        Glide.with(holder.itemView)
            .load(currentItem.owner.avatarUrl)
            .fitCenter()
            .into(viewHolder.avatarImg)

    }

    override fun getItemCount(): Int {
        return list.size
    }


}