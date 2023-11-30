package com.moneybox.minimb.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.moneybox.minimb.R
import com.moneybox.minimb.common.AuthSessionManager
import com.moneybox.minimb.feature.home.ui.HomeFragmentDirections
import com.moneybox.minimb.feature.login.ui.LoginFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main) {
    @Inject
    lateinit var moneyBoxSessionManager: AuthSessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        val supportFragmentManager =
            supportFragmentManager.findFragmentById(R.id.fragment_container_view) as NavHostFragment
        val navController = supportFragmentManager.findNavController()

        if (moneyBoxSessionManager.accessToken == null) {
            navController.navigate(LoginFragmentDirections.actionLoginFragmentToMainFragment())
        } else {
            navController.navigate(HomeFragmentDirections.actionLoginFragmentToHomeFragment())
        }
    }
}
