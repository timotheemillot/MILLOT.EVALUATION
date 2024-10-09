package com.example.locations

import android.app.Application
import org.koin.core.component.inject
import org.mathieu.domain.models.character.Character
import org.mathieu.domain.models.location.Location
import org.mathieu.domain.repositories.LocationRepository
import org.mathieu.domain.repositories.CharacterRepository
import org.mathieu.ui.ViewModel


class LocationDetailsViewModel(application: Application) : ViewModel<LocationDetailsState>(
    LocationDetailsState(), application) {

    // Init the location repository to be able to get the location details
    private val locationRepository: LocationRepository by inject()


    // Init the location details
    fun init(locationId: Int) {
        fetchData(
            source = { locationRepository.getLocation(id = locationId) }
        ) {

            onSuccess {
                updateState {
                    // Update the state with the location details we got from the repository
                    copy(
                        name = it.name,
                        type = it.type,
                        dimension = it.dimension,
                        residents = it.residents,
                    )
                }

                onFailure {
                    updateState { copy(error = it.toString()) }
                }

                updateState { copy(isLoading = false) }
            }
        }


    }
}

// State for the location details screen
data class LocationDetailsState(
    val isLoading: Boolean = true,
    val error: String? = null,
    val name : String = "",
    val type : String = "",
    val dimension : String = "",
    val residents : List<Character> = emptyList(),
)