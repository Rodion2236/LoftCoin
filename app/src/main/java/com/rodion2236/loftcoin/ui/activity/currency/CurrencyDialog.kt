package com.rodion2236.loftcoin.ui.activity.currency

import android.app.Dialog
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.rodion2236.loftcoin.R
import com.rodion2236.loftcoin.core.BaseComponent
import com.rodion2236.loftcoin.databinding.DialogCurrencyBinding
import javax.inject.Inject

class CurrencyDialog @Inject constructor(baseComponent: BaseComponent) : AppCompatDialogFragment() {

    private val component: CurrencyComponent
    private var binding: DialogCurrencyBinding? = null
    private var viewModel: CurrencyViewModel? = null
    private var adapter: CurrencyAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, component.viewModelFactory())[CurrencyViewModel::class.java]
        adapter = CurrencyAdapter()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogCurrencyBinding.inflate(layoutInflater)
        return MaterialAlertDialogBuilder(requireActivity())
            .setTitle(R.string.choose_currency)
            .setView(binding!!.root)
            .create()
    }

    init {
        component = DaggerCurrencyComponent.builder()
            .baseComponent(baseComponent)
            .build()
    }
}