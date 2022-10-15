package com.swapnildroid.pet.support.ui.main.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.swapnildroid.pet.support.databinding.PetRowItemLayout
import com.swapnildroid.pet.support.ui.main.interaction.PetActionInterface
import com.swapnildroid.pet.support.ui.main.models.PetDataModel
import com.swapnildroid.pet.support.ui.main.network.ImageCache

class PetListRecyclerAdapter(
    private val layoutInflater: LayoutInflater,
    private val list: List<PetDataModel?>?,
    private val petActionInterface: PetActionInterface?
) : RecyclerView.Adapter<PetListRecyclerAdapter.PetViewHolder>() {

    inner class PetViewHolder(private val petRowItemLayout: PetRowItemLayout) :
        RecyclerView.ViewHolder(petRowItemLayout.root) {

        fun bind(petDataModel: PetDataModel?) {
            petRowItemLayout.userInteraction = petActionInterface
            petRowItemLayout.pet = petDataModel
            ImageCache.loadBitmap(
                petDataModel?.imageUrl,
                petRowItemLayout.petImage
            )
        }

    }

    override fun getItemCount(): Int = list?.size ?: 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PetListRecyclerAdapter.PetViewHolder =
        PetViewHolder(PetRowItemLayout.inflate(layoutInflater, parent, false))

    override fun onBindViewHolder(holder: PetListRecyclerAdapter.PetViewHolder, position: Int) =
        holder.bind(list?.get(position))

}