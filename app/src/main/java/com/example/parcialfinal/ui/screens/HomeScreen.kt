package com.example.parcialfinal.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.parcialfinal.ui.theme.StarWarsYellow
import com.example.parcialfinal.ui.theme.StarWarsBlue

@Composable
fun HomeScreen(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.background,
                        StarWarsBlue.copy(alpha = 0.3f),
                        MaterialTheme.colorScheme.background
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "STAR WARS",
                fontSize = 48.sp,
                fontWeight = FontWeight.Bold,
                color = StarWarsYellow,
                letterSpacing = 4.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Universe Explorer",
                fontSize = 24.sp,
                color = StarWarsBlue
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "May the Force be with you",
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
            )

            Spacer(modifier = Modifier.height(48.dp))

            MenuButton(
                text = "PERSONAJES",
                onClick = { navController.navigate("people") }
            )

            Spacer(modifier = Modifier.height(16.dp))

            MenuButton(
                text = "PELÍCULAS",
                onClick = { navController.navigate("films") }
            )

            Spacer(modifier = Modifier.height(16.dp))

            MenuButton(
                text = "PLANETAS",
                onClick = { navController.navigate("planets") }
            )

            Spacer(modifier = Modifier.height(16.dp))

            MenuButton(
                text = "NAVES ESPACIALES",
                onClick = { navController.navigate("starships") }
            )

            Spacer(modifier = Modifier.height(16.dp))

            MenuButton(
                text = "VEHÍCULOS",
                onClick = { navController.navigate("vehicles") }
            )
        }
    }
}

@Composable
fun MenuButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = StarWarsYellow,
            contentColor = MaterialTheme.colorScheme.onPrimary
        )
    ) {
        Text(
            text = text,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
    }
}