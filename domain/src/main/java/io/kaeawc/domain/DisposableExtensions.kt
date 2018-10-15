package io.kaeawc.domain

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import io.reactivex.disposables.Disposable

fun Disposable.dispose(observer: LifecycleOwner, filter: Lifecycle.Event) {
    observer.lifecycle.addObserver(Disposer(this, filter))
}
