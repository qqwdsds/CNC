package com.messenger.cnc.domain.base

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel

abstract class BaseFragment: Fragment() {
    abstract val viewModel: ViewModel
}