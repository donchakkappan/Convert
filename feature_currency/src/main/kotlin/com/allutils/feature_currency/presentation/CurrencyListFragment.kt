package com.allutils.feature_currency.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.compose.ui.platform.ComposeView
import androidx.core.view.isVisible
import androidx.core.view.updateLayoutParams
import androidx.lifecycle.lifecycleScope
import com.allutils.app_style_guide.theme.ConvertTheme
import com.allutils.base.presentation.activity.BaseActivity
import com.allutils.base.presentation.activity.BaseActivity.Companion.mainActivity
import com.allutils.base.presentation.activity.BaseFragment
import com.allutils.base.presentation.ext.hideKeyboard
import com.allutils.base.presentation.ext.initSearchBehaviour
import com.allutils.base.presentation.ext.showKeyboard
import com.allutils.feature_currency.R
import com.allutils.feature_currency.presentation.composables.HomeScreen
import org.koin.androidx.navigation.koinNavGraphViewModel

class CurrencyListFragment : BaseFragment() {

    private val model: CurrencyListViewModel by koinNavGraphViewModel(R.id.nav_graph_currencies)

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

        mainActivity.searchTextInputEditText?.let { editText ->
            editText.initSearchBehaviour(
                viewLifecycleOwner.lifecycleScope,
                MINIMUM_PRODUCT_QUERY_SIZE,
                DELAY_BEFORE_SUBMITTING_QUERY,
                object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        editText.hideKeyboard()
                        configureDefaultAppBar(mainActivity)
                        model.onEnter(query)
                        return true
                    }

                    override fun onQueryTextChange(newText: String?): Boolean {
                        if (newText.isNullOrBlank()) {
                            editText.hideKeyboard()
                            configureDefaultAppBar(mainActivity)
                        }
                        return true
                    }
                },
            ).also { editText.text?.clear() }
        }
    }

    companion object {
        const val MINIMUM_PRODUCT_QUERY_SIZE = 1

        const val DELAY_BEFORE_SUBMITTING_QUERY = 500L

        fun configureAppBar(baseActivity: BaseActivity) {
            baseActivity.apply {
                appBarLayout?.apply {
                    elevation = 0f
                    isVisible = true
                }

                mainAppToolbar?.layoutTransition = null
                appBarLayout?.layoutTransition = null

                configureDefaultAppBar(baseActivity)
            }
        }

        private fun configureDefaultAppBar(baseActivity: BaseActivity) {
            baseActivity.apply {
                searchTextInputEditText?.hideKeyboard()
                searchLayout?.updateLayoutParams {
                    width = ViewGroup.LayoutParams.WRAP_CONTENT
                }
                searchTextInputLayout?.apply {
                    isVisible = false
                }
                mainAppToolbar?.apply {
                    post {
                        setTitle(R.string.conversion_rates)
                        logo = null
                    }
                    menu?.clear()
                    inflateMenu(R.menu.menu_toolbar_main)
                    setOnMenuItemClickListener { _ ->
                        configureSearchAppBar(baseActivity)
                        true
                    }
                    logo = null
                }
            }
        }

        private fun configureSearchAppBar(baseActivity: BaseActivity) {
            baseActivity.apply {
                searchLayout?.updateLayoutParams {
                    width = ViewGroup.LayoutParams.MATCH_PARENT
                }

                searchTextInputLayout.apply {
                    this?.isVisible = true
                }

                mainAppToolbar.apply {
                    this?.title = null
                    this?.setNavigationOnClickListener {
                        configureDefaultAppBar(
                            baseActivity,
                        )
                    }
                    this?.menu?.clear()
                    this?.logo = null
                }

                searchTextInputEditText?.let {
                    it.post {
                        it.requestFocus()
                        it.showKeyboard()
                    }
                }
            }
        }
    }
}