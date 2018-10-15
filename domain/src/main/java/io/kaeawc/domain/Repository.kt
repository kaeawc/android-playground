package io.kaeawc.domain

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.threeten.bp.Instant

@Entity(tableName = "repository")
data class Repository(
        @PrimaryKey() val name: String,
        val created: Instant) : Comparable<Repository> {

    override fun equals(other: Any?): Boolean {
        return this.name == (other as? Repository)?.name
    }

    override fun hashCode(): Int {
        return name.hashCode()
    }

    override fun compareTo(other: Repository): Int {
        return (other.created.toEpochMilli() - created.toEpochMilli()).toInt()
    }
}
