package com.rodion2236.loftcoin.ui.activity.currency

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rodion2236.loftcoin.data.models.currency.Currency
import com.rodion2236.loftcoin.databinding.ItemCurrencyBinding

internal class CurrencyAdapter : ListAdapter<Currency, CurrencyAdapter.ViewHolder>(object : DiffUtil.ItemCallback<Currency>() {

        override fun areItemsTheSame(oldItem: Currency, newItem: Currency): Boolean {
            return oldItem.equals(newItem)
        }

        override fun areContentsTheSame(oldItem: Currency, newItem: Currency): Boolean {
            return oldItem.equals(newItem)
        }
    }) {

    private var inflater: LayoutInflater? = null

    public override fun getItem(position: Int): Currency {
        return super.getItem(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemCurrencyBinding.inflate(inflater!!, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currency = getItem(position)
        holder.binding.itemCurrencyName.text = currency.name
        holder.binding.itemCurrencySymbol.text = currency.symbol
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        inflater = LayoutInflater.from(recyclerView.context)
    }

    internal class ViewHolder(val binding: ItemCurrencyBinding) : RecyclerView.ViewHolder(
        binding.root
    )
}