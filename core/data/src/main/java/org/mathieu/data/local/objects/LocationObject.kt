package org.mathieu.data.local.objects

import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.Ignore
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mathieu.data.remote.responses.LocationResponse
import org.mathieu.data.repositories.tryOrNull
import org.mathieu.domain.models.character.Character
import org.mathieu.domain.models.character.CharacterGender
import org.mathieu.domain.models.character.CharacterStatus
import org.mathieu.domain.models.character.LocationPreview
import org.mathieu.domain.models.location.Location

internal class LocationObject: RealmObject {
    @PrimaryKey
    var id: Int = -1
    var name: String = ""
    var type = ""
    var dimension = ""
    @Ignore
    var residents = emptyList<String>()
}


internal fun LocationResponse.toRealmObject() = LocationObject().also { obj ->
    obj.id = id
    obj.name = name
    obj.type = type
    obj.dimension = dimension
    obj.residents = residents.map { it.split("/").last() }
}

internal fun LocationObject.toModel() = Location(
    id = id,
    name = name,
    type = type,
    dimension = dimension,
    residents = residents.map { Character(it.toInt(), "", CharacterStatus.Alive , "", "",CharacterGender.Unknown, "" to 1, "" to 1, "", LocationPreview(0, "", "", "")) }

)
