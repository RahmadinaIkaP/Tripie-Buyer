package binar.academy.kelompok6.tripie_buyer.view.home.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import binar.academy.kelompok6.tripie_buyer.data.network.ApiEndpoint
import binar.academy.kelompok6.tripie_buyer.data.room.Favorit
import binar.academy.kelompok6.tripie_buyer.data.room.FavoritDAO
import binar.academy.kelompok6.tripie_buyer.data.room.FavoritDatabase
import binar.academy.kelompok6.tripie_buyer.data.room.FavoritRepository
import dagger.hilt.android.internal.Contexts.getApplication
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritViewModel @Inject constructor(private val api : ApiEndpoint, private val repository: FavoritRepository) : ViewModel() {

    private var allFav : MutableLiveData<List<Favorit>> = MutableLiveData()
    private val liveDataFav : MutableLiveData<List<Favorit>> = MutableLiveData()

    fun cekFav(id : Int) = repository.cekFav(id)
    fun getAllFavObserver() : MutableLiveData<List<Favorit>> = liveDataFav

    private fun getAllFav() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllFav()
            liveDataFav.postValue(repository.getAllFav())
        }
    }

    fun insertFav(fav: Favorit){
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertFav(fav)
        }
    }

    fun deleteFav(fav: Favorit){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteFav(fav)
        }
    }

}