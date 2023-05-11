package com.pkdev.awigntask.ui

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.PopupMenu
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.pkdev.awigntask.RepoApplication
import com.pkdev.awigntask.adapter.RepoAdapter
import com.pkdev.awigntask.databinding.ActivityMainBinding
import com.pkdev.awigntask.models.Item
import com.pkdev.awigntask.viewmodels.RepoViewModel
import com.pkdev.awigntask.viewmodels.providers.RepoViewModelProvider
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import javax.inject.Inject


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    lateinit var viewModel: RepoViewModel

    @Inject
    lateinit var viewModelProvider: RepoViewModelProvider

    private lateinit var repoList: List<Item>

    companion object {
        var isSearched = false
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        (application as RepoApplication).applicationComponent.inject(this)

        viewModel = ViewModelProvider(this, viewModelProvider).get(RepoViewModel::class.java)

        viewModel.repoLiveData.observe(this) { list ->
            if (list.isNotEmpty())
                isSearched = true
            repoList = list
            binding.repoRv.apply {
                if(list.isNotEmpty())
                    switchVisibility("RV")
                else
                    switchVisibility("ND")
                layoutManager = GridLayoutManager(this@MainActivity, 1)
                adapter = RepoAdapter(list, this@MainActivity)
            }

        }

        binding.search.setOnClickListener {
            hideKeyBoard(it)
            if(isOnline(this)) {
                switchVisibility("PB")
                viewModel.getRepoList(binding.searchEdittext.text.toString())
            }
            else{
                Toast.makeText(this, "No Internet", Toast.LENGTH_SHORT).show()
            }
        }

        binding.sortBy.setOnClickListener {
            if (isSearched)
                showPopup(it)
        }
        binding.clear.setOnClickListener {
            hideKeyBoard(it)
            binding.searchEdittext.setText("")
        }

    }

    private fun showPopup(v: View) {
        val popup = PopupMenu(this, v)
        popup.apply {
            menuInflater.inflate(com.pkdev.awigntask.R.menu.sort_menu, popup.menu)
            setOnMenuItemClickListener(object : PopupMenu.OnMenuItemClickListener {
                @RequiresApi(Build.VERSION_CODES.O)
                override fun onMenuItemClick(item: MenuItem?): Boolean {
                    return when (item?.itemId) {
                        com.pkdev.awigntask.R.id.stars -> {
                            Collections.sort(repoList) { r1, r2 ->
                                r1.stargazersCount.compareTo(r2.stargazersCount)
                            }
                            setRecyclerView(repoList)
                            return true
                        }
                        com.pkdev.awigntask.R.id.watcher -> {
                            Collections.sort(repoList) { r1, r2 ->
                                r1.watcherCount.compareTo(r2.watcherCount)
                            }
                            setRecyclerView(repoList)
                            return true
                        }
                        com.pkdev.awigntask.R.id.score -> {
                            Collections.sort(repoList) { r1, r2 ->
                                r1.score.compareTo(r2.score)
                            }
                            setRecyclerView(repoList)
                            return true
                        }
                        com.pkdev.awigntask.R.id.name -> {
                            Collections.sort(repoList) { r1, r2 ->
                                r1.name.compareTo(r2.name)
                            }
                            setRecyclerView(repoList)
                            return true
                        }
                        com.pkdev.awigntask.R.id.createdAt -> {

                            Collections.sort(repoList) { r1, r2 ->
                                val initialFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")
                                val date1 = LocalDate.parse(r1.createdAt, initialFormat)
                                val date2 = LocalDate.parse(r2.createdAt, initialFormat)
                                date1.compareTo(date2)
                            }
                            setRecyclerView(repoList)
                            return true
                        }
                        com.pkdev.awigntask.R.id.updatedAt -> {
                            Collections.sort(repoList) { r1, r2 ->
                                val initialFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")
                                val date1 = LocalDate.parse(r1.updatedAt, initialFormat)
                                val date2 = LocalDate.parse(r2.updatedAt, initialFormat)
                                date1.compareTo(date2)
                            }
                            setRecyclerView(repoList)
                            return true
                        }
                        else ->
                            return false
                    }
                }
            })
        }
        popup.show()
    }

    private fun hideKeyBoard(view: View) {
        val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun setRecyclerView(list: List<Item>) {
        binding.repoRv.apply {
            adapter = RepoAdapter(list, this@MainActivity)
        }
    }

    private fun switchVisibility(view: String){
        when(view) {
            "RV" -> {
                binding.repoRv.visibility = View.VISIBLE
                binding.progressBar.visibility = View.GONE
                binding.noData.visibility = View.GONE
            }
            "PB" -> {
                binding.repoRv.visibility = View.GONE
                binding.progressBar.visibility = View.VISIBLE
                binding.noData.visibility = View.GONE
            }
            "ND" -> {
                binding.repoRv.visibility = View.GONE
                binding.progressBar.visibility = View.GONE
                binding.noData.visibility = View.VISIBLE
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun isOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager != null) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                    return true
                }
            }
        }
        return false
    }


}