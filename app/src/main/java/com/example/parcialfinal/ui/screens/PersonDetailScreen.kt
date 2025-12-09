package com.example.parcialfinal.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.parcialfinal.ui.components.ErrorMessage
import com.example.parcialfinal.ui.components.LoadingIndicator
import com.example.parcialfinal.ui.theme.StarWarsYellow
import com.example.parcialfinal.utils.NetworkResult
import com.example.parcialfinal.viewmodel.PeopleViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonDetailScreen(
    personId: Int,
    navController: NavController,
    viewModel: PeopleViewModel = viewModel()
) {
    val personState = viewModel.personDetailState.value
    val homeworld = viewModel.homeworldState.value
    val films = viewModel.filmsState.value

    LaunchedEffect(personId) {
        viewModel.loadPersonDetail(personId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    when (personState) {
                        is NetworkResult.Success -> Text(
                            personState.data.name,
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
        when (personState) {
            is NetworkResult.Loading -> LoadingIndicator()
            is NetworkResult.Error -> ErrorMessage(personState.message)
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
                                Text(
                                    text = "Información Personal",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = StarWarsYellow
                                )

                                Spacer(modifier = Modifier.height(16.dp))

                                InfoRow("Altura", "${personState.data.height} cm")
                                InfoRow("Peso", "${personState.data.mass} kg")
                                InfoRow("Género", personState.data.gender.capitalize())
                                InfoRow("Año de nacimiento", personState.data.birth_year)
                                InfoRow("Color de ojos", personState.data.eye_color.capitalize())
                                InfoRow("Color de cabello", personState.data.hair_color.capitalize())

                                homeworld?.let {
                                    Divider(
                                        modifier = Modifier.padding(vertical = 12.dp),
                                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)
                                    )
                                    Text(
                                        text = "Planeta natal",
                                        fontSize = 12.sp,
                                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                                    )
                                    Text(
                                        text = it.name,
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                }
                            }
                        }

                        if (films.isNotEmpty()) {
                            Spacer(modifier = Modifier.height(16.dp))

                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.surface
                                )
                            ) {
                                Column(modifier = Modifier.padding(16.dp)) {
                                    Text(
                                        text = "Películas",
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = StarWarsYellow
                                    )

                                    Spacer(modifier = Modifier.height(12.dp))

                                    films.forEach { film ->
                                        Row(modifier = Modifier.padding(vertical = 4.dp)) {
                                            Text(
                                                text = "• ",
                                                color = StarWarsYellow,
                                                fontSize = 16.sp
                                            )
                                            Text(
                                                text = film.title,
                                                fontSize = 16.sp
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
        )
        Text(
            text = value,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

private fun String.capitalize() = this.replaceFirstChar {
    if (it.isLowerCase()) it.titlecase() else it.toString()
}