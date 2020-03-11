package com.example.bigpaytiles.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.bigpaytiles.models.TilesRepo
import com.example.bigpaytiles.repositories.TilesRepository
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


// holding, retrieving and displaying Repo's
class TilesListViewModel(application: Application) : AndroidViewModel(application) {

    // The ViewModel maintains a reference to the repository to get data.
    private val repository: TilesRepository
    var tiles: MutableLiveData<List<TilesRepo>> = MutableLiveData()
    var selectionMessage: MutableLiveData<String> = MutableLiveData()
    private val mDisposables = CompositeDisposable()

    init {
        repository = TilesRepository(application.baseContext)
    }

    fun getRepos(): LiveData<List<TilesRepo>> {
        val observable: Observable<List<TilesRepo>> = repository.getAllTilesRepos()
        val result = observable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                tiles.postValue(result)
            }, { error ->
                error.printStackTrace()
            }, {
                //completed
            })
        mDisposables.add(result)
        return tiles
    }

    fun getSelectionMsg(id:String): LiveData<String> {
        val observable: Observable<String>? = repository.getSelectionMessage(id)
        val result = observable?.subscribeOn(Schedulers.io())?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe({ result ->
                selectionMessage.postValue(result)
            }, { error ->
                error.printStackTrace()
            }, {
                //completed
            })
        result?.let { mDisposables.add(it) }
        return selectionMessage
    }

    override fun onCleared() {
        super.onCleared()
        mDisposables.clear() //no more leaks. It takes care of lifecycle for you
    }
}