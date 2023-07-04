package com.messenger.cnc.domain

import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.messenger.cnc.R

abstract class BaseSignInRegisterFragment: Fragment() {
    abstract val viewModel: ViewModel

    /**
     * Disable all view on screen.
     * States:
     * DISABLE - disable all views;
     * ENABLE - enable all view
     */
     fun changeViewsState(viewGroup: ViewGroup, state: Int) {
        when (state) {
            DISABLE -> {
                viewGroup.children.forEach {
                    it.isEnabled = false
                    if (it.id == R.id.progressbar || it.id == R.id.background_eclipse)
                        it.visibility = View.VISIBLE
                }
            }
            ENABLE -> {
                viewGroup.children.forEach {
                    it.isEnabled = true
                    if (it.id == R.id.progressbar || it.id == R.id.background_eclipse)
                        it.visibility = View.GONE
                }
            }
        }
    }

    companion object {
        // to disable/enable views
        const val DISABLE = 0
        const val ENABLE = 1
    }
}