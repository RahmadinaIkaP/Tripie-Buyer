package binar.academy.kelompok6.tripie_buyer.data.model.response


import com.google.gson.annotations.SerializedName

data class DataUpdateUser(
    @SerializedName("Name")
    val name: String,
    @SerializedName("Email")
    val email: String,
    @SerializedName("Encrypted_Password")
    val encryptedPassword: String,
    @SerializedName("Foto")
    val foto: String,
    @SerializedName("Address")
    val address: String,
    @SerializedName("Phone_Number")
    val phoneNumber: String
)