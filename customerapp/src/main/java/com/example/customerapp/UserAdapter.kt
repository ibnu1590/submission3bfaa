package com.example.customerapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.customerapp.utils.User
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_user.view.*

class UserAdapter (

    private val list: MutableList<User>? = mutableListOf(),
    private val onClick: ((User) -> Unit)? = null,
    private val onLongClick: ((User, Int) -> Unit)? = null
) :
    RecyclerView.Adapter<UserAdapter.ViewHolder>() {
    var listFav = ArrayList<User>()
        set(listFav){
            if (listFav.size>0){
                this.listFav.clear()
            }
            this.listFav.addAll(listFav)
            notifyDataSetChanged()
        }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = list?.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list?.get(position))
    }

    fun addAll(result: List<User>?) {
        if (result != null) {
            list?.clear()
            list?.addAll(result)
            notifyDataSetChanged()
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(user: User?) {
            Picasso.get()
                .load(user?.avatarUrl)
                .placeholder(R.drawable.placeholder)
                .error(R.color.design_default_color_error)
                .into(itemView.iv_avatar_customer)
            itemView.tv_username_customer.text = user?.userName
            itemView.setOnClickListener {
                if (user != null) {
                    onClick?.invoke(user)
                }
            }
            itemView.setOnLongClickListener {
                if (user != null) {
                    onLongClick?.invoke(user, adapterPosition)
                }
                return@setOnLongClickListener true
            }
        }
    }
}