package binar.academy.kelompok6.tripie_buyer.data.room

import javax.inject.Inject

class FavoritRepository @Inject constructor(private val favDAO: FavoritDAO){

    fun getAllFav() : List<Favorit> = favDAO.getData()

    fun cekFav(id : Int) = favDAO.checkFav(id)

    fun insertFav(favorit: Favorit) = favDAO.insertFav(favorit)

    fun deleteFav(favorit: Favorit) = favDAO.deleteFav(favorit)
}