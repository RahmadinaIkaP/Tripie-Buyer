package binar.academy.kelompok6.tripie_buyer.data.model.response


import com.google.gson.annotations.SerializedName

data class DataError(
    @SerializedName("status")
    val status: String,
    @SerializedName("message")
    val message: String
)