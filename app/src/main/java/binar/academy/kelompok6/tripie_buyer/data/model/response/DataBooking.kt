package binar.academy.kelompok6.tripie_buyer.data.model.response


import com.google.gson.annotations.SerializedName

data class DataBooking(
    @SerializedName("Booking")
    val booking: List<Booking>
)