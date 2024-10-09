package org.mathieu.data.local

import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.mathieu.data.local.objects.CharacterObject

/**
 * Local data source for characters.
 * @param database The local database.
 */
internal class CharacterLocal(private val database: RealmDatabase) {

    /**
     * Get all characters from the local database.
     * @return A flow of the list of characters.
     */
    suspend fun getCharacters(): Flow<List<CharacterObject>> = database.use {
        query<CharacterObject>().find().asFlow().map { it.list }
    }

    /**
     * Get a character from the local database.
     * @param id The unique identifier for the character.
     */
    suspend fun getCharacter(id: Int): CharacterObject? = database.use {
        query<CharacterObject>("id == $id").first().find()
    }

    /**
     * Save a list of characters to the local database.
     * @param characters The list of characters to save.
     */
    suspend fun saveCharacters(characters: List<CharacterObject>) = characters.onEach {
        insert(it)
    }


    suspend fun insert(character: CharacterObject) {
        database.write {
            copyToRealm(character, UpdatePolicy.ALL)
        }
    }

}