package com.stevendevit.pampa.view

import android.content.Context
import android.util.AttributeSet
import android.widget.RelativeLayout

/**
 * Created by stevendevit on 17/01/2020.
 * tankadeveloper@gmail.com
 */
class PlayingNowView : RelativeLayout {

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

    private fun setup() {

    }
}