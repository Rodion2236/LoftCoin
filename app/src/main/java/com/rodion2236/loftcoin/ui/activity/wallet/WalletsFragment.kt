package com.rodion2236.loftcoin.ui.activity.wallet

import android.os.Bundle
import android.util.TypedValue
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.rodion2236.loftcoin.R
import com.rodion2236.loftcoin.core.BaseComponent
import com.rodion2236.loftcoin.databinding.FragmentWalletsBinding
import com.rodion2236.loftcoin.ui.activity.rates.DaggerRatesComponent
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject
import kotlin.math.abs
import kotlin.math.pow

class WalletsFragment @Inject constructor() : Fragment() {

    private var walletsSnapHelper: SnapHelper? = null
    private val disposable = CompositeDisposable()
    private lateinit var walletAdapter: WalletAdapter
    private lateinit var viewModel: WalletsViewModel
    private lateinit var bindingWalletsFragment: FragmentWalletsBinding

    @Inject
    lateinit var baseComponent: BaseComponent
    private lateinit var component: WalletsComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        component = DaggerWalletsComponent
            .builder()
            .baseComponent(baseComponent)
            .build()
        viewModel = ViewModelProvider(this, component.viewModelFactory())
            .get(WalletsViewModel::class.java)
        walletAdapter = component.walletsAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_wallets, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bindingWalletsFragment = FragmentWalletsBinding.bind(view)
        walletsSnapHelper = PagerSnapHelper()
        (walletsSnapHelper as PagerSnapHelper).attachToRecyclerView(bindingWalletsFragment.recycler)

        val value = TypedValue()
        view.context.theme.resolveAttribute(R.attr.walletCardWidth, value, true)

        val displayMetrics = view.context.resources.displayMetrics
        val padding = (displayMetrics.widthPixels - value.getDimension(displayMetrics)).toInt() / 2
        bindingWalletsFragment.recycler.layoutManager = LinearLayoutManager(view.context, RecyclerView.HORIZONTAL, false)
        bindingWalletsFragment.recycler.setPadding(padding, 0, padding, 0)
        bindingWalletsFragment.recycler.clipToPadding = false
        bindingWalletsFragment.recycler.adapter = walletAdapter
        disposable.addAll(
            viewModel.getWallets().subscribe(walletAdapter::submitList),
            viewModel.getWallets().map { it.isEmpty() }.subscribe {
                bindingWalletsFragment.walletCard.visibility = if (!it) View.VISIBLE else View.GONE
                bindingWalletsFragment.transactions.visibility = if (!it) View.VISIBLE else View.GONE
            }
        )
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
        bindingWalletsFragment.recycler.adapter = null
        disposable.clear()
        super.onDestroyView()
    }
}