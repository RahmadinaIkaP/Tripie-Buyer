package binar.academy.kelompok6.tripie_buyer.data.model.response

import com.google.gson.annotations.SerializedName

data class ResponseSearchTicket(
    @SerializedName("data")
    val data: List<DataSearch>,
    @SerializedName("status")
    val status: String
)
