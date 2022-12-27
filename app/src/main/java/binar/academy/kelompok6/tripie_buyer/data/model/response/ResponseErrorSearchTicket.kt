package binar.academy.kelompok6.tripie_buyer.data.model.response


import com.google.gson.annotations.SerializedName

data class ResponseErrorSearchTicket(
    @SerializedName("data")
    val data : DataError
)