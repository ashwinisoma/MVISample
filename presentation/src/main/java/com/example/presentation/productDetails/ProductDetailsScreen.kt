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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.example.domain.common.Constants
import com.example.presentation.R
import com.example.presentation.customcomposable.ImageWidget
import com.example.presentation.customcomposable.LoadingScreen
import com.example.presentation.customcomposable.MyTopBar
import com.example.presentation.customcomposable.RetryWidget
import com.example.presentation.customcomposable.TextWidget
import com.example.presentation.model.Product
import com.example.presentation.ui.theme.SIZE_16DP
import com.example.presentation.ui.theme.SIZE_8DP
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
    val viewState = viewModel.state.collectAsState(initial = ProductDetailViewState.Loading)

    when (viewState.value) {
        is ProductDetailViewState.Loading -> {
            LoadingScreen()
        }

        is ProductDetailViewState.Success -> {
            val productDetail = (viewState.value as ProductDetailViewState.Success).data
            ProductDetail(productDetail, navController)
        }

        is ProductDetailViewState.Error -> {
            (viewState.value as ProductDetailViewState.Error).throwable.message?.let {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize(),
                ) {
                    RetryWidget(it) {
                        Log.d(Constants.APP_TAG, "Exception in getProductDetails api $it.error")
                        viewModel.sendIntent(ProductDetailViewIntent.FetchProductDetail(productId))
                    }
                }
            }
        }
    }
}

@Composable
private fun ProductDetail(
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
            val limitedTitle = product.title?.take(20)?.let { "$it..." } ?: stringResource(R.string.product_title)

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
                            .aspectRatio(1f)
                            .clip(shape = RoundedCornerShape(SIZE_8DP)),
                        contentDescription = product.title,
                    )
                    TextWidget(
                        message = product.description ?: stringResource(R.string.product_description),
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(top = SIZE_16DP),
                        maxLines = Int.MAX_VALUE,
                    )
                }
            }
        }
    }
}
