package io.kaeawc.launch

import android.content.Context
import android.content.Intent
import androidx.recyclerview.widget.DiffUtil
import arrow.core.Try
import io.kaeawc.domain.Repository
import io.kaeawc.domain.Router
import io.kaeawc.repos.GithubInteractor
import io.reactivex.Flowable
import javax.inject.Inject

@LaunchScope
open class LaunchPresenter @Inject constructor(
        open val router: Router,
        open val github: GithubInteractor) {

    fun getAdapterDiffResults(): Flowable<Try<Pair<List<Repository>, DiffUtil.DiffResult>>> =
            github.getStreamingDiffResultOfRepositories()

    open fun onRepositoryTapped(context: Context, repository: Repository): Intent {
        return router.viewRepository(context, repository)
    }
}
