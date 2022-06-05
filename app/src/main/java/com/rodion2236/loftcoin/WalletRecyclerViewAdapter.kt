package com.rodion2236.loftcoin

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rodion2236.loftcoin.databinding.ItemWalletsBinding

internal class WalletRecyclerViewAdapter :
    RecyclerView.Adapter<WalletRecyclerViewAdapter.ViewHolder>() {

    private var inflater: LayoutInflater? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(ItemWalletsBinding.inflate(inflater!!, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {}

    override fun getItemCount(): Int {
        return 10
    }

    internal class ViewHolder(bindingWallet: ItemWalletsBinding) :
        RecyclerView.ViewHolder(bindingWallet.root)

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        inflater = LayoutInflater.from(recyclerView.context)
    }
}