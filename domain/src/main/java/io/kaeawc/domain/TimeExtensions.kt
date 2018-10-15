package io.kaeawc.domain

import org.threeten.bp.Duration
import org.threeten.bp.Instant

fun Instant.moreThanHoursAgo(value: Long, now: Instant = Instant.now()): Boolean {
    return isBefore(now.minus(Duration.ofHours(value)))
}

