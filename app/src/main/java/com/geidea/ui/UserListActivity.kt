package com.geidea.ui

import DatabaseHelperImpl
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.geidea.R
import com.geidea.adapter.UserAdapter
import com.geidea.data.api.ApiHelperImpl
import com.geidea.data.api.RetrofitBuilder
import com.geidea.data.local.DatabaseBuilder
import com.geidea.data.local.entity.User
import com.geidea.ui.userdetails.UserDetailsActivity
import com.geidea.utils.Status
import com.geidea.utils.ViewModelFactory
import com.pinterestimageload.interfaces.ItemClickListner
import kotlinx.android.synthetic.main.activity_main.*


class UserListActivity : AppCompatActivity(), ItemClickListner {

    private lateinit var viewModel: UserListViewModel
    private lateinit var adapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupUI()
        setupViewModel()
        setupObserver()
    }

    private fun setupUI() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter =
            UserAdapter(
                arrayListOf(), this
            )
        recyclerView.adapter = adapter
    }

    private fun setupObserver() {
        viewModel.getUsers().observe(this, {
            when (it.status) {
                Status.SUCCESS -> {
                    progressBar.visibility = View.GONE
                    it.data?.let { users -> renderList(users) }
                    recyclerView.visibility = View.VISIBLE
                }
                Status.LOADING -> {
                    progressBar.visibility = View.VISIBLE
                    recyclerView.visibility = View.GONE
                }
                Status.ERROR -> {
                    //Handle Error
                    progressBar.visibility = View.GONE
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun renderList(users: List<User>) {
        adapter.addData(users)
        adapter.notifyDataSetChanged()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(
                ApiHelperImpl(RetrofitBuilder.apiService),
                DatabaseHelperImpl(DatabaseBuilder.getInstance(applicationContext))
            )
        ).get(UserListViewModel::class.java)
        viewModel.fetchUsers(applicationContext)
    }

    override fun onItemClickListener(user: User) {
        val intent = Intent(this, UserDetailsActivity::class.java).apply {
            putExtra("id", user.id)
        }
        startActivity(intent)
    }
}
