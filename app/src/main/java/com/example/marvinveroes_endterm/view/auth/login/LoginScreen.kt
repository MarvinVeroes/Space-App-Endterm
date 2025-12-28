package com.example.marvinveroes_endterm.view.auth.login

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.marvinveroes_endterm.R
import com.example.marvinveroes_endterm.ui.theme.MarvinVeroesEndtermTheme
import com.example.marvinveroes_endterm.view.core.components.AppButton
import com.example.marvinveroes_endterm.view.core.components.AppButtonSecondary
import com.example.marvinveroes_endterm.view.core.components.AppText
import com.example.marvinveroes_endterm.view.core.components.AppTextField

@Composable
fun LoginScreen(
    navigateToRegister: () -> Unit,
    navigateToHome: () -> Unit,
    loginViewModel: LoginViewModel = viewModel()
) {
    val uiState = loginViewModel.uiState.collectAsStateWithLifecycle().value
    val snackbarHostState = remember { SnackbarHostState() }
    val uriHandler = LocalUriHandler.current

    LaunchedEffect(Unit) {
        loginViewModel.uiEvent.collect { event ->
            when (event) {
                is LoginUiEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(event.message)
                }
                LoginUiEvent.NavigateToHome -> {
                    navigateToHome()
                }
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(paddingValues)
                .padding(horizontal = 24.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AppText(
                text = stringResource(R.string.login_screen_header_text_spain),
                modifier = Modifier.padding(top = 22.dp),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(Modifier.weight(1f))

            Image(
                modifier = Modifier.size(82.dp),
                painter = painterResource(R.drawable.logo_space),
                contentDescription = "Logo Space App",
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
            )

            Spacer(Modifier.height(16.dp))

            AppText(
                text = stringResource(R.string.login_screen_title_login),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp),
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.headlineLarge
            )

            AppTextField(
                modifier = Modifier.fillMaxWidth(),
                value = uiState.email,
                label = stringResource(R.string.login_screen_textfield_email),
                placeholder = stringResource(R.string.login_screen_textfield_email_placeholder),
                onValueChange = loginViewModel::onEmailChanged,
                isError = uiState.emailError != null,
                errorText = uiState.emailError,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                ),
                enabled = !uiState.isLoading
            )

            Spacer(Modifier.height(12.dp))

            AppTextField(
                modifier = Modifier.fillMaxWidth(),
                value = uiState.password,
                label = stringResource(R.string.login_screen_textfield_password),
                placeholder = stringResource(R.string.login_screen_textfield_password_placeholder),
                onValueChange = loginViewModel::onPasswordChanged,
                isPassword = true,
                isError = uiState.passwordError != null,
                errorText = uiState.passwordError,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = androidx.compose.foundation.text.KeyboardActions(
                    onDone = { loginViewModel.onLoginClick() }
                ),
                enabled = !uiState.isLoading
            )

            Spacer(Modifier.height(18.dp))

            AppButton(
                modifier = Modifier.fillMaxWidth(),
                text = if (uiState.isLoading) "Cargando..." else "Iniciar sesión",
                onClick = { loginViewModel.onLoginClick() },
                enabled = uiState.isLoginEnabled && !uiState.isLoading
            )

            TextButton(
                onClick = { uriHandler.openUri("https://lasallefp.com/contactar/") },
                enabled = !uiState.isLoading
            ) {
                AppText(
                    text = stringResource(R.string.login_screen_button_text_forgot_password),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(Modifier.weight(1.2f))

            AppButtonSecondary(
                modifier = Modifier.fillMaxWidth(),
                onClick = navigateToRegister,
                border = BorderStroke(1.dp, color = MaterialTheme.colorScheme.primary),
                title = stringResource(R.string.login_screen_button_register)
            )

            Icon(
                modifier = Modifier
                    .width(60.dp)
                    .padding(vertical = 22.dp),
                painter = painterResource(R.drawable.logo_space),
                contentDescription = "Logo decorativo",
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Preview
@Composable
fun LoginScreenPreview() {
    MarvinVeroesEndtermTheme {
        LoginScreen(
            navigateToRegister = {},
            navigateToHome = {},
            loginViewModel = LoginViewModel()
        )
    }
}
