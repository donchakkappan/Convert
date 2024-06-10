package com.allutils.feature_currency.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import com.allutils.app_style_guide.theme.ConvertTheme
import com.allutils.base.presentation.activity.BaseFragment
import com.allutils.feature_currency.R
import com.allutils.feature_currency.presentation.conversion.HomeScreen
import org.koin.androidx.navigation.koinNavGraphViewModel

class ConversionListFragment : BaseFragment() {

    private val model: ConversionListViewModel by koinNavGraphViewModel(R.id.nav_graph_currencies)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                ConvertTheme {
                    HomeScreen(model)
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        model.onEnter()
    }
}