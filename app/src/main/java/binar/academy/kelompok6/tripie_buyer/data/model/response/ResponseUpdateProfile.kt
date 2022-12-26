package binar.academy.kelompok6.tripie_buyer.data.model.response


import com.google.gson.annotations.SerializedName

data class ResponseUpdateProfile(
    @SerializedName("data")
    val data: List<Int>,
    @SerializedName("status")
    val status: String
)