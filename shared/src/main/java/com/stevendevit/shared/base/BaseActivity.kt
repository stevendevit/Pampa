package com.stevendevit.shared.base

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import com.stevendevit.shared.R
import com.stevendevit.shared.usecase.Failure

/**
 * Created by stevendevit on 10/01/2020.
 * tankadeveloper@gmail.com
 */

abstract class BaseActivity : AppCompatActivity() {

    @get:LayoutRes
    abstract val layoutResId: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutResId)
        onViewCanObserve()
    }

    abstract fun onViewCanObserve()

    open fun handleError(result: Failure) {

        showError(result.failureCause)
    }

    protected fun showLoading() {
        findViewById<ProgressBar>(R.id.progressView).visibility = View.VISIBLE
    }

    protected fun hideLoading() {
        findViewById<ProgressBar>(R.id.progressView).visibility = View.INVISIBLE
    }

    protected fun showToast(text: String, length: Int = Toast.LENGTH_SHORT) {

        Toast.makeText(this, text, length).show()
    }

    protected fun showError(text: String, length: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(this, text, length).show()
    }
}