package org.mathieu.data.local

import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.query
import org.mathieu.data.local.objects.LocationObject

/**
 * Local data source for locations.
 * @param database The local database.
 */
internal class LocationLocal(private val database: RealmDatabase) {

    /**
     * Get a location from the local database.
     * @param id The unique identifier for the location.
     */
    suspend fun getLocation(id: Int): LocationObject? = database.use {
        query<LocationObject>("id == $id").first().find()
    }

    /**
     * Save a list of locations to the local database.
     * @param locations The list of locations to save.
     */
    suspend fun insert(location: LocationObject) {
        database.write {
            copyToRealm(location, UpdatePolicy.ALL)
        }
    }

}