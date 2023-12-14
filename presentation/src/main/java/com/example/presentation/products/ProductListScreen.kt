package com.example.presentation.products

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.domain.common.Constants
import com.example.presentation.R
import com.example.presentation.constant.gridColumnCount
import com.example.presentation.customcomposable.LoadingScreen
import com.example.presentation.customcomposable.MyTopBar
import com.example.presentation.model.Product
import com.example.presentation.navigation.Screen
import com.example.presentation.ui.theme.PADDING_20DP
import com.example.presentation.ui.theme.SIZE_200DP
import com.example.presentation.ui.theme.SIZE_8DP
import com.example.presentation.ui.theme.fontSize_18
import com.example.presentation.ui.theme.fontSize_20

/**
 * Represents UI for list of Products screen
 */
@Composable
fun ProductsListScreen(
    navController: NavController,
    viewModel: ProductListViewModel,
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,

    ) {
        ProductList(navController, viewModel)
    }
}

@Composable
private fun ProductList(
    navController: NavController,
    viewModel: ProductListViewModel,
) {
    LaunchedEffect(Unit) {
        viewModel.sendIntent(ProductListViewIntent.FetchProductListList)
    }
    val viewState = viewModel.state.collectAsState(initial = ProductListViewState.Loading)
    when (viewState.value) {
        is ProductListViewState.Loading -> {
            LoadingScreen()
        }

        is ProductListViewState.Success -> {
            val productList = (viewState.value as ProductListViewState.Success).data
            ProductGrid(productList, viewModel)
        }

        is ProductListViewState.Error -> {
            (viewState.value as ProductListViewState.Error).throwable.message?.let {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize(),
                ) {
                    RetrySection(it) {
                        Log.d(Constants.APP_TAG, "Exception in getProduct api $it.error")
                        viewModel.sendIntent(ProductListViewIntent.FetchProductListList)
                    }
                }
            }
        }
    }

    val viewEffect by viewModel.sideEffect.collectAsState(ProductListSideEffect.Idle)

    LaunchedEffect(viewEffect) {
        when (viewEffect) {
            is ProductListSideEffect.NavigateToProductDetails -> {
                val productId = (viewEffect as ProductListSideEffect.NavigateToProductDetails).id
                navController.navigate("${Screen.ProductDetailsScreen.route}/$productId")
            }

            else -> {}
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

@Composable
private fun ProductGrid(
    products: List<Product>,
    viewModel: ProductListViewModel,
) {
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
                            stringResource(R.string.product_list),
                            fontSize = fontSize_20,
                        )
                    },
                )
                // Add your other content here
                LazyVerticalGrid(
                    columns = GridCells.Fixed(gridColumnCount),
                    content = {
                        itemsIndexed(products) { _, product ->
                            ProductItem(product, viewModel)
                        }
                    },
                )
            }
        }
    }
}

@Composable
private fun ProductItem(
    product: Product,
    viewModel: ProductListViewModel,
) {
    Card(
        shape = RoundedCornerShape(SIZE_8DP),
        elevation = CardDefaults.cardElevation(),
        modifier = Modifier
            .padding(SIZE_8DP)
            .fillMaxWidth()
            .clickable {
                viewModel.sendIntent(ProductListViewIntent.OnProductItemClick(product.id))
            },
    ) {
        Column(
            modifier = Modifier
                .padding(SIZE_8DP)
                .background(Color.White),
        ) {
            Image(
                painter = rememberAsyncImagePainter(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(product.image)
                        .crossfade(true)
                        .build(),
                ),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(SIZE_200DP)
                    .clip(RoundedCornerShape(SIZE_8DP)),
            )
            Spacer(modifier = Modifier.height(SIZE_8DP))
            Text(
                text = product.title ?: stringResource(R.string.product_title),
                style = MaterialTheme.typography.titleMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
            Spacer(modifier = Modifier.height(SIZE_8DP))
            Text(
                text = product.price.toString(),
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
            )
        }
    }
}
