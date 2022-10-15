package com.swapnildroid.pet.support.ui.main.viewmodels

import android.view.View
import androidx.databinding.ObservableInt
import androidx.lifecycle.ViewModel
import com.swapnildroid.pet.support.ui.main.interaction.ErrorInterface

open class BaseViewModel : ViewModel() {

    val isBusyLiveData: ObservableInt = ObservableInt(View.VISIBLE)

    var errorInterface: ErrorInterface? = null

    protected var isBusy: Boolean = false
        set(value) {
            isBusyLiveData.set(if (value) View.VISIBLE else View.GONE)
            field = value
        }

}