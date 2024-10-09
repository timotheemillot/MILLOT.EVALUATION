package com.example.locations

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import org.mathieu.domain.models.character.Character
import org.mathieu.ui.composables.TextLabel


private typealias UIState = LocationDetailsState

/**
 * Location details screen
 */
@Composable
fun LocationDetailsScreen(
    navController: NavController,
    id: Int
) {

    val viewModel: LocationDetailsViewModel = viewModel()
    val state by viewModel.state.collectAsState()

    viewModel.init(locationId = id)

    LocationDetailsContent(
        state = state,
        onClickBack = {
            navController.popBackStack()
        }
    )
}

/**
 * Location details content
 * Show location details and list of characters
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationDetailsContent(
    state: UIState,
    onClickBack: () -> Unit
) = Scaffold(topBar = {
    // Top bar with back button
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
}){ innerPadding ->
    Column(
        modifier = Modifier.padding(innerPadding),
    ) {
        // Location details
        TextLabel("Location name", state.name)
        TextLabel("Location type", state.type)
        TextLabel("Dimension", state.dimension)

        // List of characters
        Box(modifier = Modifier.fillMaxSize()) {
            LazyColumn(contentPadding = innerPadding) {
                items(state.residents) { character ->
                    CharacterCard(character)
                }
            }
        }
    }
}

/**
 * Character card
 * Show character details
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterCard(character: Character) {
    Card(
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
                Text(character.name, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("${character.type}", fontSize = 14.sp)
                    Spacer(Modifier.width(16.dp))
                }
            }
        }
    }
}

@Composable
@Preview
fun LocationDetailsScreenPreview() {
    LocationDetailsScreen(rememberNavController(), 1)
}