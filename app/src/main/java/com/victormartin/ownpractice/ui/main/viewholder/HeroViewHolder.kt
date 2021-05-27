package com.victormartin.ownpractice.ui.main.viewholder

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.victormartin.ownpractice.databinding.ItemHeroBinding
import com.victormartin.ownpractice.model.dao.MarvelHeroEntityModel
import com.victormartin.ownpractice.ui.main.adapter.HeroesListAdapter

/**
 * [RecyclerView.ViewHolder] implementation to inflate View for RecyclerView.
 * See [HeroesListAdapter]]
 */
class HeroViewHolder(private val binding: ItemHeroBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(heroModel: MarvelHeroEntityModel, onItemClickListener: HeroesListAdapter.OnItemClickListener? = null) {
        binding.heroTitle.text = heroModel.name
        binding.heroImage.load(heroModel.photoUrl) {
            crossfade(true)
            placeholder(android.R.drawable.ic_popup_sync)
        }

        onItemClickListener?.let { listener ->
            binding.root.setOnClickListener {
                listener.onItemClicked(heroModel)
            }
        }
    }
}
