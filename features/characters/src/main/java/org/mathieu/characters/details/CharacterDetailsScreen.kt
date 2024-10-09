package org.mathieu.characters.details

import android.media.MediaPlayer
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import org.mathieu.characters.R
import org.mathieu.characters.list.CharactersAction
import org.mathieu.characters.list.CharactersState
import org.mathieu.domain.models.character.LocationPreview
import org.mathieu.domain.models.location.Location
import org.mathieu.ui.Destination
import org.mathieu.ui.composables.PreviewContent
import org.mathieu.ui.navigate

private typealias UIState = CharacterDetailsState
private typealias UIAction = CharacterDetailsViewModel.CharactersDetailsAction

/**
 * Character details screen
 * Show the character details and the location
 * Display a vibration when the location card is clicked
 */
@Composable
fun CharacterDetailsScreen(
    navController: NavController,
    id: Int
) {
    // Init state and view model to handle the screen
    val viewModel: CharacterDetailsViewModel = viewModel<CharacterDetailsViewModel>()
    val state by viewModel.state.collectAsState()

    viewModel.init(characterId = id)

    // Handle events
    LaunchedEffect(viewModel) {
        viewModel.events
            .onEach { event ->
                if (event is CharacterDetailsViewModel.NavigateToLocation)
                    navController.navigate(destination = Destination.LocationDetails(event.locationId.toString()))
            }.collect()
    }

    // Display the content
    CharacterDetailsContent(
        state = state,
        onClickBack = navController::popBackStack,
        onAction = viewModel::handleAction // Pass the action handler to the content
    )

}


@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
private fun CharacterDetailsContent(
    viewModel: CharacterDetailsViewModel = viewModel(),
    state: UIState = UIState(),
    onClickBack: () -> Unit = { },
    onAction: (UIAction) -> Unit = { }
) = Scaffold(topBar = {

    // Display the top bar with the character name and a back button
    Row(
        modifier = Modifier
            .background(org.mathieu.ui.theme.Purple40)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier
                .padding(16.dp)
                .clickable(onClick = onClickBack),
            imageVector = Icons.Default.ArrowBack,
            contentDescription = "",
            colorFilter = ColorFilter.tint(Color.White)
        )

        Text(
            text = state.name,
            textAlign = TextAlign.Center,
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        )
    }
}) { paddingValues ->
    Box(modifier = Modifier
        .fillMaxSize()
        .padding(paddingValues), contentAlignment = Alignment.Center) {
        AnimatedContent(targetState = state.error != null, label = "") {
            state.error?.let { error ->
                Text(
                    modifier = Modifier.padding(16.dp),
                    text = error,
                    textAlign = TextAlign.Center,
                    color = org.mathieu.ui.theme.Purple40,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Medium,
                    lineHeight = 36.sp
                )
            } ?: Box(modifier = Modifier.fillMaxSize()) {

                Box(Modifier.align(Alignment.TopCenter)) {

                    SubcomposeAsyncImage(
                        modifier = Modifier
                            .blur(100.dp)
                            .alpha(0.3f)
                            .fillMaxWidth(),
                        model = state.avatarUrl,
                        contentDescription = null
                    )

                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .fillMaxWidth()
                            .height(100.dp)
                            .background(
                                Brush.verticalGradient(
                                    listOf(
                                        Color.Transparent,
                                        MaterialTheme.colorScheme.background
                                    )
                                )
                            )
                    )


                }

                Column(
                    modifier = Modifier.fillMaxWidth().padding(top = 24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    SubcomposeAsyncImage(
                        modifier = Modifier
                            .size(120.dp)
                            .clip(CircleShape)
                            .shadow(3.dp),
                        model = state.avatarUrl,
                        contentDescription = null
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(text = state.name)
                }


            }
        }
        // Display the location card and trigger the action when clicked on it
        LocationCard(state.locationPreview, onClick = { onAction(CharacterDetailsViewModel.ClickedOnLocation(state.locationPreview.id)) })
    }
}

/**
 * Location card
 * Display the location name and type
 * Trigger the action when clicked on it
 * Display a vibration and a sound when clicked
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LocationCard(locationPreview: LocationPreview, onClick: () -> Unit = { }) {
    val haptic = LocalHapticFeedback.current
    val context = LocalContext.current
    val mediaPlayer = MediaPlayer.create(context, R.raw.click_button)
    Card(

        // Vibration and sound when the card is clicked
        onClick = { onClick()
            // Sound on click
            mediaPlayer.start()

            // Vibration on click
            haptic.performHapticFeedback(HapticFeedbackType.LongPress)},
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .weight(2f)
            ) {
                Text(locationPreview.name , fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = locationPreview.type, fontSize = 14.sp)
                    Spacer(Modifier.width(16.dp))
                }
            }
        }
    }
}


@Preview
@Composable
private fun CharacterDetailsPreview() = PreviewContent {
    CharacterDetailsContent()
}

