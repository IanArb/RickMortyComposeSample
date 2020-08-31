package com.ianarbuckle.rickmortycompose

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Box
import androidx.compose.foundation.Icon
import androidx.compose.foundation.Image
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumnFor
import androidx.compose.foundation.lazy.LazyRowFor
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.ui.tooling.preview.Preview
import com.ianarbuckle.rickmortycompose.api.Character
import com.ianarbuckle.rickmortycompose.api.Location
import com.ianarbuckle.rickmortycompose.api.Origin
import com.ianarbuckle.rickmortycompose.ui.RickyMortyComposeTheme
import com.ianarbuckle.rickmortycompose.ui.characters.CharactersViewModel
import com.ianarbuckle.rickmortycompose.ui.characters.UIViewState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val charactersViewModel: CharactersViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
            setContent {
                RickyMortyComposeTheme {
                    CataloguesScreen(charactersViewModel)
                }
            }
    }
}

@ExperimentalCoroutinesApi
@Composable
fun CataloguesScreen(charactersViewModel: CharactersViewModel) {
    val bottomBarSelectedIndex by remember {
        mutableStateOf(0)
    }

    val observeState = charactersViewModel.uiViewStateObservable.observeAsState()
    val uiState = observeState.value

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "Rick & Morty Catalogue") })
        },
        bodyContent = {
            when (uiState) {
                is UIViewState.Success -> {
                    CharactersContent(uiState.result as List<Character>, it)
                }
                is UIViewState.Error -> {
                    ErrorContent()
                }
                is UIViewState.Loading -> {
                    LoadingContent()
                }
            }
        },
        bottomBar = {
            BottomAppBar(bottomBarSelectedIndex)
        }
    )
}

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
fun LoadingContent() {
    Row(
        modifier = Modifier.padding(16.dp),
        horizontalArrangement = Arrangement.Center) {
        CircularProgressIndicator(progress = 0.5f)
    }
}

@Composable
fun ErrorContent() {
    Row(modifier = Modifier.padding(16.dp),
        verticalGravity = Alignment.CenterVertically) {
        Column(
                horizontalGravity = Alignment.CenterHorizontally
        ){
            Image(
                    imageResource(id = R.drawable.rick_morty_error),
                    modifier = Modifier.preferredWidthIn(250.dp, 250.dp),
                    alignment = Alignment.Center,
                    contentScale = ContentScale.Fit
            )
            Text(
                    text = "Oops! Something went wrong.",
                    modifier = Modifier.padding(16.dp)
            )
            Button(onClick = {

            }) {
                Text(
                        text = "Try Again"
                )
            }
        }

    }
}

@Composable
fun CardLayout(character: Character) {
    Card(
            modifier = Modifier.padding(8.dp),
            shape = RoundedCornerShape(10.dp),
            elevation = 6.dp
    ) {
        Row(
                modifier = Modifier.fillMaxWidth(),

        ) {
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
                shape = CircleShape,
                backgroundColor = Color.Blue
            )
            Text(
                text = "${character.status} - ${character.species}",
                color = statusColor(character.status),
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
                text = "First seen in:",
                color = Color.Black,
                fontSize = 12.sp,
                fontWeight = FontWeight.Light
        )
        Text(
                text = character.episode.first().toString(),
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

@Composable
fun BottomAppBar(bottomBarSelectedIndex: Int) {
    var selectedIndex = bottomBarSelectedIndex
    BottomAppBar(content = {
        BottomNavigationItem(
            icon = { Icon(Icons.Default.Person) },
            label = { Text("Characters") },
            selected = selectedIndex == 0,
            onSelect = {
                selectedIndex = 0
            })

        BottomNavigationItem(
            icon = { Icon(Icons.Default.LocationOn) },
            label = { Text("Locations") },
            selected = selectedIndex == 1,
            onSelect = {
                selectedIndex = 0
            })

    })
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

@Preview(showBackground = true)
@Composable
fun LoadingPreview() {
    RickyMortyComposeTheme {
        LoadingContent()
    }
}

@Preview(showBackground = true)
@Composable
fun ErrorPreview() {
    RickyMortyComposeTheme {
        ErrorContent()
    }
}