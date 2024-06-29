package com.allutils.feature_currency.presentation

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.runtime.Composable
import com.allutils.feature_currency.presentation.basecode.BasecodeBottomSheet
import com.allutils.feature_currency.presentation.basecode.BasecodeViewModel
import com.allutils.feature_currency.presentation.countries.CountriesBottomSheet
import com.allutils.feature_currency.presentation.countries.CountriesViewModel
import com.allutils.feature_currency.presentation.home.ConversionListViewModel

enum class BottomSheetType {
    BASE_CODE_LIST,
    COUNTRIES_LIST
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
internal fun BottomSheetLayouts(
    basecodeViewModel: BasecodeViewModel,
    countriesViewModel: CountriesViewModel,
    conversionsViewModel: ConversionListViewModel? = null,
    bottomSheetType: BottomSheetType,
    sheetState: ModalBottomSheetState,
    closeSheet: () -> Unit
) {
    when (bottomSheetType) {
        BottomSheetType.BASE_CODE_LIST -> BasecodeBottomSheet(basecodeViewModel,conversionsViewModel!!, closeSheet)
        BottomSheetType.COUNTRIES_LIST -> CountriesBottomSheet(
            countriesViewModel = countriesViewModel,
            conversionRatesViewModel = conversionsViewModel,
            closeSheet = closeSheet,
            sheetState = sheetState
        )
    }
}
