package binar.academy.kelompok6.tripie_buyer.view.histori.adapter

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.navigation.Navigation
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import binar.academy.kelompok6.tripie_buyer.R
import binar.academy.kelompok6.tripie_buyer.data.model.response.Booking
import binar.academy.kelompok6.tripie_buyer.databinding.ItemHistoriBinding
import binar.academy.kelompok6.tripie_buyer.view.histori.DetailHistoriFragment

class HistoryAdapter(private var listBooking : List<Booking>, private val onClick : HistoryInterface) : RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>()
    , Filterable {

    private var filteredBooking : List<Booking> = listBooking

    private val differCallback = object : DiffUtil.ItemCallback<Booking>(){
        override fun areItemsTheSame(oldItem: Booking, newItem: Booking): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Booking, newItem: Booking): Boolean {
            return oldItem == newItem
        }

    }

    private val differ = AsyncListDiffer(this, differCallback)

    fun filteredHistory(filter : List<Booking>){
        listBooking = filter
    }


    inner class HistoryViewHolder(val binding : ItemHistoriBinding)
        : RecyclerView.ViewHolder(binding.root) {
        fun bind(booking: Booking) {
            binding.apply {
                tvKodeBandaraAsal.text = booking.originCode
                tvKodeBandaraTujuan.text = booking.destinationCode
                tvKotaBandaraAsal.text = booking.originCity
                tvKotaBandaraTujuan.text = booking.destinationCity
                tvJamBerangkat.text = booking.departureHour
                tvJamPulang.text = booking.arrivalHour
                tvHargatiket.text = "IDR ${booking.price}"

                btnDetails.setOnClickListener {
                    onClick.onItemClick(booking)
                }
            }
        }
    }

    interface HistoryInterface {
        fun onItemClick(booking: Booking)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val view = ItemHistoriBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HistoryViewHolder(view)
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.bind(listBooking[position])
    }

    fun setData(data : List<Booking>) {
        differ.submitList(data)
    }

    override fun getFilter(): Filter {
        return object : Filter(){
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charString = constraint.toString()

                filteredBooking = if (charString.isEmpty()){
                    listBooking
                }else{
                    val filtered = listBooking
                        .filter {
                            it.destinationCode.lowercase().contains(charString.lowercase())
                        }
                    filtered
                }

                val result = Filter.FilterResults()
                result.values = filteredBooking
                return result
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredHistory(results?.values as List<Booking>)
                differ.submitList(filteredBooking)
            }

        }
    }
}