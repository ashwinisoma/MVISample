package com.example.presentation.customcomposable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.example.presentation.R
import com.example.presentation.navigation.Screen
import com.example.presentation.ui.theme.SIZE_16DP
import com.example.presentation.ui.theme.SIZE_8DP
import com.example.presentation.ui.theme.WEIGHT_1
import com.example.presentation.ui.theme.WEIGHT_2

@Composable
fun MyTopBar(
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color.DarkGray,
    title: @Composable () -> Unit,
    hasNavigation: Boolean = false,
    onBackClick: () -> Unit = {},
    navController: NavController,
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        color = backgroundColor,
    ) {
        Row(
            modifier = Modifier.padding(horizontal = SIZE_16DP, vertical = SIZE_8DP)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
        ) {
            if (hasNavigation) {
                IconButton(
                    onClick = {
                        if (navController.popBackStack()) {
                            // Navigation back button logic
                        } else {
                            onBackClick() // If navigation fails, use custom action
                            navController.navigate(Screen.ProductListScreen.route)
                        }
                    },
                    modifier = Modifier.weight(WEIGHT_1), // Maintains small size for back button
                ) {
                    Icon(
                        Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back),
                    )
                }
            }
            Spacer(modifier = Modifier.weight(WEIGHT_2)) // Space before title
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.padding(top = SIZE_8DP),
            ) {
                title() // Wrap title in Box with vertical alignment
            }
            Spacer(modifier = Modifier.weight(WEIGHT_2)) // Space after title
        }
    }
}
