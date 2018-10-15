package io.kaeawc.storage

import android.content.SharedPreferences
import androidx.core.content.edit
import org.threeten.bp.Instant
import javax.inject.Inject

class Prefs @Inject constructor(val sharedPrefs: SharedPreferences) {

    var identityId: String?
        get() = sharedPrefs.getString("identityId", null)
        set(value) = sharedPrefs.edit { putString("identityId", value) }

    var lastRepositoryFetch: Instant
        get() = Instant.ofEpochMilli(sharedPrefs.getLong("lastRepositoryFetch", 0))
        set(value) = sharedPrefs.edit { putLong("lastRepositoryFetch", value.toEpochMilli()) }
}
