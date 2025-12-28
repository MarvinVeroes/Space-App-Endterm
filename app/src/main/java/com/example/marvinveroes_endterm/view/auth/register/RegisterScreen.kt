package com.example.marvinveroes_endterm.view.auth.register

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.marvinveroes_endterm.R
import com.example.marvinveroes_endterm.view.core.components.AppButton
import com.example.marvinveroes_endterm.view.core.components.AppButtonSecondary
import com.example.marvinveroes_endterm.view.core.components.AppText
import com.example.marvinveroes_endterm.view.core.components.AppTextField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    navigateBack: () -> Unit,
    navigateToHome: () -> Unit,
    registerViewModel: RegisterViewModel = viewModel()
) {
    val uiState = registerViewModel.uiState.collectAsStateWithLifecycle().value
    val snackbarHostState = remember { SnackbarHostState() }

    val title: String
    val subtitle: String
    val label: String
    val placeholder: String
    val changeModeTitle: String
    val continueText: String

    if (uiState.isPhoneMode) {
        title = stringResource(R.string.register_screen_title_phone)
        subtitle = stringResource(R.string.register_screen_subtitle_phone)
        label = stringResource(R.string.register_screen_textfield_phone)
        placeholder = stringResource(R.string.register_screen_placeholder_phone)
        continueText = stringResource(R.string.register_screen_button_continue_phone)
        changeModeTitle = stringResource(R.string.register_screen_button_change_to_email)
    } else {
        title = stringResource(R.string.register_screen_title_email)
        subtitle = stringResource(R.string.register_screen_subtitle_email)
        label = stringResource(R.string.register_screen_textfield_email)
        placeholder = stringResource(R.string.register_screen_placeholder_email)
        continueText = stringResource(R.string.register_screen_button_continue_email)
        changeModeTitle = stringResource(R.string.register_screen_button_change_to_phone)
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                ),
                title = {},
                navigationIcon = {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Volver",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .clickable { navigateBack() }
                    )
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(padding)
                .padding(horizontal = 24.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            AnimatedContent(title) { animatedTitle ->
                AppText(
                    modifier = Modifier.fillMaxWidth(),
                    text = animatedTitle,
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            Spacer(Modifier.height(8.dp))

            AppText(
                text = subtitle,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(Modifier.height(16.dp))

            AppTextField(
                modifier = Modifier.fillMaxWidth(),
                value = uiState.value,
                onValueChange = registerViewModel::onRegisterChanged,
                label = label,
                placeholder = placeholder,
                isError = uiState.errorText != null,
                errorText = uiState.errorText,
                keyboardOptions = KeyboardOptions(
                    keyboardType = if (uiState.isPhoneMode) KeyboardType.Phone else KeyboardType.Email,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = { /* luego: continuar */ }
                )
            )


            Spacer(Modifier.height(12.dp))

            AppText(
                text = stringResource(R.string.register_screen_body),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(Modifier.height(16.dp))

            AppButton(
                modifier = Modifier.fillMaxWidth(),
                text = continueText,
                onClick = { /* luego: continuar */ },
                enabled = uiState.isRegisterEnabled
            )

            Spacer(Modifier.height(10.dp))

            AppButtonSecondary(
                modifier = Modifier.fillMaxWidth(),
                onClick = { registerViewModel.onChangeMode() },
                title = changeModeTitle,
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
                tittleColor = MaterialTheme.colorScheme.onBackground
            )

            Spacer(Modifier.weight(1f))

            AppText(
                modifier = Modifier.padding(12.dp),
                text = stringResource(R.string.register_screen_forgot_account),
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}
