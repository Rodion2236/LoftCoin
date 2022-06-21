package com.rodion2236.loftcoin.ui.activity.rates

import android.util.TypedValue
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rodion2236.loftcoin.BuildConfig
import com.rodion2236.loftcoin.R
import com.rodion2236.loftcoin.data.models.coin.Coin
import com.rodion2236.loftcoin.databinding.ItemRatesBinding
import com.rodion2236.loftcoin.ui.util.PercentFormatter
import com.rodion2236.loftcoin.ui.util.PriceFormatter
import com.rodion2236.loftcoin.ui.widget.CircleViewOutLineProvider
import com.squareup.picasso.Picasso

class RatesAdapter(
    private var priceFormatter: PriceFormatter,
    private var percentFormatter: PercentFormatter
) : ListAdapter<Coin, RatesAdapter.RatesViewHolder>(CoinComparator) {

    private lateinit var inflater: LayoutInflater
    private var colorPositive = R.color.weird_green
    private var colorNegative = R.color.watermelon

    init {
        this.priceFormatter = PriceFormatter()
        this.percentFormatter = PercentFormatter()
    }

    object CoinComparator : DiffUtil.ItemCallback<Coin>() {
        override fun areItemsTheSame(oldItem: Coin, newItem: Coin): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Coin, newItem: Coin): Boolean {
            return oldItem == newItem
        }
    }

    inner class RatesViewHolder(val bindingRates: ItemRatesBinding) :
        RecyclerView.ViewHolder(bindingRates.root) {
        init {
            CircleViewOutLineProvider.apply(bindingRates.logo)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RatesViewHolder {
        return RatesViewHolder(ItemRatesBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: RatesViewHolder, position: Int) {
        val coin: Coin = getItem(position)
        holder.bindingRates.logoSymbol.text = coin.symbol
        holder.bindingRates.price.text = priceFormatter.format(coin.price)
        holder.bindingRates.change.text = percentFormatter.format(coin.change24h)
        if (coin.change24h > 0) {
            holder.bindingRates.change.setTextColor(colorPositive)
        } else {
            holder.bindingRates.change.setTextColor(colorNegative)
        }
        Picasso.get()
            .load(BuildConfig.IMG_ENDPOINT + coin.id + ".png")
            .into(holder.bindingRates.logo)
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        inflater = LayoutInflater.from(recyclerView.context)
        val typedValue = TypedValue()

        recyclerView.context.theme.resolveAttribute(R.attr.textPositive, typedValue, true)
        colorPositive = typedValue.data
        recyclerView.context.theme.resolveAttribute(R.attr.textNegative, typedValue, true)
        colorNegative = typedValue.data
    }
}