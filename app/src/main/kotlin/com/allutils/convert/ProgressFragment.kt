package com.allutils.convert

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.navigation.dynamicfeatures.fragment.ui.AbstractProgressFragment

class ProgressFragment : AbstractProgressFragment(R.layout.fragment_progress) {

    private var message: TextView? = null
    private var progressBar: ProgressBar? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(view) {
            message = findViewById(R.id.message)
            progressBar = findViewById(R.id.progressBar)
        }
    }

    override fun onProgress(status: Int, bytesDownloaded: Long, bytesTotal: Long) {
        progressBar?.progress = (bytesDownloaded.toDouble() * 100 / bytesTotal).toInt()
    }

    override fun onFailed(errorCode: Int) {
        message?.text = "Failed"
    }

    override fun onCancelled() {
        message?.text = "Cancelled"
    }
}
