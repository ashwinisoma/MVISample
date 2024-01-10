package com.example.presentation.productDetails

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.example.domain.common.Constants
import com.example.presentation.R
import com.example.presentation.customcomposable.ImageWidget
import com.example.presentation.customcomposable.LoadingScreen
import com.example.presentation.customcomposable.MyTopBar
import com.example.presentation.customcomposable.RetryWidget
import com.example.presentation.customcomposable.TextWidget
import com.example.presentation.model.Product
import com.example.presentation.ui.theme.ASPECT_RATIO
import com.example.presentation.ui.theme.SIZE_16DP
import com.example.presentation.ui.theme.SIZE_8DP
import com.example.presentation.ui.theme.TITLE_LENGTH
import com.example.presentation.ui.theme.fontSize_20

/**
 * Represents Product detail screen
 */
@Composable
fun ProductDetailScreen(
    productId: Int,
    viewModel: ProductDetailViewModel,
    navController: NavController,
) {
    LaunchedEffect(UInt) {
        viewModel.sendIntent(ProductDetailViewIntent.FetchProductDetail(productId))
    }
    val viewState by viewModel.state.collectAsState()
    ProductDetail(viewState, navController, viewModel, productId)

}

@Composable
private fun ProductDetail(
    viewState: ProductDetailViewState,
    navController: NavController,
    viewModel: ProductDetailViewModel,
    productId: Int
) {
    when (viewState) {
        is ProductDetailViewState.Error -> {
            viewState.throwable.message?.let {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize(),
                ) {
                    RetryWidget(it) {
                        Log.d(
                            Constants.APP_TAG,
                            Constants.GET_DETAIL_PRODUCTS_API_ERROR + "$it.error"
                        )
                        viewModel.sendIntent(ProductDetailViewIntent.FetchProductDetail(productId))
                    }
                }
            }
        }

        is ProductDetailViewState.Loading -> {
            LoadingScreen()
        }

        is ProductDetailViewState.Success -> {
            ProductDetailUI(product = viewState.data, navController = navController)
        }
    }
}

@Composable
private fun ProductDetailUI(
    product: Product,
    navController: NavController,
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter,
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.White,
        ) {
            val limitedTitle = product.title.take(TITLE_LENGTH).let { "$it..." }
            Column {
                MyTopBar(
                    backgroundColor = Color(R.color.topBar),
                    navController = navController,
                    title = {
                        TextWidget(
                            message = limitedTitle,
                            fontSize = fontSize_20,
                        )
                    },
                    hasNavigation = true,
                )
                // Add your other content here
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(SIZE_16DP),
                ) {
                    ImageWidget(
                        product.image,
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(ASPECT_RATIO)
                            .clip(shape = RoundedCornerShape(SIZE_8DP)),
                        contentDescription = product.title,
                    )
                    TextWidget(
                        message = product.description,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(top = SIZE_16DP),
                        maxLines = Int.MAX_VALUE,
                    )
                }
            }
        }
    }
}
