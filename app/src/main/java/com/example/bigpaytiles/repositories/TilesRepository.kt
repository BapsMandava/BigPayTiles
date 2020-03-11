package com.example.bigpaytiles.repositories

import android.content.Context
import com.example.bigpaytiles.models.TilesRepo
import com.example.bigpaytiles.network.TilesApiClient
import com.example.bigpaytiles.util.ConnectivityUtil

import io.reactivex.Observable


class TilesRepository(private val context: Context) {


    // The ViewModel maintains a reference to the repository to get data.
    private var tilesRepoApiClient: TilesApiClient = TilesApiClient()

    fun getAllTilesRepos(): Observable<List<TilesRepo>> {
        val hasConnection = ConnectivityUtil.isNetworkAvailable(context)
        var observableFromApi: Observable<List<TilesRepo>>? = null
        if (hasConnection) {
            observableFromApi = getTilesReposFromApi()
        }

        return Observable.concatArrayEager(observableFromApi)
    }

    fun getSelectionMessage(id:String): Observable<String>? {
        val hasConnection = ConnectivityUtil.isNetworkAvailable(context)
        var observableFromApi: Observable<String>? = null
        if (hasConnection) {
            observableFromApi = getTilesSelectionIdFromApi(id)
        }

        return observableFromApi
    }

    private fun getTilesReposFromApi(): Observable<List<TilesRepo>> {
        return tilesRepoApiClient.getTilesRepos()
    }

    private fun getTilesSelectionIdFromApi(id:String): Observable<String> {
        return tilesRepoApiClient.getSelectionRepos(id)
    }

}