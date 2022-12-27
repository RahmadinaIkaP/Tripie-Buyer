package binar.academy.kelompok6.tripie_buyer.view.histori.adapter

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import binar.academy.kelompok6.tripie_buyer.R
import binar.academy.kelompok6.tripie_buyer.data.model.response.Booking
import binar.academy.kelompok6.tripie_buyer.databinding.ItemHistoriBinding
import binar.academy.kelompok6.tripie_buyer.view.histori.DetailHistoriFragment

class HistoryAdapter(var listHistory: List<Booking>) : RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    class HistoryViewHolder(val binding : ItemHistoriBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(booking: Booking) {
            binding.tvKodeBandaraAsal.text = booking.originAirport
            binding.tvKodeBandaraTujuan.text = booking.destinationAirport
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
        holder.binding.tvKodeBandaraAsal.text = listHistory[position].originAirport
        holder.binding.tvKodeBandaraTujuan.text = listHistory[position].destinationAirport

        holder.binding.btnDetails.setOnClickListener {
            val bundle = Bundle()
            val intent = Intent(holder.itemView.context, DetailHistoriFragment::class.java)
            bundle.putString("originAirport", listHistory[position].originAirport)
            bundle.putString("destinationAirport", listHistory[position].destinationAirport)
            bundle.putInt("id", listHistory[position].id)
            Navigation.findNavController(it).navigate(R.id.action_historiFragment_to_detailHistoriFragment, bundle)
        }
    }
}