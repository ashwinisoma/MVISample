package com.example.presentation.productDetails

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.domain.common.Constants
import com.example.presentation.R
import com.example.presentation.constant.previewPrice
import com.example.presentation.constant.previewProductId
import com.example.presentation.customcomposable.LoadingScreen
import com.example.presentation.customcomposable.MyTopBar
import com.example.presentation.model.Product
import com.example.presentation.ui.theme.MVISampleTheme
import com.example.presentation.ui.theme.PADDING_20DP
import com.example.presentation.ui.theme.SIZE_16DP
import com.example.presentation.ui.theme.SIZE_8DP
import com.example.presentation.ui.theme.fontSize_18
import com.example.presentation.ui.theme.fontSize_20

/**
 * Represents Product detail screen
 */
@Composable
fun ProductDetailScreen(
    productId: Int,
    viewModel: ProductDetailViewModel,
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
            ProductDetail(productDetail)
        }

        is ProductDetailViewState.Error -> {
            (viewState.value as ProductDetailViewState.Error).throwable.message?.let {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize(),
                ) {
                    RetrySection(it) {
                        Log.d(Constants.APP_TAG, "Exception in getProductDetails api $it.error")
                        viewModel.sendIntent(ProductDetailViewIntent.FetchProductDetail(productId))
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun GreetingPreview() {
    MVISampleTheme {
        val product = Product(
            title = stringResource(R.string.product_title),
            image = stringResource(R.string.product_url),
            description = stringResource(R.string.product_description),
            id = previewProductId,
            price = previewPrice,
        )
        ProductDetail(product)
    }
}

@Composable
private fun ProductDetail(product: Product) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter,
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.White,
        ) {
            Column {
                MyTopBar(
                    backgroundColor = Color(R.color.topBar),
                    title = {
                        Text(
                            stringResource(R.string.product_details),
                            fontSize = fontSize_20,
                        )
                    },
                )
                // Add your other content here
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(SIZE_16DP),
                ) {
                    Text(
                        text = product.title ?: stringResource(R.string.product_title),
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(bottom = SIZE_8DP),
                    )
                    val url = product.image
                    val painter = rememberAsyncImagePainter(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(url)
                            .crossfade(true)
                            .build(),
                    )
                    Image(
                        painter = painter,
                        contentDescription = product.title,
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1f)
                            .clip(shape = RoundedCornerShape(SIZE_8DP)),
                    )
                    Text(
                        text = product.description ?: stringResource(R.string.product_description),
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(top = SIZE_16DP),
                    )
                }
            }
        }
    }
}

@Composable
private fun RetrySection(
    error: String,
    onRetry: () -> Unit,
) {
    Column {
        Text(
            error,
            color = Color.Black,
            fontSize = fontSize_18,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(PADDING_20DP),
        )
        Spacer(modifier = Modifier.height(SIZE_8DP))
        Button(
            onClick = { onRetry() },
            modifier = Modifier.align(Alignment.CenterHorizontally),
        ) {
            Text(text = stringResource(R.string.button_retry))
        }
    }
}
