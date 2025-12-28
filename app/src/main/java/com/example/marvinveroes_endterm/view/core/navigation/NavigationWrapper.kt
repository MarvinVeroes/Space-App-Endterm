package com.example.marvinveroes_endterm.view.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.marvinveroes_endterm.view.auth.login.LoginScreen
import com.example.marvinveroes_endterm.view.auth.register.RegisterScreen
import com.example.marvinveroes_endterm.view.presentation.detail.RocketDetailScreen
import com.example.marvinveroes_endterm.view.presentation.home.HomeScreen
import com.example.marvinveroes_endterm.view.presentation.splash.SplashScreen

@Composable
fun NavigationWrapper() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Splash
    ) {
        composable<Splash> {
            SplashScreen(
                onTimeout = {
                    navController.navigate(Login) {
                        popUpTo(Splash) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            )
        }

        composable<Login> {
            LoginScreen(
                navigateToRegister = { navController.navigate(Register) },
                navigateToHome = {
                    navController.navigate(Home) {
                        popUpTo(Login) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            )
        }

        composable<Register> {
            RegisterScreen(
                navigateBack = { navController.popBackStack() },
                navigateToHome = {
                    navController.navigate(Home) {
                        popUpTo(Login) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            )
        }

        composable<Home> {
            HomeScreen(
                onLogout = {
                    navController.navigate(Login) {
                        popUpTo(Home) { inclusive = true }
                        launchSingleTop = true
                    }
                },
                onRocketClick = { id -> navController.navigate(RocketDetail(id))}
            )
        }
        composable<RocketDetail> { backStackEntry ->
            val args = backStackEntry.toRoute<RocketDetail>()

            RocketDetailScreen(
                rocketId = args.rocketId,
                navigateBack = {navController.popBackStack()}
            )
        }
    }
}
