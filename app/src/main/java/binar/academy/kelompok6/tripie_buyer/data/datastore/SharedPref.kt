package binar.academy.kelompok6.tripie_buyer.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore : DataStore<Preferences> by preferencesDataStore(name = "user")

class SharedPref (private val context: Context) {

    private val token = stringPreferencesKey("token")
    private val status = booleanPreferencesKey("status")
    private val idUser = stringPreferencesKey("idUser")

    suspend fun saveToken(tokens : String,idUsers : String){
        context.dataStore.edit {
            it[token] = tokens
            it[idUser] = idUsers
        }
    }

    val getToken : Flow<String> = context.dataStore.data
        .map {
            it[token] ?: "Undefined"
        }
    val getIdUser : Flow<String> = context.dataStore.data
        .map {
            it[idUser] ?: "Undefined"
        }

    suspend fun removeToken(){
        context.dataStore.edit {
            it.clear()
        }
    }

    suspend fun saveStatus(statuss:Boolean){
        context.dataStore.edit {
            it[status] = statuss
        }
    }

    val getStatus : Flow<Boolean> = context.dataStore.data
        .map {
            it[status] ?: false
        }
}