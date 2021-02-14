package com.ianarbuckle.rickmortycompose.ui.characters.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.AmbientContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import com.ianarbuckle.rickmortycompose.api.Character
import com.ianarbuckle.rickmortycompose.api.Location
import com.ianarbuckle.rickmortycompose.api.Origin
import dev.chrisbanes.accompanist.coil.CoilImage

@Composable
fun CardLayout(character: Character) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        elevation = 4.dp
    ) {
        Row {
            CoilImage(
                fadeIn = true,
                request = ImageRequest.Builder(AmbientContext.current)
                    .data(character.image)
                    .transformations(CircleCropTransformation())
                    .build(),
                modifier = Modifier.padding(16.dp),
                contentScale = ContentScale.FillHeight,
                contentDescription = "Character Icon"
            )
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
                    .size(10.dp)
                    .clip(shape = CircleShape)
                    .background(statusColor(status = character.status))
            )
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
    return when (status) {
        "Alive" -> Color.Green
        "Dead" -> Color.Red
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
        origin = Origin("Earth", ""),
        id = 0
    )
    CardLayout(rick)
}