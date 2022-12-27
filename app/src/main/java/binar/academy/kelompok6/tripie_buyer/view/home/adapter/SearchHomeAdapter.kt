package binar.academy.kelompok6.tripie_buyer.view.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import binar.academy.kelompok6.tripie_buyer.data.model.response.DataSearch
import binar.academy.kelompok6.tripie_buyer.databinding.ItemHistoriBinding

class SearchHomeAdapter(private val onClick: HasilSearchInterface) : RecyclerView.Adapter<SearchHomeAdapter.ViewHolder>() {

    interface HasilSearchInterface{
        fun onItemClick(dataHome: DataSearch)
    }

    private val differCallback = object : DiffUtil.ItemCallback<DataSearch>(){
        override fun areItemsTheSame(oldItem: DataSearch, newItem: DataSearch): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: DataSearch, newItem: DataSearch): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, differCallback)

    inner class ViewHolder(var binding:ItemHistoriBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(dataHome: DataSearch){
//            binding.destinasiData = dataHome

            binding.apply {
                btnDetails.text = "Pilih"

                tvKodeBandaraAsal.text = dataHome.originCode
                tvKodeBandaraTujuan.text = dataHome.destinationCode
                tvKotaBandaraAsal.text = dataHome.originCity
                tvKotaBandaraTujuan.text = dataHome.destinationCity
                tvJamBerangkat.text = dataHome.departureHour
                tvJamPulang.text = dataHome.arrivalHour
                tvHargatiket.text = "IDR ${dataHome.price}"

                btnDetails.setOnClickListener {
                    onClick.onItemClick(dataHome)
                }
            }


        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemHistoriBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int = differ.currentList.size

    fun setData(data : List<DataSearch>){
        differ.submitList(data)
    }
}
