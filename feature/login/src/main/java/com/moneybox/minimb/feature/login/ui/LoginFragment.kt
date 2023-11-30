package com.moneybox.minimb.feature.login.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalMinimumInteractiveComponentEnforcement
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Snackbar
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.moneybox.minimb.common.model.login.Login
import com.moneybox.minimb.common.MoneyBoxNavigator
import com.moneybox.minimb.common.NetworkResult.*
import com.moneybox.minimb.data.designsystem.component.CircularProgressComposable
import com.moneybox.minimb.data.designsystem.component.MBTextField
import com.moneybox.minimb.data.designsystem.theme.MoneyBoxAppTheme
import com.moneybox.minimb.feature.login.model.MainEvent
import com.moneybox.minimb.feature.login.R as loginR
import com.moneybox.minimb.common.R as commonR
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : Fragment() {
    @Inject
    lateinit var navigator: MoneyBoxNavigator

    private val viewModel: LoginViewModel by viewModels()

    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                MoneyBoxAppTheme {
                    Scaffold {
                        LoginScreen()
                    }
                }
            }
        }
    }

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    fun LoginScreen() {
        val loginState by viewModel.loginState.collectAsStateWithLifecycle()

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            Box {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(100.dp))
                    Image(
                        painter = painterResource(id = commonR.drawable.moneybox_logo),
                        contentDescription = null,
                        modifier = Modifier
                            .height(40.dp)
                            .width(182.dp)
                    )
                    Spacer(modifier = Modifier.height(50.dp))
                    Text(
                        text = stringResource(id = loginR.string.email),
                        style = MaterialTheme.typography.caption,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                            .padding(bottom = 8.dp)
                    )
                    MBTextField(
                        placeholder = stringResource(id = loginR.string.email),
                        text = viewModel.formState.email,
                        onValueChange = {
                            viewModel.onEvent(MainEvent.EmailChanged(it))
                        },
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        singleLine = true,
                        isError = viewModel.formState.emailError != null,
                        errorMessage = viewModel.formState.emailError,
                    )

                    Text(
                        text = stringResource(id = loginR.string.password),
                        style = MaterialTheme.typography.caption,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                            .padding(vertical = 8.dp)
                    )
                    MBTextField(
                        placeholder = stringResource(id = loginR.string.password),
                        text = viewModel.formState.password,
                        onValueChange = {
                            viewModel.onEvent(MainEvent.PasswordChanged(it))
                        },
                        keyboardType = KeyboardType.Password,
                        ImeAction.Done,
                        trailingIcon = {
                            CompositionLocalProvider(LocalMinimumInteractiveComponentEnforcement provides false) {
                                IconButton(
                                    onClick =
                                    {
                                        viewModel.onEvent(MainEvent.VisiblePassword(!(viewModel.formState.isVisiblePassword)))
                                    }
                                ) {
                                    Icon(
                                        painter = if (viewModel.formState.isVisiblePassword) painterResource(
                                            id = loginR.drawable.ic_visible
                                        ) else painterResource(
                                            id = loginR.drawable.ic_invisible
                                        ),
                                        contentDescription = loginR.string.visible.toString(),
                                        tint = MaterialTheme.colors.primary,
                                        modifier = Modifier
                                            .size(20.dp)
                                            .padding(end = 5.dp)
                                    )
                                }
                            }
                        },
                        isVisible = viewModel.formState.isVisiblePassword,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        singleLine = true,
                        isError = viewModel.formState.passwordError != null,
                        errorMessage = viewModel.formState.passwordError
                    )
                }
                Button(
                    onClick = {
                        viewModel.onEvent(MainEvent.Submit)
                    },
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, top = 10.dp, bottom = 60.dp)
                        .align(Alignment.BottomCenter),
                    colors = ButtonDefaults.buttonColors(
                        contentColor = MaterialTheme.colors.onPrimary,
                    )
                ) {
                    Text(
                        text = stringResource(id = loginR.string.login),
                        color = MaterialTheme.colors.onPrimary
                    )
                }
                when (loginState) {
                    is Error -> {
                        (loginState as? Error)?.message.let {
                            Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
                        }
                    }

                    is Success -> {
                        (loginState as? Success<Login>)?.data?.session?.bearerToken.let {
                            if (it != null) {
                                with(navigator) {
                                    navigateToHomeScreen()
                                }
                            }
                        }
                    }

                    is Loading -> Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 60.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = stringResource(id = loginR.string.trying_to_login),
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

                    is NoState -> {}
                }
            }
        }
    }
}