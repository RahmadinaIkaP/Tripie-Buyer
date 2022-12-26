package binar.academy.kelompok6.tripie_buyer.data.model.request


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class SearchTicketRequest(
    @SerializedName("destination_name")
    val destinationName: String,
    @SerializedName("flight_date")
    val flightDate: String,
    @SerializedName("origin_name")
    val originName: String,
    @SerializedName("plane_class")
    val planeClass: String,
    @SerializedName("total_passenger")
    val totalPassenger: Int
) : Serializable