package com.geidea.ui.userdetails

import DatabaseHelperImpl
import android.os.Bundle
import android.os.CountDownTimer
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.geidea.R
import com.geidea.data.api.ApiHelperImpl
import com.geidea.data.api.RetrofitBuilder
import com.geidea.data.local.DatabaseBuilder
import com.geidea.data.local.entity.User
import com.geidea.utils.Status
import com.geidea.utils.ViewModelFactory
import kotlinx.android.synthetic.main.activity_user_details.*


class UserDetailsActivity : AppCompatActivity() {

    private lateinit var viewModel: UserDetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_details)
        val actionBar: ActionBar? = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.title = "User Details"
        setupViewModel()
        setupObserver()
        setupCounter()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupCounter() {
        object : CountDownTimer(6000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                textViewCounter.setText("Remaining Seconds to close: " + millisUntilFinished / 1000)
            }

            override fun onFinish() {
                finish()
            }
        }.start()
    }

    private fun setupObserver() {
        viewModel.getUsers().observe(this, {
            when (it.status) {
                Status.SUCCESS -> {
                    progressBar.visibility = View.GONE
                    it.data?.let { users -> renderList(users) }
                }
                Status.LOADING -> {
                    progressBar.visibility = View.VISIBLE
                }
                Status.ERROR -> {
                    //Handle Error
                    progressBar.visibility = View.GONE
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun renderList(users: User) {
        textViewUserNameDetails.text = users.first_name
        textViewUserLastName.text = users.last_name
        textViewUseremail.text = users.email
        Glide.with(imageViewAvatarDetails.context)
            .load(users.avatar)
            .into(imageViewAvatarDetails)

    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(
                ApiHelperImpl(RetrofitBuilder.apiService),
                DatabaseHelperImpl(DatabaseBuilder.getInstance(applicationContext))
            )
        ).get(UserDetailsViewModel::class.java)
        viewModel.fetchUsersDetails(intent.getIntExtra("id", 1))

    }

}
