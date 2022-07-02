package com.rodion2236.loftcoin.ui.activity.welcome

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rodion2236.loftcoin.R
import com.rodion2236.loftcoin.databinding.WelcomePageBinding

internal class WelcomeAdapter: RecyclerView.Adapter<WelcomeAdapter.ViewHolder>() {

    companion object {
        private val images_welcome = intArrayOf(
            R.drawable.welcome_1,
            R.drawable.welcome_2,
            R.drawable.welcome_3
        )

        private val title_welcome = intArrayOf(
            R.string.welcome_page_1_title,
            R.string.welcome_page_2_title,
            R.string.welcome_page_3_title
        )

        private val subtitle_welcome = intArrayOf(
            R.string.welcome_page_1_subtitle,
            R.string.welcome_page_2_subtitle,
            R.string.welcome_page_3_subtitle
        )
    }
    private var inflater: LayoutInflater? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(
            WelcomePageBinding.inflate(inflater!!, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, pos: Int) {
        holder.bingingWelcome.image.setImageResource(images_welcome[pos])
        holder.bingingWelcome.titleWelcome.setText(title_welcome[pos])
        holder.bingingWelcome.subtitleWelcome.setText(subtitle_welcome[pos])
    }

    override fun getItemCount(): Int {
        return images_welcome.size
    }

    internal class ViewHolder(
        val bingingWelcome: WelcomePageBinding
        ): RecyclerView.ViewHolder(bingingWelcome.root)

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        inflater = LayoutInflater.from(recyclerView.context)
    }
}