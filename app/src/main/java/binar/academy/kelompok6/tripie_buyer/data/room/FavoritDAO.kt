package binar.academy.kelompok6.tripie_buyer.data.room

import androidx.room.*
import dagger.Module
import dagger.Provides

@Dao @Module
interface FavoritDAO {
    @Provides
    @Query("SELECT * FROM Favorit ORDER BY id DESC")
    fun getData() : List<Favorit>

    @Provides
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertFav(notes : Favorit)

    @Delete
    fun deleteFav(notes: Favorit)

    @Provides
    @Query("SELECT count(*) FROM Favorit WHERE id =:id")
    fun checkFav(id : Int) : Int

}

