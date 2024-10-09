package org.mathieu.domain.repositories

import org.mathieu.domain.models.location.Location

interface LocationRepository {

    /**
     * Fetches a list of locations from the data source. The function streams the results
     * as a [Flow] of [List] of [Location] objects.
     *
     * @return A flow emitting a list of locations.
     */
    suspend fun getLocation(id: Int): Location

}