package com.ianarbuckle.rickmortycompose

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import com.ianarbuckle.rickmortycompose.api.Character
import com.ianarbuckle.rickmortycompose.ui.characters.composables.CharactersContent
import com.ianarbuckle.rickmortycompose.ui.theme.RickyMortyComposeTheme
import com.ianarbuckle.rickmortycompose.ui.characters.viewmodel.CharactersViewModel
import com.ianarbuckle.rickmortycompose.utils.UIViewState
import dagger.hilt.android.AndroidEntryPoint

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
fun LoadingContent() {
    Row(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalGravity = Alignment.CenterVertically
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun ErrorContent() {
    Row(modifier = Modifier
        .fillMaxHeight()
        .fillMaxWidth()
        .padding(16.dp),
        horizontalArrangement = Arrangement.Center,
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
fun LoadingPreview() {
    RickyMortyComposeTheme {
        Scaffold(
            topBar = {
                TopAppBar(title = { Text(text = "Rick & Morty Catalogue") })
            },
            bodyContent = {
                LoadingContent()
            },
            bottomBar = {
                BottomAppBar(0)
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ErrorPreview() {
    RickyMortyComposeTheme {
        Scaffold(
            topBar = {
                TopAppBar(title = { Text(text = "Rick & Morty Catalogue") })
            },
            bodyContent = {
                ErrorContent()
            },
            bottomBar = {
                BottomAppBar(0)
            }
        )
    }
}