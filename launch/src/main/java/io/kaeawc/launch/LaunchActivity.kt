package io.kaeawc.launch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.MainThread
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import io.kaeawc.domain.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_launch.*
import javax.inject.Inject

class LaunchActivity : AppCompatActivity() {

    @Inject lateinit var presenter: LaunchPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch)
        (application as LaunchApp).getLaunchComponent().inject(this)
    }

    override fun onResume() {
        super.onResume()

        repositories?.layoutManager = LinearLayoutManager(baseContext)
        val adapter = RepositoryAdapter(emptyList())
        repositories?.adapter = adapter

        /**
         * Given changes to GitHub [Repository] records we should calculate
         * the DiffResult on a background thread. Once that is completed we
         * can update the [RepositoryAdapter] with the latest results.
         */
        presenter.getAdapterDiffResults()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ it.map(this::applyGithubChanges) }, {})
                .dispose(this, Lifecycle.Event.ON_PAUSE)

        /**
         * Given any clicks on the adapter start blurring the current Window
         * on a computation thread. Once that is completed handle routing to
         * the [Repository] detail view.
         */
        adapter.clicks
                .subscribeOn(Schedulers.computation())
                .doOnNext { blur() }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onRepositoryTapped) {}
                .dispose(this, Lifecycle.Event.ON_PAUSE)
    }

    @MainThread
    private fun applyGithubChanges(result: Pair<List<Repository>, DiffUtil.DiffResult>) {
        val adapter = (repositories?.adapter as? RepositoryAdapter) ?: return
        val (records, diffResult) = result
        adapter.repositories = records
        diffResult.dispatchUpdatesTo(adapter)
    }

    @MainThread
    private fun onRepositoryTapped(repository: Repository) {
        routeTo(presenter.onRepositoryTapped(baseContext, repository))
    }
}
