package binar.academy.kelompok6.tripie_buyer.view.histori.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import binar.academy.kelompok6.tripie_buyer.data.model.response.Booking
import binar.academy.kelompok6.tripie_buyer.databinding.ItemHistoriBinding

class HistoryAdapter(var listHistory: List<Booking>) : RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    class HistoryViewHolder(val binding : ItemHistoriBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(booking: Booking) {
            binding.tvBandaraAsal.text = booking.originAirport
            binding.tvBandaraTujuan.text = booking.destinationAirport
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val view = ItemHistoriBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HistoryViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listHistory.size
    }

    fun setData(data : List<Booking>) {
        this.listHistory = data
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.binding.tvBandaraAsal.text = listHistory[position].originAirport
        holder.binding.tvBandaraTujuan.text = listHistory[position].destinationAirport
    }
}