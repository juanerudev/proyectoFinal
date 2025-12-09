package com.example.parcialfinal.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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
import com.example.parcialfinal.viewmodel.*
import com.example.parcialfinal.ui.components.DetailText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VehiclesListScreen(
    navController: NavController,
    viewModel: VehiclesViewModel = viewModel()
) {
    val vehiclesState = viewModel.vehiclesState.value

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("VEHÍCULOS", fontWeight = FontWeight.Bold) },
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
        when (vehiclesState) {
            is NetworkResult.Loading -> LoadingIndicator()
            is NetworkResult.Error -> ErrorMessage(vehiclesState.message)
            is NetworkResult.Success -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentPadding = PaddingValues(16.dp)
                ) {
                    items(vehiclesState.data) { vehicle ->
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(8.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surface
                            )
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(
                                    text = vehicle.name,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                DetailText("Modelo", vehicle.model)
                                DetailText("Fabricante", vehicle.manufacturer)
                                DetailText("Clase", vehicle.vehicle_class)
                                DetailText("Pasajeros", vehicle.passengers)
                            }
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                    }
                }
            }
        }
    }
}