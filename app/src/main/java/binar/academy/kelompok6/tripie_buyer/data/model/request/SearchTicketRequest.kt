package binar.academy.kelompok6.tripie_buyer.data.model.request

import com.google.gson.annotations.SerializedName

data class SearchTicketRequest(
    @SerializedName("Origin_Airport")
    val origin: String,
    @SerializedName("Destination_Airport")
    val destinasi: String,
    @SerializedName("Plane_class")
    val planeClass: String,
    @SerializedName("flight_Date")
    val flightDate:String
)
