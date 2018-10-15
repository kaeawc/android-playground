package io.kaeawc.launch

import androidx.recyclerview.widget.DiffUtil
import arrow.core.Try
import io.kaeawc.domain.Repository
import io.kaeawc.repos.GithubRepository
import io.reactivex.Flowable
import javax.inject.Inject

@LaunchScope
open class LaunchInteractor @Inject constructor(open val github: GithubRepository) {

    fun getStreamingDiffOfRepositories(): Flowable<Try<Pair<List<Repository>, DiffUtil.DiffResult>>> {
        return github.getStreamingDiffResultOfAll()
    }
}
