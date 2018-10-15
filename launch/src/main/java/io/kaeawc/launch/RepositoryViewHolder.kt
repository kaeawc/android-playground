package io.kaeawc.launch

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import io.kaeawc.domain.Repository
import io.reactivex.subjects.PublishSubject

class RepositoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun onBind(repository: Repository, publishSubject: PublishSubject<Repository>) {
        // TODO: show name, stars, language, last updated

        itemView.setOnClickListener {
            publishSubject.onNext(repository)
        }
    }
}
