package com.example.testwithpoetry.presentation.account

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.testwithpoetry.R
import com.example.testwithpoetry.presentation.components.LoadingIndicator
import com.example.testwithpoetry.presentation.state.UiState
import com.example.testwithpoetry.utils.DateUtils.convertMillisToDate
import com.example.testwithpoetry.utils.ShowToast

@Composable
fun ProfileScreen(onTitleChanged: (String) -> Unit, viewModel: ProfileViewModel = hiltViewModel()) {
    onTitleChanged(stringResource(R.string.profile))
    val userState by viewModel.userState.collectAsState()

    when (userState) {
        is UiState.Loading -> {
            LoadingIndicator()
        }

        is UiState.Success -> {
            val name = (userState as UiState.Success).data.name
            val email = (userState as UiState.Success).data.email
            val birthday = (userState as UiState.Success).data.birthday

            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                modifier = Modifier
                    .padding(dimensionResource(R.dimen.dim_8))
                    .fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(dimensionResource(R.dimen.dim_16))) {
                    Text(text = "${stringResource(R.string.name)}: $name")
                    Text(text = "${stringResource(R.string.email)}: $email")
                    Text(text = "${stringResource(R.string.birthday)}: ${convertMillisToDate(birthday)}")
                }
            }
        }

        is UiState.Error -> {
            ShowToast(message = (userState as UiState.Error).message)
        }
    }
}
