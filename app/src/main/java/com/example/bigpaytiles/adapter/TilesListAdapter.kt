package com.example.bigpaytiles.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

import com.bumptech.glide.Glide
import com.example.bigpaytiles.R
import com.example.bigpaytiles.models.TilesRepo
import com.example.bigpaytiles.viewmodels.TilesListViewModel


class TilesListAdapter internal constructor(context: Context, val adapterOnClick : (String) -> Unit) : RecyclerView.Adapter<TilesListAdapter.RepoViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var repos = emptyList<TilesRepo>()

    inner class RepoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val id: TextView = itemView.findViewById(R.id.item_id)
        val label: TextView = itemView.findViewById(R.id.item_label)
        val priority: TextView = itemView.findViewById(R.id.item_priority)
        val container: LinearLayout = itemView.findViewById(R.id.container)
        //set our click method here
        fun setItem(item: String) {
            container.setOnClickListener { adapterOnClick(item) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        val itemView = inflater.inflate(R.layout.layout_tileslist_item, parent, false)
        return RepoViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        val current = repos[position]
        holder.id.text = "Id = " + current.Id
        holder.label.text = "Label = " + current.Label
        holder.priority.text = "Priority = " + current.Priority
        holder.setItem(current.Id)
    }

    internal fun setRepos(repos: List<TilesRepo>) {
        this.repos = repos
        notifyDataSetChanged()
    }

    override fun getItemCount() = repos.size

    fun clear() {
        repos = emptyList()
        notifyDataSetChanged()
    }
}