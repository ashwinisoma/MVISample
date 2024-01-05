package com.example.presentation

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.example.presentation.customcomposable.TextWidget
import org.junit.Rule
import org.junit.Test

class TextWidgetTest {


    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun productItem_displaysProductDetails() {
        // Given
        val defaultMessage = FakeData.product_description

        // When
        composeTestRule.setContent {
            TextWidget(message = defaultMessage)
        }

        // Then
        // Verify that the Text composable is displayed with the default values
        composeTestRule.onNodeWithText(defaultMessage).assertIsDisplayed()
    }

}