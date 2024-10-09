package org.mathieu.data.repositories

import android.content.Context
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import org.mathieu.data.local.CharacterLocal
import org.mathieu.data.local.LocationLocal
import org.mathieu.data.local.objects.CharacterObject
import org.mathieu.data.local.objects.toModel
import org.mathieu.data.local.objects.toRealmObject
import org.mathieu.data.remote.CharacterApi
import org.mathieu.data.remote.LocationApi
import org.mathieu.data.remote.responses.CharacterResponse
import org.mathieu.domain.models.character.Character
import org.mathieu.domain.models.location.Location
import org.mathieu.domain.repositories.CharacterRepository
import org.mathieu.domain.repositories.LocationRepository

internal class LocationRepositoryImpl(
    private val context: Context,
    private val locationApi: LocationApi,
    private val locationLocal: LocationLocal,
    private val characterRepositoryImpl: CharacterRepository
) : LocationRepository {


    /**
     * Fetches the next batch of characters and saves them to local storage.
     *
     * The function follows these steps:
     * 1. Tries to fetch the location from the local storage.
     * 2. If not found locally, it fetches the location from the API.
     * 3. Upon successful API retrieval, it saves the location to local storage.
     * 4. If the location is still not found, it throws an exception.
     *
     * @param id The unique identifier for the location.
     * @return A [Location] object.
     * @throws Exception If the location is not found.
     */
    override suspend fun getLocation(id: Int): Location =
       locationLocal.getLocation(id)?.toModel()
            ?: locationApi.getLocation(id = id)?.let { response ->

                val obj = response.toRealmObject()
                // Fetch characters linked to the location
                obj.residents.forEach { characterId ->
                    characterRepositoryImpl.getCharacter(characterId.toInt())
                }
                locationLocal.insert(obj)
                obj.toModel()
            }
            ?: throw Exception("Location not found.")



}

