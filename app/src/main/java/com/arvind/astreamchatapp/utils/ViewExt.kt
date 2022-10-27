package com.arvind.astreamchatapp.utils

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.GONE
}

fun toast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

inline fun View.snack(
    @StringRes string: Int,
    length: Int = Snackbar.LENGTH_LONG,
    action: Snackbar.() -> Unit = {}
) {
    val snack = Snackbar.make(this, resources.getString(string), length)
    action.invoke(snack)
    snack.show()
}

fun Snackbar.action(
    @StringRes text: Int,
    color: Int? = null,
    listener: (View) -> Unit
) {
    setAction(text, listener)
    color?.let { setActionTextColor(ContextCompat.getColor(context, color)) }
}

internal fun View.longSnackBar(message: String, action: (Snackbar.() -> Unit)? = null) {
    val snackbar = Snackbar.make(this, message, Snackbar.LENGTH_LONG)
    action?.let { snackbar.it() }
    snackbar.show()
}

fun Fragment.hideKeyboard() {
    view?.let { activity?.hideKeyboard(it) }
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}


fun Context.showPermissionRequestDialog(
    title: String,
    body: String,
    callback: () -> Unit
) {
    AlertDialog.Builder(this).also {
        it.setTitle(title)
        it.setMessage(body)
        it.setPositiveButton("Ok") { _, _ ->
            callback()
        }
    }.create().show()
}
