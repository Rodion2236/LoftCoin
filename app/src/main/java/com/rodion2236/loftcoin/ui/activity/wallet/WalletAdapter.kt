package com.rodion2236.loftcoin.ui.activity.wallet

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rodion2236.loftcoin.data.models.wallet.Wallet
import com.rodion2236.loftcoin.databinding.ItemWalletsBinding
import com.rodion2236.loftcoin.ui.util.BalanceFormatter
import com.rodion2236.loftcoin.ui.util.PriceFormatter
import com.squareup.picasso.Picasso
import javax.inject.Inject

class WalletAdapter @Inject constructor(
    private val priceFormatter: PriceFormatter,
    private val balanceFormatter: BalanceFormatter,
) : ListAdapter<Wallet, WalletAdapter.ViewHolder>(WalletComparator) {

    private lateinit var layoutInflater: LayoutInflater

    object WalletComparator : DiffUtil.ItemCallback<Wallet>() {
        override fun areItemsTheSame(oldItem: Wallet, newItem: Wallet): Boolean =
            oldItem.getUid() == newItem.getUid()

        override fun areContentsTheSame(oldItem: Wallet, newItem: Wallet): Boolean =
            oldItem == newItem
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        layoutInflater = LayoutInflater.from(recyclerView.context)
    }

    class ViewHolder(val binding: ItemWalletsBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemWalletsBinding.inflate(layoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val wallet = getItem(position)
        with(holder.binding) {
            logoSymbol.text = wallet.getCoin().symbol
            tvBalance1.text = balanceFormatter.format(wallet)
            val balance = wallet.getBalance() * wallet.getCoin().price
            tvBalance2.text = priceFormatter.format(wallet.getCoin().currencyCode, balance)
            Picasso.get()
                .load(BuildConfig.IMG_ENDPOINT + wallet.getCoin().id + ".png")
                .into(logo)
        }
    }
}