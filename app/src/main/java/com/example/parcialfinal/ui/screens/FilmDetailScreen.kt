package com.example.parcialfinal.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.parcialfinal.ui.components.ErrorMessage
import com.example.parcialfinal.ui.components.LoadingIndicator
import com.example.parcialfinal.ui.theme.StarWarsYellow
import com.example.parcialfinal.utils.NetworkResult
import com.example.parcialfinal.viewmodel.FilmsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilmDetailScreen(
    filmId: Int,
    navController: NavController,
    viewModel: FilmsViewModel = viewModel()
) {
    val filmState = viewModel.filmDetailState.value

    LaunchedEffect(filmId) {
        viewModel.loadFilmDetail(filmId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    when (filmState) {
                        is NetworkResult.Success -> Text(
                            filmState.data.title,
                            fontWeight = FontWeight.Bold
                        )
                        else -> Text("Cargando...")
                    }
                },
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
        when (filmState) {
            is NetworkResult.Loading -> LoadingIndicator()
            is NetworkResult.Error -> ErrorMessage(filmState.message)
            is NetworkResult.Success -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .padding(16.dp)
                ) {
                    item {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surface
                            )
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Surface(
                                    color = StarWarsYellow,
                                    shape = RoundedCornerShape(20.dp)
                                ) {
                                    Text(
                                        text = "Episodio ${filmState.data.episode_id}",
                                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onPrimary
                                    )
                                }

                                Spacer(modifier = Modifier.height(16.dp))

                                Text(
                                    text = filmState.data.title,
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.Bold
                                )

                                Spacer(modifier = Modifier.height(16.dp))

                                InfoRow("Director", filmState.data.director)
                                InfoRow("Productor", filmState.data.producer)
                                InfoRow("Fecha de estreno", filmState.data.release_date)

                                Divider(
                                    modifier = Modifier.padding(vertical = 16.dp),
                                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)
                                )

                                Text(
                                    text = "Opening Crawl",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = StarWarsYellow
                                )

                                Spacer(modifier = Modifier.height(8.dp))

                                Text(
                                    text = filmState.data.opening_crawl,
                                    fontSize = 14.sp,
                                    lineHeight = 20.sp,
                                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
                                    textAlign = TextAlign.Justify
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}