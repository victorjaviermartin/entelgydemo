package com.victormartin.ownpractice.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.victormartin.ownpractice.databinding.ItemHeroBinding
import com.victormartin.ownpractice.model.dao.MarvelHeroEntityModel
import com.victormartin.ownpractice.ui.main.viewholder.HeroViewHolder

/**
 * Adapter class [RecyclerView.Adapter] for [RecyclerView] which binds [Post] along with [HeroViewHolder]
 */
class HeroesListAdapter(private val onItemClickListener: OnItemClickListener) :
    ListAdapter<MarvelHeroEntityModel, HeroViewHolder>(DIFF_CALLBACK) {

    private val mHeroesList: MutableList<MarvelHeroEntityModel> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = createHeroViewHolder(parent)

    override fun getItemCount() = mHeroesList.size

    override fun onBindViewHolder(holder: HeroViewHolder, position: Int) =
        holder.bind(mHeroesList[position], onItemClickListener)

    private fun createHeroViewHolder(parent: ViewGroup) =
        HeroViewHolder(
            ItemHeroBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    fun setHeroes(postList: List<MarvelHeroEntityModel>) {
        clearAllHeroes()
        mHeroesList.addAll(postList)
        notifyDataSetChanged()
    }

    fun clearAllHeroes() {
        mHeroesList.clear()
    }

    interface OnItemClickListener {
        fun onItemClicked(heroModel: MarvelHeroEntityModel)
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MarvelHeroEntityModel>() {
            override fun areItemsTheSame(oldItem: MarvelHeroEntityModel, newItem: MarvelHeroEntityModel): Boolean =
                oldItem.name.equals(newItem.name)

            override fun areContentsTheSame(oldItem: MarvelHeroEntityModel, newItem: MarvelHeroEntityModel): Boolean =
                oldItem == newItem

        }
    }
}
