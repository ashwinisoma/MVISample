package com.example.presentation.customcomposable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.example.presentation.R
import com.example.presentation.ui.theme.PADDING_20DP
import com.example.presentation.ui.theme.SIZE_8DP
import com.example.presentation.ui.theme.fontSize_18

@Composable
fun RetryWidget(
    error: String,
    onRetry: () -> Unit,
) {
    Column {
        TextWidget(
            message = error,
            fontSize = fontSize_18,
            color = Color.Black,
            maxLines = 2,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(PADDING_20DP),
        )
        Spacer(modifier = Modifier.height(SIZE_8DP))
        Button(
            onClick = { onRetry() },
            modifier = Modifier.align(Alignment.CenterHorizontally),
        ) {
            TextWidget(
                message = stringResource(R.string.button_retry),
            )
        }
    }
}
