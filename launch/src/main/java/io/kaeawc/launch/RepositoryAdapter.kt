package io.kaeawc.launch

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.kaeawc.domain.Repository
import io.reactivex.subjects.PublishSubject

class RepositoryAdapter(
        var repositories: List<Repository>
) : RecyclerView.Adapter<RepositoryViewHolder>() {

    val clicks = PublishSubject.create<Repository>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.repository_item, parent, false)
        return RepositoryViewHolder(view)
    }

    override fun getItemCount(): Int {
        return repositories.size
    }

    override fun onBindViewHolder(holder: RepositoryViewHolder, position: Int) {
        val repository = repositories.getOrNull(position) ?: return
        holder.onBind(repository, clicks)
    }
}
