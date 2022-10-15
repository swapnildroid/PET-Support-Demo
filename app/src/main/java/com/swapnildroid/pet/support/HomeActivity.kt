package com.swapnildroid.pet.support

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.swapnildroid.pet.support.ui.main.fragments.PetListFragment
import com.swapnildroid.pet.support.ui.main.fragments.WebBrowserFragment
import com.swapnildroid.pet.support.ui.main.interaction.PetActionInterface
import com.swapnildroid.pet.support.ui.main.models.PetDataModel

class HomeActivity : AppCompatActivity(), PetActionInterface {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)
        if (savedInstanceState == null) {
            val petListFragment = PetListFragment.newInstance()
            showFragment(petListFragment)
        }
    }

    private fun showFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .addToBackStack(fragment.toString())
            .commit()
    }

    override fun onPetItemClicked(petDataModel: PetDataModel) {
        showFragment(WebBrowserFragment.newInstance(petDataModel.contentUrl))
    }

    override fun onBackPressed() {
        val webBrowserFragment: Fragment? = supportFragmentManager.findFragmentById(R.id.container)
        if (webBrowserFragment is WebBrowserFragment && webBrowserFragment.canGoBack()) {
            webBrowserFragment.goBack()
        } else if (supportFragmentManager.backStackEntryCount == 1) {
            finish()
        } else {
            super.onBackPressed()
        }
    }
}