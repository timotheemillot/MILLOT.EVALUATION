package org.mathieu.characters.details

import android.app.Application
import org.koin.core.component.inject
import org.mathieu.domain.models.character.LocationPreview
import org.mathieu.domain.models.location.Location
import org.mathieu.domain.repositories.CharacterRepository
import org.mathieu.domain.repositories.LocationRepository
import org.mathieu.ui.ViewModel

/**
 * ViewModel for the character details screen.
 */
class CharacterDetailsViewModel(application: Application) : ViewModel<CharacterDetailsState>(
    CharacterDetailsState(), application) {

    // Init the character repository to be able to get the character details
    private val characterRepository: CharacterRepository by inject()

    sealed interface CharactersDetailsAction
    data class ClickedOnLocation(val locationId: Int) : CharactersDetailsAction

    sealed interface Event
    data class NavigateToLocation(val locationId: Int) : Event

    fun handleAction(action: CharactersDetailsAction) {
        when (action) {
            is ClickedOnLocation -> {
                sendEvent(NavigateToLocation(action.locationId))
            }
        }
    }


    fun init(characterId: Int) {
        fetchData(
            source = { characterRepository.getCharacter(id = characterId) }
        ) {

            onSuccess {
                updateState {
                    // Update the state with the character details and the location preview
                    copy(
                        avatarUrl = it.avatarUrl,
                        name = it.name,
                        error = null,
                        locationPreview = it.locationPreview
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

// Represents the state of the character details screen
data class CharacterDetailsState(
    val isLoading: Boolean = true,
    val avatarUrl: String = "",
    val name: String = "",
    val error: String? = null,
    val location: Pair<String, Int> = Pair("", 0),
    val locationPreview: LocationPreview = LocationPreview(0, "", "", ""),
)