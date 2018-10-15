package io.kaeawc.domain

import android.content.Context
import android.content.Intent
import timber.log.Timber
import kotlin.reflect.KClass

abstract class Router {

    private fun intent(context: Context, clazz: KClass<*>): Intent {
        val screen = clazz.java.simpleName.replace("Activity", "")
        Timber.d("Routing to $screen")
        return Intent(context, clazz.java)
    }

    fun launch(context: Context) = intent(context, launch())
    fun viewRepository(context: Context, repository: Repository): Intent {
        return intent(context, viewRepository()).apply {
            putExtra("NAME", repository.name)
        }
    }

    abstract fun launch(): KClass<*>
    abstract fun viewRepository(): KClass<*>
}
