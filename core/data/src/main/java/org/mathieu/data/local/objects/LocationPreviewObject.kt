package org.mathieu.data.local.objects

import io.realm.kotlin.types.RealmObject
import org.mathieu.domain.models.character.LocationPreview

/**
 * Represents a minimal description of a location without the characters that reside in it.
 *
 * @property id The unique identifier for the location.
 * @property name The name of the location.
 * @property type The type of location.
 * @property dimension The dimension where the location is located.
 */
internal class LocationPreviewObject: RealmObject {
    var id: Int = -1
    var name: String = ""
    var type: String = ""
    var dimension: String = ""
}

/**
 * Converts a [LocationPreview] object to a [LocationPreviewObject] object.
 * @return A [LocationPreviewObject] object.
 */
internal fun LocationPreview.toRealmObject() = LocationPreviewObject().also { obj ->
    obj.id = id
    obj.name = name
    obj.type = type
    obj.dimension = dimension
}

/**
 * Converts a [LocationPreviewObject] object to a [LocationPreview] object.
 * @return A [LocationPreview] object.
 */
internal fun LocationPreviewObject.toModel() = LocationPreview(
    id = id,
    name = name,
    type = type,
    dimension = dimension
)