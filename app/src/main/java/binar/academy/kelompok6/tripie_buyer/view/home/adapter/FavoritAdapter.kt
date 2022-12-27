package binar.academy.kelompok6.tripie_buyer.view.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import binar.academy.kelompok6.tripie_buyer.data.model.response.Airport
import binar.academy.kelompok6.tripie_buyer.data.room.Favorit
import binar.academy.kelompok6.tripie_buyer.data.room.FavoritDatabase
import binar.academy.kelompok6.tripie_buyer.databinding.ItemAirportBinding
import binar.academy.kelompok6.tripie_buyer.databinding.ItemWishlistBinding
import com.bumptech.glide.Glide

class FavoritAdapter(private val onClick : FavoritInterface) : RecyclerView.Adapter<FavoritAdapter.ViewHolder>(){

    private val differCallback = object : DiffUtil.ItemCallback<Favorit>(){
        override fun areItemsTheSame(oldItem: Favorit, newItem: Favorit): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Favorit, newItem: Favorit): Boolean {
            return oldItem == newItem
        }

    }

    private val differ = AsyncListDiffer(this, differCallback)

    inner class ViewHolder(private val binding : ItemWishlistBinding) :
        RecyclerView.ViewHolder(binding.root){
        fun bind(favorit: Favorit){
            binding.txtNamaBandara.text = favorit.airportName
            binding.txtLokasiBandara.text = favorit.city
            Glide.with(this.itemView.context).load(favorit.foto).into(binding.imageView2)

            itemView.setOnClickListener {
                onClick.onItemClick(favorit)
            }
        }
    }

    interface FavoritInterface {
        fun onItemClick(favorit: Favorit)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemWishlistBinding.inflate(
            LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int = differ.currentList.size

    fun setData(data : List<Favorit>){
        differ.submitList(data)
    }
}