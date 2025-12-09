package com.example.parcialfinal.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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
import com.example.parcialfinal.utils.extractId
import com.example.parcialfinal.viewmodel.PeopleViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PeopleListScreen(
    navController: NavController,
    viewModel: PeopleViewModel = viewModel()
) {
    val peopleState = viewModel.peopleState.value
    val searchQuery = viewModel.searchQuery.value
    val filteredPeople = viewModel.getFilteredPeople()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("PERSONAJES", fontWeight = FontWeight.Bold) },
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            // Barra de búsqueda
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { viewModel.updateSearchQuery(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                placeholder = { Text("Buscar personajes...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = StarWarsYellow,
                    cursorColor = StarWarsYellow
                )
            )

            when (peopleState) {
                is NetworkResult.Loading -> LoadingIndicator()
                is NetworkResult.Error -> ErrorMessage(peopleState.message)
                is NetworkResult.Success -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        items(filteredPeople) { person ->
                            PersonItem(
                                name = person.name,
                                onClick = {
                                    person.url.extractId()?.let { id ->
                                        navController.navigate("person/$id")
                                    }
                                }
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PersonItem(name: String, onClick: () -> Unit) {
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
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = name,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = "→",
                color = StarWarsYellow,
                fontSize = 20.sp
            )
        }
    }
}