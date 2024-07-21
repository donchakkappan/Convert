package com.allutils.feature_scanner.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.codescanner.GmsBarcodeScanner
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ScannerViewModel(private val scanner: GmsBarcodeScanner) : ViewModel() {

    private val _state = MutableStateFlow(MainState())
    val state = _state.asStateFlow()

    fun startScanning() {
        viewModelScope.launch {
            scanner.startScan()
                .addOnSuccessListener {
                    launch {
                        _state.value = state.value.copy(
                            scannedData = getDetails(it)
                        )
                    }
                }.addOnFailureListener {
                    it.printStackTrace()
                }
        }
    }

    private fun getDetails(barcode: Barcode): String {
        return when (barcode.valueType) {
            Barcode.TYPE_WIFI -> {
                val ssid = barcode.wifi!!.ssid
                val password = barcode.wifi!!.password
                val type = barcode.wifi!!.encryptionType
                "ssid : $ssid, password : $password, type : $type"
            }

            Barcode.TYPE_URL -> {
                "url : ${barcode.url!!.url}"
            }

            Barcode.TYPE_PRODUCT -> {
                "productType : ${barcode.displayValue}"
            }

            Barcode.TYPE_EMAIL -> {
                "email : ${barcode.email}"
            }

            Barcode.TYPE_CONTACT_INFO -> {
                "contact : ${barcode.contactInfo}"
            }

            Barcode.TYPE_PHONE -> {
                "phone : ${barcode.phone}"
            }

            Barcode.TYPE_CALENDAR_EVENT -> {
                "calender event : ${barcode.calendarEvent}"
            }

            Barcode.TYPE_GEO -> {
                "geo point : ${barcode.geoPoint}"
            }

            Barcode.TYPE_ISBN -> {
                "isbn : ${barcode.displayValue}"
            }

            Barcode.TYPE_DRIVER_LICENSE -> {
                "driving license : ${barcode.driverLicense}"
            }

            Barcode.TYPE_SMS -> {
                "sms : ${barcode.sms}"
            }

            Barcode.TYPE_TEXT -> {
                "text : ${barcode.rawValue}"
            }

            Barcode.TYPE_UNKNOWN -> {
                "unknown : ${barcode.rawValue}"
            }

            else -> {
                "Couldn't determine"
            }
        }

    }

    data class MainState(
        val scannedData: String = "",
    )

}
