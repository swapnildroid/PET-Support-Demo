package com.swapnildroid.pet.support.ui.main.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.swapnildroid.pet.support.R
import com.swapnildroid.pet.support.databinding.PetListFragmentLayout
import com.swapnildroid.pet.support.ui.main.adapters.PetListRecyclerAdapter
import com.swapnildroid.pet.support.ui.main.interaction.ErrorInterface
import com.swapnildroid.pet.support.ui.main.interaction.PetActionInterface
import com.swapnildroid.pet.support.ui.main.interaction.TelephoneActionInterface
import com.swapnildroid.pet.support.ui.main.viewmodels.ConfigViewModel
import com.swapnildroid.pet.support.ui.main.viewmodels.PetListViewModel

class PetListFragment : Fragment(), TelephoneActionInterface, ErrorInterface {

    companion object {
        fun newInstance() = PetListFragment()
    }

    private lateinit var mPetListViewModel: PetListViewModel
    private lateinit var mConfigViewModel: ConfigViewModel
    private lateinit var mPetListFragmentLayout: PetListFragmentLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mPetListFragmentLayout = PetListFragmentLayout.inflate(inflater, container, false)
        return mPetListFragmentLayout.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeViewModels()
        initializeViews()
        initializeObservers()
        getData()
    }

    private fun getData() {
        mPetListViewModel.getPetList()
        mConfigViewModel.getConfig()
    }

    private fun initializeViews() {
        mPetListFragmentLayout.telephoneInterface = this
        mPetListFragmentLayout.petListViewModel = mPetListViewModel
        mPetListFragmentLayout.configViewModel = mConfigViewModel
    }

    private fun initializeViewModels() {
        mPetListViewModel = ViewModelProvider(this).get(PetListViewModel::class.java)
        mConfigViewModel = ViewModelProvider(this).get(ConfigViewModel::class.java)
        mPetListViewModel.errorInterface = this
        mConfigViewModel.errorInterface = this
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mPetListViewModel.errorInterface = null
        mConfigViewModel.errorInterface = null
    }

    private fun initializeObservers() {
        mConfigViewModel.settingsDataModelLiveData.observe(this) {
            mPetListFragmentLayout.settings = it
        }
        mPetListViewModel.petListLiveData.observe(this) {
            mPetListFragmentLayout.adapter =
                PetListRecyclerAdapter(
                    layoutInflater,
                    it,
                    activity as? PetActionInterface
                )
        }
    }

    override fun onCallButtonClicked() {
        val messageStringRes = if (mConfigViewModel.isWorkingHours()) {
            R.string.tip_get_back_soon
        } else {
            R.string.tip_work_hours_ended
        }
        showAlert(messageStringRes)
    }

    override fun onChatButtonClicked() {
        val messageStringRes = if (mConfigViewModel.isWorkingHours()) {
            R.string.tip_get_back_soon
        } else {
            R.string.tip_work_hours_ended
        }
        showAlert(messageStringRes)
    }

    override fun onError(message: String?) = showAlert(message)

    private fun showAlert(messageStringRes: Int) = showAlert(getString(messageStringRes))

    private fun showAlert(message: String?) {
        context?.let {
            val alertDialog = AlertDialog.Builder(it)
                .setMessage("$message")
                .setPositiveButton(R.string.ok, null)
                .setCancelable(false)
                .create()
            alertDialog.show()
            alertDialog.setCanceledOnTouchOutside(false)
        }
    }

}