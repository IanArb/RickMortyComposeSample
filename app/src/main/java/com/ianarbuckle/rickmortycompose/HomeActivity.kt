package com.ianarbuckle.rickmortycompose

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.ianarbuckle.rickmortycompose.api.Character
import com.ianarbuckle.rickmortycompose.ui.characters.view.CardLayout
import com.ianarbuckle.rickmortycompose.ui.theme.RickyMortyComposeTheme
import com.ianarbuckle.rickmortycompose.ui.characters.viewmodel.CharactersViewModel
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
    val characters = charactersViewModel.characters.collectAsLazyPagingItems()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "Rick & Morty Catalogue") })
        },
        bodyContent = {
            RowList(characters = characters, innerPadding = it)
        }
    )
}

@Composable
fun RowList(characters: LazyPagingItems<Character>, innerPadding: PaddingValues) {
    LazyColumn(contentPadding = innerPadding) {

        items(characters) { character ->
            character?.let {
                CardLayout(character = it)
            }
        }

        characters.apply {
            when {
                loadState.append is LoadState.Error -> {
                    item { ErrorContent() }
                }
                loadState.append is LoadState.Loading -> {
                    item { LoadingContent() }
                }
                loadState.refresh is LoadState.Loading -> {
                    item { LoadingView() }
                }
            }
        }
    }
}

@Composable
fun LoadingContent() {
    Row(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun ErrorContent() {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                imageResource(id = R.drawable.rick_morty_error),
                modifier = Modifier.preferredWidthIn(250.dp, 250.dp),
                alignment = Alignment.Center,
                contentScale = ContentScale.Fit,
                contentDescription = ""
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

@Composable
fun LoadingView() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator()
    }
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
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LoadingItemPreview() {
    RickyMortyComposeTheme {
        Scaffold(
            topBar = {
                TopAppBar(title = { Text(text = "Rick & Morty Catalogue") })
            },
            bodyContent = {
                LoadingView()
            }
        )
    }
}