package com.stevendevit.pampa.view

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageButton
import androidx.appcompat.widget.AppCompatImageView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.mancj.slideup.SlideUp
import com.mancj.slideup.SlideUpBuilder
import com.stevendevit.domain.interfaces.ISpeechListener
import com.stevendevit.pampa.R
import com.stevendevit.pampa.module.KoinApp

/**
 * Created by stevendevit on 15/01/2020.
 * tankadeveloper@gmail.com
 */
class CommandGuiView : FrameLayout {

    private lateinit var slideUp: SlideUp
    private lateinit var fabButton: FloatingActionButton
    private lateinit var scaleAnimator: ObjectAnimator
    private lateinit var delegate: ISpeechListener

    constructor(context: Context) : super(context) {
        setup()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        setup()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        setup()
    }

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes) {
        setup()
    }

    fun setDelegate(delegate: ISpeechListener) {

        this.delegate = delegate
    }

    fun startListeningForCommand() {
        scaleAnimator.start()

        KoinApp.Speech.startListening(delegate)
    }

    fun stopListeningForCommand() {

        scaleAnimator.cancel()
        KoinApp.Speech.stopAll()
    }

    private fun setup() {

        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.view_command_gui, this, true)

        val btnListenCmd = findViewById<ImageButton>(R.id.btnCommand)

        btnListenCmd.setOnClickListener {
            stopListeningForCommand()
            startListeningForCommand()
        }

        scaleAnimator = ObjectAnimator.ofPropertyValuesHolder(
            btnListenCmd,
            PropertyValuesHolder.ofFloat("scaleX", 0.9f),
            PropertyValuesHolder.ofFloat("scaleY", 0.9f)
        )

        scaleAnimator.duration = 700L
        scaleAnimator.repeatMode = ValueAnimator.REVERSE
        scaleAnimator.repeatCount = ValueAnimator.INFINITE

        val dim = findViewById<FrameLayout>(R.id.dim)

        dim.setOnClickListener {

            hideOverlayShowFab()
        }

        fabButton = findViewById(R.id.fab)
        fabButton.setOnClickListener {

            fabButton.hide()
            show()
        }

        slideUp = SlideUpBuilder(findViewById(R.id.container))
            .withStartState(SlideUp.State.HIDDEN)
            .withStartGravity(Gravity.BOTTOM)
//            .withSlideFromOtherView(
//                findViewById(R.id.command_gui_root)
//            )
            .withListeners(object : SlideUp.Listener.Events {
                override fun onVisibilityChanged(visibility: Int) {

                    if (fabButton.visibility == View.GONE) {
                        if (visibility == View.GONE) {
                            fabButton.show()
                        }
                    }
                }

                override fun onSlide(percent: Float) {

                    val alpha = 1 - (percent / 100)

                    if (alpha <= 0.7) {
                        dim.alpha = alpha
                    }
                }
            })
            .build()

        findViewById<AppCompatImageView>(R.id.btnHide).setOnClickListener {
            hideOverlayShowFab()
        }
    }

    private fun hideOverlayShowFab() {
        hide()
        fabButton.show()
    }

    fun canGoBack(): Boolean {

        return !this.slideUp.isVisible
    }

    fun show() {
        this.slideUp.show()
    }

    fun hide() {

        this.slideUp.hide()
    }
}
