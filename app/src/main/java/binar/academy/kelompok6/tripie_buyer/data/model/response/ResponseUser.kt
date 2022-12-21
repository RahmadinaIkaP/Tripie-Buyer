package binar.academy.kelompok6.tripie_buyer.data.model.response


import com.google.gson.annotations.SerializedName

data class ResponseUser(
    @SerializedName("data")
    val data: Data
)