package binar.academy.kelompok6.tripie_buyer.view.home.adapter

import android.app.Application
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import binar.academy.kelompok6.tripie_buyer.R
import binar.academy.kelompok6.tripie_buyer.data.datastore.SharedPref
import binar.academy.kelompok6.tripie_buyer.data.model.response.Airport
import binar.academy.kelompok6.tripie_buyer.data.room.Favorit
import binar.academy.kelompok6.tripie_buyer.data.room.FavoritDAO
import binar.academy.kelompok6.tripie_buyer.data.room.FavoritDatabase
import binar.academy.kelompok6.tripie_buyer.databinding.ItemAirportBinding
import binar.academy.kelompok6.tripie_buyer.databinding.ItemDestinasiPopularBinding
import binar.academy.kelompok6.tripie_buyer.view.home.viewmodel.FavoritViewModel
import binar.academy.kelompok6.tripie_buyer.view.whistlist.WhistlistFragment
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.*

class PopularDestinationAdapter (private val onClick : PopularInterface, private val vmFav: FavoritViewModel, private val context: Context) : RecyclerView.Adapter<PopularDestinationAdapter.ViewHolder>(){

    private val differCallback = object : DiffUtil.ItemCallback<Airport>(){
        override fun areItemsTheSame(oldItem: Airport, newItem: Airport): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Airport, newItem: Airport): Boolean {
            return oldItem == newItem
        }

    }

    private val differ = AsyncListDiffer(this, differCallback)

    inner class ViewHolder(val binding : ItemDestinasiPopularBinding, private val vmFav: FavoritViewModel) : RecyclerView.ViewHolder(binding.root){
        fun bind(airport: Airport){
            binding.txtNamaBandara.text = airport.airportName
            binding.txtLokasiBandara.text = airport.city

            itemView.setOnClickListener {
                onClick.onItemClick(airport)
            }
        }
    }

    interface PopularInterface {
        fun onItemClick(airport: Airport)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemDestinasiPopularBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view, vmFav)
    }

    private var dbFav : FavoritDatabase? = null
    private var isClicked = false
    lateinit var sharedPref: SharedPref

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(differ.currentList[position])

        sharedPref = SharedPref(context)

        GlobalScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main){
                val count = vmFav.cekFav(differ.currentList[position].id)
                isClicked = if (count > 0){
                    holder.binding.btnFavorit.setImageResource(R.drawable.ic_favorite)
                    true
                }else{
                    holder.binding.btnFavorit.setImageResource(R.drawable.ic_unfavorite)
                    false
                }
            }
        }

        holder.binding.btnFavorit.setOnClickListener {
            dbFav = FavoritDatabase.getInstance(it.context)

            var data = Favorit(id = 0,
                airportCode = differ.currentList[position].airportCode,
                airportName = differ.currentList[position].airportName,
                city = differ.currentList[position].city,
                description = differ.currentList[position].description,
                foto = differ.currentList[position].foto)

            isClicked = !isClicked

            if (sharedPref.getIdUser != null){
                vmFav.insertFav(data)
                holder.binding.btnFavorit.setImageResource(R.drawable.ic_favorite)
            }

            else{
                vmFav.deleteFav(data)
                holder.binding.btnFavorit.setImageResource(R.drawable.ic_unfavorite)
            }
        }
    }

    override fun getItemCount(): Int = differ.currentList.size

    fun setData(data : List<Airport>){
        differ.submitList(data)
    }
}
