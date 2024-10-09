package org.mathieu.data.remote.responses

import kotlinx.serialization.Serializable

/**
 * Represents a preview of a location
 * It is a lighter version of the location details.
 *
 * @property id The unique identifier for the location.
 * @property name The name of the location.
 * @property type The type of location.
 * @property dimension The dimension where the location is located.
 */
@Serializable
internal data class LocationPreviewResponse(
    val id: Int,
    val name: String,
    val type: String,
    val dimension: String,
)