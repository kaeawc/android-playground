package io.kaeawc.storage

import android.content.SharedPreferences
import androidx.core.content.edit
import org.threeten.bp.Instant
import javax.inject.Inject

class Prefs @Inject constructor(val sharedPrefs: SharedPreferences) {

    var identityId: String?
        get() = sharedPrefs.getString(javaClass.enclosingMethod.name, null)
        set(value) = sharedPrefs.edit { putString(javaClass.enclosingMethod.name, value) }

    var lastRepositoryFetch: Instant
        get() = Instant.ofEpochMilli(sharedPrefs.getLong(javaClass.enclosingMethod.name, 0))
        set(value) = sharedPrefs.edit { putLong(javaClass.enclosingMethod.name, value.toEpochMilli()) }
}
