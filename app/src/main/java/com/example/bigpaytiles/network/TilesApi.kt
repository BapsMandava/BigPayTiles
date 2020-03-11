package com.example.bigpaytiles.network

import com.example.bigpaytiles.models.TilesRepo
import com.example.bigpaytiles.util.Constants
import io.reactivex.Observable
import android.R.id
import retrofit2.http.*


interface TilesApi {
    @GET(value = Constants.GET_TILES)
    fun getTilesRepos(): Observable<List<TilesRepo>>

    @POST(value = Constants.SELECTION_ID)
    fun postSelectionID(@Query("id", encoded = true) id: String): Observable<String>
}