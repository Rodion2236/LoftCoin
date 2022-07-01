package com.rodion2236.loftcoin.ui.activity.rates

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.rodion2236.loftcoin.R
import com.rodion2236.loftcoin.core.BaseComponent
import com.rodion2236.loftcoin.databinding.FragmentRatesBinding
import javax.inject.Inject

class RatesFragment @Inject constructor(

) : Fragment() {

    private lateinit var adapter: RatesAdapter
    private lateinit var viewModel: RatesViewModel
    private lateinit var bindingRatesFragment: FragmentRatesBinding

    @Inject
    lateinit var baseComponent: BaseComponent
    private lateinit var component: RatesComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        component = DaggerRatesComponent
            .builder()
            .baseComponent(baseComponent)
            .build()
        viewModel = ViewModelProvider(this, component.viewModelFactory())[RatesViewModel::class.java]
        adapter = component.ratesAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_rates, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        bindingRatesFragment = FragmentRatesBinding.bind(view)

        bindingRatesFragment.recycler.layoutManager = LinearLayoutManager(requireContext())
        bindingRatesFragment.recycler.swapAdapter(adapter, false)
        bindingRatesFragment.recycler.setHasFixedSize(true)

        viewModel.coins().observe(viewLifecycleOwner) { list ->
            adapter.submitList(list)
        }
        viewModel.isRefreshing().observe(viewLifecycleOwner) { isRefreshing ->
            bindingRatesFragment.fragmentRatesRefresher.isRefreshing = isRefreshing
            bindingRatesFragment.recycler.visibility = if (isRefreshing) View.GONE else View.VISIBLE
        }

        bindingRatesFragment.fragmentRatesRefresher.setOnRefreshListener {
            viewModel.refresh()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_icon, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.currency_dialog) {
            NavHostFragment.findNavController(this)
                .navigate(R.id.currency_dialog)
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        bindingRatesFragment.recycler.swapAdapter(null, false)
        super.onDestroyView()
    }
}