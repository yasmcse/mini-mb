package com.moneybox.minimb.feature.home.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Snackbar
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.moneybox.minimb.common.AuthSessionManager
import com.moneybox.minimb.common.NetworkResult.*
import com.moneybox.minimb.common.model.products.AllProducts
import com.moneybox.minimb.common.MoneyBoxNavigator
import com.moneybox.minimb.common.model.products.Product
import com.moneybox.minimb.common.utils.NetworkStatus
import com.moneybox.minimb.data.designsystem.component.CircularProgressComposable
import com.moneybox.minimb.data.designsystem.theme.MoneyBoxAppTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import com.moneybox.minimb.common.R as commonR
import com.moneybox.minimb.feature.home.R as homeR

@AndroidEntryPoint
class HomeFragment : Fragment() {
    @Inject
    lateinit var networkStatus: NetworkStatus

    @Inject
    lateinit var navigator: MoneyBoxNavigator

    @Inject
    lateinit var sessionManager: AuthSessionManager

    private val viewModel: HomeViewModel by viewModels()

    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        onBackPressed()
        return ComposeView(requireContext()).apply {
            setContent {
                MoneyBoxAppTheme {
                    Scaffold {
                        HomeScreen()
                    }
                }
            }
        }
    }

    @SuppressLint("NewApi")
    @Composable
    fun HomeScreen() {
        val products by viewModel.products.collectAsStateWithLifecycle()

        LaunchedEffect(key1 = "key1") {
            viewModel.getInvestorProducts()
        }

        when {
            networkStatus.hasNetworkAccess(activity as FragmentActivity) -> {
                when (products) {
                    is Loading -> Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = stringResource(id = homeR.string.portfolio_loading),
                            style = MaterialTheme.typography.subtitle1,
                            color = MaterialTheme.colors.primary,
                            textAlign = TextAlign.Center
                        )
                        CircularProgressComposable(
                            Modifier
                                .size(100.dp)
                                .padding(top = 5.dp)
                        )
                    }

                    is Success -> {
                        HomeContent(products = (products as Success<AllProducts>).data)
                    }

                    is Error -> {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = (products as Error).message,
                                style = MaterialTheme.typography.subtitle1,
                                color = MaterialTheme.colors.error,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                    is NoState -> {}
                }
            }
            else -> {
                Snackbar(
                    modifier = Modifier.fillMaxWidth(),
                    backgroundColor = MaterialTheme.colors.error
                ) {
                    Text(
                        text = stringResource(id = commonR.string.no_internet),
                        style = MaterialTheme.typography.caption,
                        color = MaterialTheme.colors.error
                    )
                }
            }
        }
    }

    @Composable
    fun HomeContent(products: AllProducts) {
        Surface {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 50.dp)
            ) {
                Text(
                    text = stringResource(id = commonR.string.total_plan_value),
                    modifier = Modifier
                        .fillMaxWidth(),
                    style = MaterialTheme.typography.h6,
                    color = MaterialTheme.colors.secondary,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = products.totalPlanValue.toString(),
                    modifier = Modifier
                        .fillMaxWidth(),
                    style = MaterialTheme.typography.h6,
                    color = MaterialTheme.colors.secondary,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(40.dp))

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp)
                ) {
                    LazyColumn(content = {
                        items(products.products.size) { index ->
                            ProductItem(
                                product = products.products[index]
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                        }
                    })
                }
            }
        }
    }

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    fun ProductItem(product: Product) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(top = 5.dp, bottom = 5.dp)
                .border(
                    width = 2.dp,
                    color = MaterialTheme.colors.primary,
                    shape = MaterialTheme.shapes.medium
                ),
            onClick = {
            }
        ) {
            Column(modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)) {
                Text(
                    text = product.product.friendlyName,
                    modifier = Modifier
                        .fillMaxWidth(),
                    style = MaterialTheme.typography.subtitle1,
                    color = MaterialTheme.colors.secondary,
                    textAlign = TextAlign.Center,
                )
                Spacer(modifier = Modifier.height(5.dp))
                Row {
                    Text(
                        text = stringResource(id = homeR.string.plan_value),
                        modifier = Modifier
                            .weight(1f)
                            .padding(3.dp),
                        style = MaterialTheme.typography.subtitle1,
                        color = MaterialTheme.colors.secondary
                    )
                    Text(
                        text = product.planValue.toString(),
                        modifier = Modifier
                            .weight(1f)
                            .padding(3.dp),
                        style = MaterialTheme.typography.subtitle1,
                        color = MaterialTheme.colors.secondary
                    )
                }

                Spacer(modifier = Modifier.height(5.dp))
                Row {
                    Text(
                        text = stringResource(id = homeR.string.money_box),
                        modifier = Modifier
                            .weight(1f)
                            .padding(3.dp),
                        style = MaterialTheme.typography.subtitle1,
                        color = MaterialTheme.colors.secondary
                    )
                    Text(
                        text = product.moneybox.toString(),
                        modifier = Modifier
                            .weight(1f)
                            .padding(3.dp),
                        style = MaterialTheme.typography.subtitle1,
                        color = MaterialTheme.colors.secondary
                    )
                }
            }
        }
    }

    private fun onBackPressed() {
        activity?.onBackPressedDispatcher?.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    with(navigator) {
                        if (!sessionManager.isActive()) {
                            navigateToMainActivity()
                        }
                    }
                }
            }
        )
    }
}