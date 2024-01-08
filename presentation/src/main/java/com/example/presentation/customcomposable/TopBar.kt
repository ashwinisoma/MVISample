package com.example.presentation.customcomposable

import android.app.Activity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.example.presentation.R
import com.example.presentation.ui.theme.PADDING_8DP
import com.example.presentation.ui.theme.SIZE_16DP
import com.example.presentation.ui.theme.SIZE_8DP
import com.example.presentation.ui.theme.WEIGHT_1

@Composable
fun MyTopBar(
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color.DarkGray,
    title: @Composable () -> Unit,
    hasNavigation: Boolean = false,
    navController: NavController,
) {
    val activity = LocalContext.current as Activity

    Surface(
        modifier = modifier.fillMaxWidth(),
        color = backgroundColor,
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = SIZE_16DP, vertical = SIZE_8DP)
                .fillMaxWidth(),
             horizontalArrangement = Arrangement.Center,
        ) {
            if (hasNavigation) {
                IconButton(
                    onClick = {
                        navController.popBackStack()
                    },
                    modifier = Modifier.weight(WEIGHT_1),
                ) {
                    Icon(
                        Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back),
                    )
                }
            }
            Spacer(modifier = Modifier.weight(WEIGHT_1))
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.
                padding(top = PADDING_8DP),
            ) {
                title() // Wrap title in Box with vertical alignment
            }
            Spacer(modifier = Modifier.weight(WEIGHT_1))


            // Close button
            IconButton(
                onClick = {
                    activity.finish()
                },
            ) {
                Icon(
                    Icons.Filled.Close,
                    contentDescription = stringResource(R.string.close)
                )
            }
        }
    }
}
