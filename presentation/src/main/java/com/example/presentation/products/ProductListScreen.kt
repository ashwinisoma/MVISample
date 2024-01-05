package com.example.presentation.products

import android.util.Log
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.NavController
import com.example.domain.common.Constants
import com.example.presentation.R
import com.example.presentation.constant.gridColumnCount
import com.example.presentation.constant.productItemMaxLines
import com.example.presentation.customcomposable.ImageWidget
import com.example.presentation.customcomposable.LoadingScreen
import com.example.presentation.customcomposable.MyTopBar
import com.example.presentation.customcomposable.RetryWidget
import com.example.presentation.customcomposable.TextWidget
import com.example.presentation.model.Product
import com.example.presentation.navigation.Screen
import com.example.presentation.ui.theme.SIZE_200DP
import com.example.presentation.ui.theme.SIZE_8DP
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

        viewModel.sideEffect.collect { sideEffect ->
            when (sideEffect) {
                is ProductListSideEffect.NavigateToProductDetails -> {
                    val productId = sideEffect.id
                    navController.navigate("${Screen.ProductDetailsScreen.route}/$productId")
                }
            }
        }
    }
    val viewState by viewModel.state.collectAsState()
    ProductListState(viewState, viewModel, navController)
}

@Composable
private fun ProductListState(
    viewState: ProductListViewState,
    viewModel: ProductListViewModel,
    navController: NavController
) {
    when (viewState) {
        is ProductListViewState.Loading -> {
            LoadingScreen()
        }
        is ProductListViewState.Success -> {
            val productList = viewState.data
            ProductGrid(productList, viewModel, navController)
        }
        is ProductListViewState.Error -> {
            viewState.throwable.message?.let {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize(),
                ) {
                    RetryWidget(it) {
                        Log.d(Constants.APP_TAG, Constants.GET_PRODUCTS_API_ERROR + "$it.error")
                        viewModel.sendIntent(ProductListViewIntent.FetchProductListList)
                    }
                }
            }
        }
    }
}
@Composable
private fun ProductGrid(
    products: List<Product>,
    viewModel: ProductListViewModel,
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
            Column {
                MyTopBar(
                    backgroundColor = Color(R.color.topBar),
                    navController = navController,
                    title = {
                        TextWidget(
                            message = stringResource(R.string.product_list),
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
            ImageWidget(
                product.image,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(SIZE_200DP)
                    .clip(RoundedCornerShape(SIZE_8DP)),
            )
            Spacer(modifier = Modifier.height(SIZE_8DP))
            TextWidget(
                message = product.title ?: stringResource(R.string.product_title),
                style = MaterialTheme.typography.titleMedium,
                maxLines = productItemMaxLines,
                overflow = TextOverflow.Ellipsis,
            )
            Spacer(modifier = Modifier.height(SIZE_8DP))
            TextWidget(
                message = product.price.toString(),
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
            )
        }
    }
}
