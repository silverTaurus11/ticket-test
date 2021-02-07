package com.project.tiket_test.ui.main

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.tiket_test.R
import com.project.tiket_test.data.utils.Resource
import com.project.tiket_test.databinding.ActivityMainBinding
import com.project.tiket_test.ui.adapter.UserListAdapter
import com.project.tiket_test.ui.errorCodeTranslate
import com.project.tiket_test.ui.toVisible
import org.koin.android.viewmodel.ext.android.viewModel
import java.net.UnknownHostException

class MainActivity : AppCompatActivity() {

    private val homeViewModel: MainViewModel by viewModel()

    private val userListAdapter by lazy { UserListAdapter() }

    private lateinit var binding: ActivityMainBinding

    private var lastQuery = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initRecyclerView()
        initSearchEditor()
        initRefreshLayout()
    }

    private fun initRecyclerView(){
        binding.githubUsersRecyclerView.adapter = userListAdapter
        binding.githubUsersRecyclerView.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.VERTICAL, false)
    }

    private fun initSearchEditor(){
        binding.searchContainer.searchEditor.clearFocus()
        binding.searchContainer.searchEditor.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                doSearchUser()
                true
            } else false
        }
        binding.searchContainer.searchButton.setOnClickListener {
            doSearchUser()
        }
    }

    private fun hideSoftKeyboard(){
        val inputMethodManager: InputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        currentFocus?.windowToken?.apply {
            inputMethodManager.hideSoftInputFromWindow(this, 0)
        }
    }

    private fun doSearchUser(){
        val keyword = binding.searchContainer.searchEditor.text.toString()
        hideSoftKeyboard()
        searchGithubUserObserver(keyword)
    }

    private fun searchGithubUserObserver(query: String){
        lastQuery = query
        homeViewModel.searchUser(query).observe(this){
            userListAdapter.setNetworkState(it, resources)
            when(it){
                is Resource.Loading -> {
                    binding.loadingView.toVisible(true)
                    binding.githubUsersRecyclerView.toVisible(false)
                    binding.infoMessageView.toVisible(false)
                }
                is Resource.Success -> {
                    binding.loadingView.toVisible(false)
                    binding.githubUsersRecyclerView.toVisible(true)
                    binding.infoMessageView.toVisible(false)
                    userListAdapter.submitList(it.data)
                }
                is Resource.Empty -> {
                    if(!it.isLoadMoreBehaviour){
                        showErrorLayout(getString(R.string.error_data_not_found))
                    }
                }
                is Resource.Error -> {
                    if(it.throwable is UnknownHostException){
                        Toast.makeText(this, R.string.error_no_internet_connection_message, Toast.LENGTH_SHORT).show()
                        if(!it.isLoadMoreBehaviour)showErrorLayout(getString(R.string.error_no_internet_connection_message))
                    } else{
                        var message = it.message?:getString(R.string.error_cant_get_information)
                        errorCodeTranslate(resources, it.throwable).apply {
                            if(isNotEmpty()){
                                message = this
                            }
                        }
                        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                        if(!it.isLoadMoreBehaviour)showErrorLayout(message)
                    }
                }
                else -> return@observe
            }
        }
    }

    private fun showErrorLayout(message: String){
        binding.infoMessageView.toVisible(true)
        binding.infoMessageView.text = message
        binding.loadingView.toVisible(false)
    }

    private fun initRefreshLayout(){
        binding.swipeRefresh.setOnRefreshListener {
            if(lastQuery.isNotEmpty()){
                searchGithubUserObserver(lastQuery)
            }
            Handler(Looper.getMainLooper()).postDelayed({
                binding.swipeRefresh.isRefreshing = false
            }, 200)
        }
    }

}