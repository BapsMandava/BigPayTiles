package com.example.bigpaytiles.network

import com.example.bigpaytiles.models.TilesRepo
import io.reactivex.Observable


class TilesApiClient {

    fun getTilesRepos(): Observable<List<TilesRepo>> {
        return ServiceGenerator.instance.getTilesRepoApi().getTilesRepos()
    }

    fun getSelectionRepos(id:String): Observable<String> {
        return ServiceGenerator.instance.getTilesRepoApi().postSelectionID(id)
    }
}