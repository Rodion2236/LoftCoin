package com.rodion2236.loftcoin.ui.activity.converter

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rodion2236.loftcoin.R
import com.rodion2236.loftcoin.databinding.FragmentConverterBinding

class ConverterFragment : Fragment() {

    private lateinit var bindingConverterFragment: FragmentConverterBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bindingConverterFragment = FragmentConverterBinding.inflate(layoutInflater)
        return inflater.inflate(R.layout.fragment_converter, container, false)
    }
}