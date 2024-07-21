package com.allutils.convert.presentation

import android.os.Bundle
import android.view.View
import androidx.navigation.dynamicfeatures.fragment.ui.AbstractProgressFragment
import com.airbnb.lottie.LottieAnimationView
import com.allutils.convert.R

class ProgressFragment : AbstractProgressFragment(R.layout.fragment_progress) {

    private var animationView: LottieAnimationView? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(view) {
            animationView = findViewById(R.id.animation_view)
        }
    }

    override fun onProgress(status: Int, bytesDownloaded: Long, bytesTotal: Long) {

    }

    override fun onFailed(errorCode: Int) {
        animationView?.setAnimation(R.raw.error)
    }

    override fun onCancelled() {

    }
}
