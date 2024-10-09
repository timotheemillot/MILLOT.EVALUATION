package org.mathieu.domain.repositories

import org.mathieu.domain.models.location.Location

interface LocationRepository {

    suspend fun getLocation(id: Int): Location

}