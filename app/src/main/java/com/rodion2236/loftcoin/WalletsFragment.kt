package com.rodion2236.loftcoin

import android.os.Bundle
import android.util.TypedValue
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.rodion2236.loftcoin.databinding.FragmentWalletsBinding
import kotlin.math.abs
import kotlin.math.pow

class WalletsFragment : Fragment() {

    private var walletsSnapHelper: SnapHelper? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_wallets, container, false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        walletsSnapHelper = PagerSnapHelper()
        val bindingWalletsFragment = FragmentWalletsBinding.bind(view)
        (walletsSnapHelper as PagerSnapHelper).attachToRecyclerView(bindingWalletsFragment.recycler)

        val value = TypedValue()
        view.context.theme.resolveAttribute(R.attr.walletCardWidth, value, true)

        val displayMetrics = view.context.resources.displayMetrics
        val padding = (displayMetrics.widthPixels - value.getDimension(displayMetrics)).toInt() / 2
        bindingWalletsFragment.recycler.layoutManager = LinearLayoutManager(view.context, RecyclerView.HORIZONTAL, false)
        bindingWalletsFragment.recycler.adapter = WalletRecyclerViewAdapter()

        bindingWalletsFragment.recycler.setPadding(padding, 0, padding, 0)
        bindingWalletsFragment.recycler.clipToPadding = false

        bindingWalletsFragment.recycler.addOnScrollListener(CarouselScroller())
        bindingWalletsFragment.recycler.visibility = View.VISIBLE
        bindingWalletsFragment.walletCard.visibility = View.GONE
    }

    private class CarouselScroller : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            val centerX = (recyclerView.left + recyclerView.right) / 2
            for (i in 0 until recyclerView.childCount) {
                val child = recyclerView.getChildAt(i)
                val childCenterX = (child.left + child.right) / 2
                val childOffset =
                    abs(centerX - childCenterX) / centerX.toFloat()
                val factor = 0.85.pow(childOffset.toDouble())
                    .toFloat()
                child.scaleX = factor
                child.scaleY = factor
            }
        }
    }

    override fun onDestroyView() {
        walletsSnapHelper?.attachToRecyclerView(null)
        super.onDestroyView()
    }
}