package com.rodion2236.loftcoin.ui.activity.currency

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatDialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.rodion2236.loftcoin.R
import com.rodion2236.loftcoin.databinding.DialogCurrencyBinding

class CurrencyDialog : AppCompatDialogFragment() {

    private lateinit var bindingDialogCurrency: DialogCurrencyBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        bindingDialogCurrency = DialogCurrencyBinding.inflate(requireActivity().layoutInflater)
        return MaterialAlertDialogBuilder(requireActivity())
            .setTitle(R.string.choose_currency)
            .setView(bindingDialogCurrency.root)
            .create()
    }

}