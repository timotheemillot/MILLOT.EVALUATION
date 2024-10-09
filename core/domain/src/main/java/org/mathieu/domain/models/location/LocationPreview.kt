package org.mathieu.domain.models.location

import org.mathieu.domain.models.character.Character

/**
 * Represents a simplified version of a location without the list of residents.
 *
 * @property id The unique identifier for the location.
 * @property name The name of the location.
 * @property type The type or category of the location.
 * @property dimension The specific dimension or universe where this location exists.
 */
data class LocationPreview(
    val id: Int,
    val name: String,
    val type: String,
    val dimension: String,
    )

