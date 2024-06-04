package com.protectly.alkewallet.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.protectly.alkewallet.R
import com.protectly.alkewallet.model.Transaction

class ListTransactionAdapter(private val listaTransacciones: List<Transaction>) : RecyclerView.Adapter<ListTransactionAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val concept: TextView = view.findViewById(R.id.concept)
        val date: TextView = view.findViewById(R.id.date_list)
        val amount: TextView = view.findViewById(R.id.ammount_list)
        val image: ImageView = view.findViewById(R.id.img_profile_list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_view_list_users_transaction, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listaTransacciones.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val transaction = listaTransacciones[position]
        holder.concept.text = transaction.concept
        holder.date.text = transaction.date
        holder.amount.text = "$${transaction.amount}"
        holder.image.setImageResource(R.drawable.default_user_image) // Utiliza una imagen por defecto
    }
}
