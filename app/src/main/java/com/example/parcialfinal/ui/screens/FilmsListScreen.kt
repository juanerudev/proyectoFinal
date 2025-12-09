package com.example.parcialfinal.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.parcialfinal.data.model.Film
import com.example.parcialfinal.ui.components.ErrorMessage
import com.example.parcialfinal.ui.components.LoadingIndicator
import com.example.parcialfinal.ui.theme.StarWarsYellow
import com.example.parcialfinal.utils.NetworkResult
import com.example.parcialfinal.utils.extractId
import com.example.parcialfinal.viewmodel.FilmsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilmsListScreen(
    navController: NavController,
    viewModel: FilmsViewModel = viewModel()
) {
    val filmsState = viewModel.filmsState.value

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("PELÍCULAS", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Atrás")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = StarWarsYellow
                )
            )
        }
    ) { padding ->
        when (filmsState) {
            is NetworkResult.Loading -> LoadingIndicator()
            is NetworkResult.Error -> ErrorMessage(filmsState.message)
            is NetworkResult.Success -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentPadding = PaddingValues(16.dp)
                ) {
                    items(filmsState.data) { film ->
                        FilmItem(
                            film = film,
                            onClick = {
                                film.url.extractId()?.let { id ->
                                    navController.navigate("film/$id")
                                }
                            }
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun FilmItem(film: Film, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Surface(
                    color = StarWarsYellow,
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Text(
                        text = "Episodio ${film.episode_id}",
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = film.title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = "Estreno: ${film.release_date}",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            }
            Text(
                text = "→",
                color = StarWarsYellow,
                fontSize = 24.sp
            )
        }
    }
}