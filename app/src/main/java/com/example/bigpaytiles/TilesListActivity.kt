package com.example.bigpaytiles

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bigpaytiles.adapter.TilesListAdapter
import com.example.bigpaytiles.models.TilesRepo
import com.example.bigpaytiles.network.ServiceGenerator
import com.example.bigpaytiles.viewmodels.TilesListViewModel
import kotlinx.android.synthetic.main.activity_tiles_list.*



class TilesListActivity : BaseActivity() {

    companion object {
        val TAG = TilesListActivity::class.java.simpleName
    }

    private lateinit var tilesListViewModel: TilesListViewModel
    private lateinit var adapter: TilesListAdapter
    private lateinit var tilesRepoList: List<TilesRepo>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tiles_list)

        setSupportActionBar(findViewById(R.id.dashboard_toolbar))

        ServiceGenerator(this)

        adapter = TilesListAdapter(this, { item -> doClick(item) })
        rv_repo.adapter = adapter
        rv_repo.layoutManager = LinearLayoutManager(this)
        rv_repo.addItemDecoration(
            DividerItemDecoration(
                this,
                DividerItemDecoration.VERTICAL
            )
        )

        // Get the view model
        initialiseViewModel()

        fetchTilesRepos()



    }

    private fun initialiseViewModel() {
        tilesListViewModel =
            ViewModelProviders.of(this).get(TilesListViewModel(application)::class.java)
    }

    private fun  fetchTilesRepos() {
        showProgressBar(true)
        tilesListViewModel.getRepos().observe(this, Observer { repoList ->
            Log.i(TAG, "Viewmodel response: $repoList")

            repoList?.let {

                showProgressBar(false)
                if (it.isNotEmpty()) {
                    empty_list.visibility = View.GONE
                    tilesRepoList = it
                    adapter.clear()
                    adapter.setRepos(tilesRepoList.sortedByDescending { repo -> repo.Priority })
                } else {
                    empty_list.visibility = View.VISIBLE
                }
            }
        })
    }


    override fun onResume() {
        super.onResume()
        showNetworkMessage(hasNetwork())
    }

    override fun onPause() {

        super.onPause()
    }

    private fun showNetworkMessage(isConnected: Boolean) {
        if (!isConnected) {
            Toast.makeText(this, "Internet is not available", Toast.LENGTH_SHORT).show()
        }
    }

    fun doClick(id: String){
        showProgressBar(true)
        tilesListViewModel.getSelectionMsg(id).observe(this, Observer { selectionMsg ->
            Log.i(TAG, "Viewmodel response: $selectionMsg")

            selectionMsg?.let {

                showProgressBar(false)
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Selection Message")
                builder.setMessage(it.toString())

                builder.setPositiveButton(android.R.string.yes) { dialog, which ->
                   dialog.dismiss()
                }
                builder.show()

            }
        })
    }
}
