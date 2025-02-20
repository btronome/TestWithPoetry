package com.example.testwithpoetry.presentation.welcome

import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.testwithpoetry.R
import com.example.testwithpoetry.presentation.components.LoadingIndicator
import com.example.testwithpoetry.presentation.state.UiState
import com.example.testwithpoetry.utils.DateUtils.convertMillisToDate
import com.example.testwithpoetry.utils.ShowToast

@Composable
fun WelcomeScreen(
    hasSeenWelcomeScreen: () -> Unit = {},
    viewModel: WelcomeViewModel = hiltViewModel()
) {
    val welcomeState by viewModel.welcomeState.collectAsState()

    when (welcomeState) {
        is UiState.Loading -> {
            LoadingIndicator()
        }

        is UiState.Success -> {
            val hasUsers = (welcomeState as UiState.Success<Boolean>).data
            if (hasUsers) {
                LaunchedEffect(Unit) {
                    hasSeenWelcomeScreen()
                }
            } else {
                WelcomeFormScreen(viewModel)
            }
        }

        is UiState.Error -> {
            ShowToast(message = (welcomeState as UiState.Error).message)
        }
    }
}

@Composable
private fun WelcomeFormScreen(viewModel: WelcomeViewModel) {
    var showModal by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .padding(dimensionResource(R.dimen.dim_16))
            .systemBarsPadding()
    ) {
        Column(modifier = Modifier.weight(1f)) {
            OutlinedTextField(
                value = viewModel.name,
                onValueChange = { viewModel.name = it },
                label = { Text(stringResource(R.string.name)) },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.dim_8)))

            OutlinedTextField(
                value = viewModel.email,
                onValueChange = { viewModel.email = it },
                label = { Text(stringResource(R.string.email)) },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.dim_8)))

            OutlinedTextField(
                value = convertMillisToDate(viewModel.birthday),
                onValueChange = { },
                label = { Text(stringResource(R.string.birthday)) },
                placeholder = { Text(stringResource(R.string.date_format)) },
                trailingIcon = {
                    Icon(
                        Icons.Default.DateRange,
                        contentDescription = stringResource(R.string.select_date)
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .pointerInput(viewModel.birthday) {
                        awaitEachGesture {
                            awaitFirstDown(pass = PointerEventPass.Initial)
                            val upEvent = waitForUpOrCancellation(pass = PointerEventPass.Initial)
                            if (upEvent != null) {
                                showModal = true
                            }
                        }
                    }
            )

            if (showModal) {
                DatePickerModal(
                    onDateSelected = { date -> date?.let { viewModel.birthday = it } },
                    onDismiss = { showModal = false }
                )
            }
        }

        Button(
            onClick = {
                viewModel.registerUser(
                    name = viewModel.name,
                    email = viewModel.email,
                    birthday = viewModel.birthday
                )
            },
            enabled = viewModel.isRegistrationFormValid(),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.register))
        }
    }
}

@Composable
fun DatePickerModal(onDateSelected: (Long?) -> Unit, onDismiss: () -> Unit) {
    val datePickerState = rememberDatePickerState()

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = {
                    onDateSelected(datePickerState.selectedDateMillis)
                    onDismiss()
                }
            ) {
                Text(stringResource(R.string.confirm))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(R.string.close))
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}
