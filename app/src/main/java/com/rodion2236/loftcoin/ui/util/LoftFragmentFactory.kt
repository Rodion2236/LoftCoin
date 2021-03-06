package com.rodion2236.loftcoin.ui.util

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import javax.inject.Inject
import javax.inject.Provider

class LoftFragmentFactory @Inject constructor(
    private val providers: Map<Class<*>, @JvmSuppressWildcards Provider<Fragment>>
) : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        val fragmentClass = loadFragmentClass(classLoader, className)
        val provider = providers[fragmentClass]
        return provider?.get() ?: super.instantiate(classLoader, className)
    }
}