package com.geidea.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.geidea.R
import com.geidea.data.local.entity.User
import com.pinterestimageload.interfaces.ItemClickListner
import kotlinx.android.synthetic.main.item_layout.view.*


class UserAdapter(
    private val users: ArrayList<User>,
    private val view: ItemClickListner
) : RecyclerView.Adapter<UserAdapter.DataViewHolder>() {

    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(user: User) {
            itemView.textViewUserName.text = user.first_name
            itemView.textViewUserEmail.text = user.last_name
            Glide.with(itemView.imageViewAvatar.context)
                .load(user.avatar)
                .into(itemView.imageViewAvatar)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        DataViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_layout, parent,
                false
            )
        )

    override fun getItemCount(): Int = users.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            view.onItemClickListener(users[position])
        }
        holder.bind(users[position])
    }

    fun addData(list: List<User>) {
        users.addAll(list)
    }

}