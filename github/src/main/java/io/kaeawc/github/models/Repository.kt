package io.kaeawc.github.models

import org.threeten.bp.Instant
import io.kaeawc.domain.Repository as DomainRepository

data class Repository(val name: String?, val created: Instant?)
