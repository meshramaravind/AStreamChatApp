package com.arvind.astreamchatapp.view.users

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.widget.SearchView
import com.arvind.astreamchatapp.R
import com.arvind.astreamchatapp.adapter.UsersAdapter
import com.arvind.astreamchatapp.databinding.FragmentUsersBinding
import com.arvind.astreamchatapp.view.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.client.api.models.QueryUsersRequest
import io.getstream.chat.android.client.models.Filters
import io.getstream.chat.android.client.models.User

@AndroidEntryPoint
class UsersFragment : BaseFragment<FragmentUsersBinding>() {

    private val client = ChatClient.instance()
    private val usersAdapter by lazy { UsersAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()

    }

    private fun initView()= with(binding) {
        setupToolbar(binding)
        setupRecyclerView(binding)
        queryAllUsers()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.users_menu, menu)
        val search = menu.findItem(R.id.menu_search)
        val searchView = search.actionView as? SearchView
        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(query: String?): Boolean {
                if (query!!.isEmpty()) {
                    queryAllUsers()
                } else {
                    searchUser(query)
                }
                return true
            }
        })
        searchView?.setOnCloseListener {
            queryAllUsers()
            false
        }
    }

    private fun searchUser(query: String) {
        val filters = Filters.and(
            Filters.autocomplete("id", query),
            Filters.ne("id", client.getCurrentUser()!!.id)
        )
        val request = QueryUsersRequest(
            filter = filters,
            offset = 0,
            limit = 100
        )
        client.queryUsers(request).enqueue { result ->
            if (result.isSuccess) {
                val users: List<User> = result.data()
                usersAdapter.setData(users)
            } else {
                Log.e("UsersFragment", result.error().message.toString())
            }
        }

    }

    private fun setupToolbar(binding: FragmentUsersBinding) {
        binding.toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    private fun setupRecyclerView(binding: FragmentUsersBinding) {
       binding.usersRecyclerView.adapter = usersAdapter
    }

    private fun queryAllUsers() {
        val request = QueryUsersRequest(
            filter = Filters.ne("id", client.getCurrentUser()!!.id),
            offset = 0,
            limit = 100
        )
        client.queryUsers(request).enqueue { result ->
            if (result.isSuccess) {
                val users: List<User> = result.data()
                usersAdapter.setData(users)
            } else {
                Log.e("UsersFragment", result.error().message.toString())
            }
        }
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentUsersBinding.inflate(inflater, container, false)
}