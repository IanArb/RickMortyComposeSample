package com.ianarbuckle.rickmortycompose.ui.characters.composables

import androidx.compose.foundation.Box
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumnFor
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ContextAmbient
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.ui.tooling.preview.Preview
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import com.ianarbuckle.rickmortycompose.api.Character
import com.ianarbuckle.rickmortycompose.api.Location
import com.ianarbuckle.rickmortycompose.api.Origin
import com.ianarbuckle.rickmortycompose.ui.theme.RickyMortyComposeTheme
import dev.chrisbanes.accompanist.coil.CoilImageWithCrossfade

@Composable
fun CharactersContent(characters: List<Character>, innerPadding: InnerPadding) {
    LazyColumnFor(
        contentPadding = innerPadding,
        modifier = Modifier.padding(16.dp),
        items = characters
    ) { item ->
        CardLayout(item)
    }
}

@Composable
fun CardLayout(character: Character) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        elevation = 6.dp
    ) {
        Row {
            Box(
                modifier = Modifier.gravity(Alignment.CenterVertically)
            ) {
                CoilImageWithCrossfade(
                    request = ImageRequest.Builder(ContextAmbient.current)
                        .data(character.image)
                        .transformations(CircleCropTransformation())
                        .build(),
                    modifier = Modifier.padding(16.dp),
                    contentScale = ContentScale.FillHeight
                )
            }
            CardBodyContent(character)
        }

    }
}

@Composable
private fun CardBodyContent(character: Character) {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Text(
            text = character.name,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
        )
        Row {
            Box(
                modifier = Modifier
                    .padding(top = 4.dp)
                    .preferredSize(12.dp),
                shape = CircleShape,
                backgroundColor = statusColor(character.status)
            ) {}
            Text(
                modifier = Modifier
                    .padding(
                        top = 1.dp,
                        start = 6.dp
                    ),
                text = "${character.status} - ${character.species}",
                color = Color.Black,
                fontSize = 12.sp
            )
        }
        Text(
            modifier = Modifier.padding(0.dp, 12.dp, 0.dp, 0.dp),
            text = "Last known location:",
            color = Color.Black,
            fontSize = 12.sp,
            fontWeight = FontWeight.Light
        )
        Text(
            text = character.location.name,
            color = Color.Black,
            fontSize = 14.sp,
            fontWeight = FontWeight.Black
        )
        Text(
            modifier = Modifier.padding(0.dp, 12.dp, 0.dp, 0.dp),
            text = "Origin:",
            color = Color.Black,
            fontSize = 12.sp,
            fontWeight = FontWeight.Light
        )
        Text(
            text = character.origin.name,
            color = Color.Black,
            fontSize = 14.sp,
            fontWeight = FontWeight.Black
        )
    }
}

@Composable
fun statusColor(status: String): Color {
    return when(status) {
        "Alive" -> Color.Green
        "Dead" -> Color.Red
        "unknown" -> Color.Black
        else -> Color.Black
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    val rick = Character(
        created = "",
        episode = listOf("Rick E1"),
        gender = "Male",
        image = "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
        location = Location("Earth", ""),
        name = "Rick Sanchez",
        status = "Alive",
        type = "Human",
        species = "Human",
        url = "",
        origin = Origin("", ""),
        id = 0
    )
    val morty = Character(
        created = "",
        episode = listOf("Morty E1"),
        gender = "Male",
        image = "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
        location = Location("Earth", ""),
        name = "Morty Smith",
        status = "Alive",
        type = "Human",
        species = "Human",
        url = "",
        origin = Origin("", ""),
        id = 0
    )
    val summer = Character(
        created = "",
        episode = listOf("Morty E1"),
        gender = "Female",
        image = "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
        location = Location("Earth", ""),
        name = "Summer Smith",
        status = "Alive",
        type = "Human",
        species = "Human",
        url = "",
        origin = Origin("", ""),
        id = 0
    )
    RickyMortyComposeTheme {
        CharactersContent(listOf(rick, morty, summer), InnerPadding(16.dp))
    }
}