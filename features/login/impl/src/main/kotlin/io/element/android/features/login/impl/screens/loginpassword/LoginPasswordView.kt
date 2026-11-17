/*
 * Copyright (c) 2025 Element Creations Ltd.
 * Copyright 2023-2025 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package io.element.android.features.login.impl.screens.loginpassword

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.autofill.ContentType
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalAutofillManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentType
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import io.element.android.compound.theme.ElementTheme
import io.element.android.compound.tokens.generated.CompoundIcons
import io.element.android.features.login.impl.R
import io.element.android.features.login.impl.error.loginError
import io.element.android.libraries.architecture.AsyncData
import io.element.android.libraries.designsystem.atomic.molecules.ButtonColumnMolecule
import io.element.android.libraries.designsystem.components.dialogs.ErrorDialog
import io.element.android.libraries.designsystem.components.form.textFieldState
import io.element.android.libraries.designsystem.modifiers.bringIntoViewOnImeVisible
import io.element.android.libraries.designsystem.modifiers.onTabOrEnterKeyFocusNext
import io.element.android.libraries.designsystem.preview.ElementPreview
import io.element.android.libraries.designsystem.preview.PreviewsDayNight
import io.element.android.libraries.designsystem.theme.components.Button
import io.element.android.libraries.designsystem.theme.components.Icon
import io.element.android.libraries.designsystem.theme.components.PasswordVisibilityToggle
import io.element.android.libraries.designsystem.theme.components.Scaffold
import io.element.android.libraries.designsystem.theme.components.Text
import io.element.android.libraries.designsystem.theme.components.TextField
import io.element.android.libraries.testtags.TestTags
import io.element.android.libraries.testtags.testTag
import io.element.android.libraries.ui.strings.CommonStrings
import androidx.compose.material3.Button as M3Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text as M3Text
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.RoundedCornerShape

private val CharlieSecondary = Color(0xFF29335C)
private val CharlieSecondaryDisabled = Color(0xFF5B6487)

@Composable
fun LoginPasswordView(
    state: LoginPasswordState,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val autofillManager = LocalAutofillManager.current

    val isLoading by remember(state.loginAction) {
        derivedStateOf {
            state.loginAction is AsyncData.Loading
        }
    }
    val focusManager = LocalFocusManager.current

    fun submit() {
        // Clear focus to prevent keyboard issues with textfields
        focusManager.clearFocus(force = true)

        autofillManager?.commit()

        state.eventSink(LoginPasswordEvent.Submit)
    }

    Scaffold(
        modifier = modifier,
        topBar = {},
    ) { padding ->
        val scrollState = rememberScrollState()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF971B2F),
                            Color(0xFF801A3B)
                        )
                    )
                )
                .imePadding()
                .padding(padding)
                .consumeWindowInsets(padding)
                .verticalScroll(state = scrollState)
                .padding(start = 20.dp, end = 20.dp, bottom = 20.dp),
        ) {
            CharlieLoginHeader()
            Spacer(modifier = Modifier.height(16.dp))

            LoginForm(
                state = state,
                isLoading = isLoading,
                onSubmit = ::submit
            )
            // Min spacing
            Spacer(Modifier.height(24.dp))
            // Submit
            Box(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
            ) {
                ButtonColumnMolecule {
                    M3Button(
                        onClick = ::submit,
                        enabled = state.submitEnabled && !isLoading,
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag(TestTags.loginContinue),
                        shape = RoundedCornerShape(28.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = CharlieSecondary,
                            contentColor = Color.White,
                            disabledContainerColor = CharlieSecondaryDisabled,
                            disabledContentColor = Color.White.copy(alpha = 0.85f)
                        )
                    ) {
                        if (isLoading) {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(18.dp),
                                    strokeWidth = 2.dp,
                                    color = Color.White
                                )
                                M3Text(text = stringResource(R.string.charlie_login_button))
                            }
                        } else {
                            M3Text(text = stringResource(R.string.charlie_login_button))
                        }
                    }
                    Spacer(modifier = Modifier.height(48.dp))
                }
            }

            if (state.loginAction is AsyncData.Failure) {
                LoginErrorDialog(error = state.loginAction.error, onDismiss = {
                    state.eventSink(LoginPasswordEvent.ClearError)
                })
            }
        }
    }
}

@Composable
private fun CharlieLoginHeader() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 36.dp, bottom = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.charlie_login_icon),
            contentDescription = null,
            modifier = Modifier.size(150.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = stringResource(R.string.charlie_login_title),
            style = ElementTheme.typography.fontHeadingLgBold,
            color = Color.White,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = stringResource(R.string.charlie_login_subtitle),
            style = ElementTheme.typography.fontBodyLgRegular,
            color = Color.White.copy(alpha = 0.9f),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun LoginForm(
    state: LoginPasswordState,
    isLoading: Boolean,
    onSubmit: () -> Unit,
) {
    var loginFieldState by textFieldState(stateValue = state.formState.login)
    var passwordFieldState by textFieldState(stateValue = state.formState.password)

    val focusManager = LocalFocusManager.current
    val eventSink = state.eventSink

    Column {
        TextField(

            value = loginFieldState,
            enabled = !isLoading,
            modifier = Modifier
                .fillMaxWidth()
                .bringIntoViewOnImeVisible()
                .onTabOrEnterKeyFocusNext(focusManager)
                .testTag(TestTags.loginEmailUsername)
                .semantics {
                    contentType = ContentType.Username
                },
            placeholder = stringResource(R.string.charlie_login_username),
            onValueChange = {
                val sanitized = it.sanitize()
                loginFieldState = sanitized
                eventSink(LoginPasswordEvent.SetLogin(sanitized))
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(onNext = {
                focusManager.moveFocus(FocusDirection.Down)
            }),
            singleLine = true,
            trailingIcon = if (loginFieldState.isNotEmpty()) {
                {
                    Box(
                        Modifier.clickable(
                            onClickLabel = stringResource(CommonStrings.action_clear),
                            role = Role.Button,
                        ) {
                            loginFieldState = ""
                            eventSink(LoginPasswordEvent.SetLogin(""))
                        }
                    ) {
                        Icon(
                            imageVector = CompoundIcons.Close(),
                            contentDescription = stringResource(CommonStrings.action_clear),
                            tint = ElementTheme.colors.iconSecondary
                        )
                    }
                }
            } else {
                null
            },
        )
        var passwordVisible by remember { mutableStateOf(false) }
        if (state.loginAction is AsyncData.Loading) {
            // Ensure password is hidden when user submits the form
            passwordVisible = false
        }
        Spacer(Modifier.height(20.dp))
        TextField(
            value = passwordFieldState,
            enabled = !isLoading,
            modifier = Modifier
                .fillMaxWidth()
                .bringIntoViewOnImeVisible()
                .onTabOrEnterKeyFocusNext(focusManager)
                .testTag(TestTags.loginPassword)
                .semantics {
                    contentType = ContentType.Password
                },
            onValueChange = {
                val sanitized = it.sanitize()
                passwordFieldState = sanitized
                eventSink(LoginPasswordEvent.SetPassword(sanitized))
            },
            placeholder = stringResource(R.string.charlie_login_password),
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                PasswordVisibilityToggle(
                    visible = passwordVisible,
                    onToggle = { passwordVisible = !passwordVisible },
                )
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done,
            ),
            keyboardActions = KeyboardActions(
                onDone = { onSubmit() }
            ),
            singleLine = true,
        )
    }
}


/**
 * Ensure that the string does not contain any line separator, which can happen when pasting values.
 */
private fun String.sanitize(): String {
    return filterNot { it == '\n' || it == '\r' }
}

@Composable
private fun LoginErrorDialog(error: Throwable, onDismiss: () -> Unit) {
    ErrorDialog(
        title = stringResource(id = CommonStrings.dialog_title_error),
        content = stringResource(R.string.charlie_login_error_sign_in),
        onSubmit = onDismiss
    )
}

@PreviewsDayNight
@Composable
internal fun LoginPasswordViewPreview(@PreviewParameter(LoginPasswordStatePreviewParam::class) state: LoginPasswordState) = ElementPreview {
    LoginPasswordView(
        state = state,
        onBackClick = {},
    )
}
