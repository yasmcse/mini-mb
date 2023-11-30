package com.moneybox.minimb.navigation

import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.moneybox.minimb.common.MoneyBoxNavigator
import com.moneybox.minimb.feature.home.ui.HomeFragmentDirections
import com.moneybox.minimb.ui.MainActivity
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class MoneyBoxNavigatorImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : MoneyBoxNavigator {
    override fun navigateToMainActivity() {
        val intent = Intent(context, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }

    override fun Fragment.navigateToHomeScreen() {
        navigate(HomeFragmentDirections.actionLoginFragmentToHomeFragment())
    }


    override fun Fragment.goBack() {
        findNavController().popBackStack()
    }

    private fun Fragment.navigate(navDirections: NavDirections) {
        findNavController().navigate(navDirections)
    }
}