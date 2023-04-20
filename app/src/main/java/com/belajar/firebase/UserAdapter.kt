package com.belajar.firebase

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.belajar.firebase.databinding.ItemUserBinding

class UserAdapter(
    private val onItemClickListener: (UserData) -> Unit,
    private val onItemDeleteClickListener: (String) -> Unit
) : RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    private val users = mutableListOf<UserData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            ItemUserBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun getItemCount(): Int = users.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(users[position])
    }

    fun setUsers(users: List<UserData>) {
        this.users.clear()
        this.users.addAll(users)
        notifyDataSetChanged()
    }

    fun deleteUser(id: String) {
        val user = users.find { it.id == id } ?: return
        val index = users.indexOf(user)
        users.removeAt(index)
        notifyItemRemoved(index)
    }

    inner class ViewHolder(
        private val binding: ItemUserBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: UserData) = with(binding) {
            tvName.text = user.name
            tvAge.text = user.age.toString()
            tvGender.text = user.gender

            ibDelete.setOnClickListener { onItemDeleteClickListener(user.id) }
            cvItem.setOnClickListener { onItemClickListener(user) }
        }
    }
}