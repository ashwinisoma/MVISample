package com.example.presentation.customcomposable

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import com.example.presentation.ui.theme.fontSize_16

@Composable
fun TextWidget(
    modifier: Modifier = Modifier,
    message: String,
    fontSize: TextUnit? = null,
    color: Color? = null,
    style: TextStyle? = null,
    maxLines: Int? = 1,
    overflow: TextOverflow? = null,
    fontWeight: FontWeight? = null,
) {
    Text(
        message,
        color = color ?: Color.Unspecified,
        fontSize = fontSize ?: fontSize_16,
        modifier = modifier,
        style = style ?: MaterialTheme.typography.titleSmall,
        maxLines = maxLines ?: 1,
        overflow = overflow ?: TextOverflow.Clip,
        fontWeight = fontWeight ?: FontWeight.Normal,
    )
}
