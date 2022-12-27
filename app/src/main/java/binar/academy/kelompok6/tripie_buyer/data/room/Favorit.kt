package binar.academy.kelompok6.tripie_buyer.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity
data class Favorit(
    @PrimaryKey(autoGenerate = true) var id:Int,
    val airportCode: String,
    val airportName: String,
    val city: String,
    val foto: String,
    val description: String) : Serializable
