package com.stevendevit.pampa.view

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.FrameLayout
import androidx.appcompat.widget.AppCompatImageView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.mancj.slideup.SlideUp
import com.mancj.slideup.SlideUpBuilder
import com.stevendevit.pampa.R
import com.stevendevit.pampa.module.KoinApp

/**
 * Created by stevendevit on 17/01/2020.
 * tankadeveloper@gmail.com
 */
class CommandDebugView : FrameLayout {

    private lateinit var slideUp: SlideUp
    private lateinit var fabButton: FloatingActionButton

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

    private fun hideOverlayShowFab() {
        hide()
        fabButton.show()
    }

    private fun setup() {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.view_debug_command, this, true)

        findViewById<AppCompatImageView>(R.id.btnHide).setOnClickListener {
            hideOverlayShowFab()
        }

        fabButton = findViewById(R.id.fab)
        fabButton.setOnClickListener {

            fabButton.hide()
            show()
        }

        val editText = findViewById<EditText>(R.id.editField)
        findViewById<Button>(R.id.btnTest).setOnClickListener {

            KoinApp.CommandManager.perform(editText.text.toString(), true)
        }

        slideUp = SlideUpBuilder(findViewById(R.id.container))
            .withStartState(SlideUp.State.HIDDEN)
            .withStartGravity(Gravity.BOTTOM)
            .withListeners(object : SlideUp.Listener.Events {
                override fun onVisibilityChanged(visibility: Int) {

                    if (fabButton.visibility == View.GONE) {
                        if (visibility == View.GONE) {
                            fabButton.show()
                        }
                    }
                }

                override fun onSlide(percent: Float) {

                }
            })
            .build()

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