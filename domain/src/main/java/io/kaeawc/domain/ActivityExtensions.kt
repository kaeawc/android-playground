package io.kaeawc.domain

import android.app.Activity
import android.content.Intent

fun Activity.blur() {

}

fun Activity.routeTo(intent: Intent) {
    startActivity(intent)
}
