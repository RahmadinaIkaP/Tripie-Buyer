package binar.academy.kelompok6.tripie_buyer.data.model.response


import binar.academy.kelompok6.tripie_buyer.data.model.response.Airport
import com.google.gson.annotations.SerializedName

data class DataAirport(
    @SerializedName("airport")
    val airport: List<Airport>
)