package com.fernandoehs.movies.utils

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.fernandoehs.movies.R


class FragmentContainerHelper(private val mActivity: AppCompatActivity, private val mFragmentContainer: View?) {

    fun updateFragmentContainer(
        fragment: Fragment,
        isReplacement: Boolean
    ) {
        updateContainer(
            fragment,
            isReplacement,
            FragmentTransaction.TRANSIT_FRAGMENT_FADE
        )
    }

    private fun updateContainer(
        fragment: Fragment,
        isReplacement: Boolean,
        fragmentTransaction: Int
    ) {
        if (mFragmentContainer != null) {
            val fragmentId = mFragmentContainer.id
            val fm = mActivity.supportFragmentManager
            val ft = fm.beginTransaction()
            ft.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right)
            if (isReplacement) {
                if (fm.backStackEntryCount > 0) {
                    cleanBackStack(fm)
                }
                ft.replace(fragmentId, fragment)
            } else {
                val fragToHide = fm.findFragmentById(fragmentId)
                if (fragToHide != null) {
                    ft.hide(fragToHide)
                    ft.setTransition(fragmentTransaction)
                }
                ft.addToBackStack(null)
                ft.replace(fragmentId, fragment)
            }
            ft.commit()
        }
    }

    private fun cleanBackStack(fm: FragmentManager) {
        val firstId = fm.getBackStackEntryAt(0).id
        fm.popBackStack(firstId, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }
}