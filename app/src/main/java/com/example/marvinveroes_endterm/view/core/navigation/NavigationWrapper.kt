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

// Helper para evitar repetir el mismo bloque.
private fun androidx.navigation.NavController.navigateAndClear(
    target: Any,
    clearUpTo: Any
) {
    navigate(target) {
        popUpTo(clearUpTo) { inclusive = true }
        launchSingleTop = true
    }
}

@Composable
fun NavigationWrapper() {
    // NavController controla el stack de pantallas (backstack) y la navegacion.
    val navController = rememberNavController()

    // NavHost para definir las rutas y pantallas de la app.
    // startDestination define la pantalla inicial.
    NavHost(
        navController = navController,
        startDestination = Splash
    ) {
        // SPLASH SCREEN
        composable<Splash> {
            SplashScreen(
                // Navegar a Login despues del timeout, con popUpTo para limpiar el backstack.
                // inclusive = true elimina la pantalla de Splash del backstack.
                onTimeout = {
                    navController.navigateAndClear(Login, Splash)
                }
            )
        }

        // LOGIN SCREEN
        composable<Login> {
            LoginScreen(
                //Navegar a Register dejando login en el backstack.
                navigateToRegister = { navController.navigate(Register) },
                // Login correcto -> navegar a home y limpiar backstack.
                // inclusive = true elimina la pantalla de Login del backstack.
                navigateToHome = {
                    navController.navigateAndClear(Home, Login)
                }
            )
        }

        // REGISTER SCREEN
        composable<Register> {
            RegisterScreen(
                // Navegar de vuelta a Login.
                navigateBack = { navController.popBackStack() },
                // Registro correcto -> navegar a home y limpiar backstack.
                // inclusive = true elimina la pantalla de Login del backstack.
                navigateToHome = {
                    navController.navigateAndClear(Home, Login)
                }
            )
        }

        // HOME SCREEN
        composable<Home> {
            HomeScreen(
                // Logout -> navegar a Login y limpiar backstack.
                // popUpto inclusive = true elimina la pantalla de Home del backstack.
                onLogout = {
                    navController.navigateAndClear(Login, Home)
                },
                // Navegar a detalle de Rocket pasando el id como argumento.
                onRocketClick = { id -> navController.navigate(RocketDetail(id))}
            )
        }
        // ROCKET DETAIL SCREEN
        composable<RocketDetail> { backStackEntry ->
            // Recuperar los argumentos de la ruta.
            val args = backStackEntry.toRoute<RocketDetail>()

            RocketDetailScreen(
                rocketId = args.rocketId,
                // Back desde detalle a home-
                navigateBack = {navController.popBackStack()}
            )
        }
    }
}
