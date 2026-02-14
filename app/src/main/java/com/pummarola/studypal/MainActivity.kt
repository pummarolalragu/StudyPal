package com.pummarola.studypal

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.pummarola.studypal.ui.theme.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StudyPalTheme {
                val navController = rememberNavController()
                val context = LocalContext.current

                NavHost(navController = navController, startDestination = "home") {

                    composable("home") {
                        HomeScreen(
                            onNavigateToCreate = { navController.navigate("create") },
                            onNavigateToExplore = { navController.navigate("explore") },
                            onNavigateToMySessions = { navController.navigate("my_sessions") }
                        )
                    }

                    composable("create") {
                        CreateGroupScreen(
                            onBack = { navController.popBackStack() },
                            onCreate = {
                                Toast.makeText(context, "Sessione creata con successo!", Toast.LENGTH_SHORT).show()
                                navController.popBackStack()
                            }
                        )
                    }

                    composable("explore") {
                        ExploreScreen(
                            onBack = { navController.popBackStack() },
                            onNavigateToDetail = { materia -> navController.navigate("explore_detail/$materia") }
                        )
                    }

                    composable("my_sessions") {
                        MySessionsScreen(
                            onBack = { navController.popBackStack() },
                            onNavigateToDetail = { materia -> navController.navigate("detail/$materia") },
                            navController = navController // Passato per navigare al futuro
                        )
                    }

                    composable("detail/{materia}") { backStackEntry ->
                        val materia = backStackEntry.arguments?.getString("materia") ?: "Sessione"
                        SessionDetailScreen(
                            materia = materia,
                            onBack = {
                                Toast.makeText(context, "Sei uscito dalla sessione", Toast.LENGTH_SHORT).show()
                                navController.popBackStack()
                            },
                            navController = navController
                        )
                    }

                    composable("chat/{materia}") { backStackEntry ->
                        val materia = backStackEntry.arguments?.getString("materia") ?: "Gruppo"
                        ChatScreen(
                            onBack = { navController.popBackStack() },
                            materia = materia
                        )
                    }

                    composable("explore_detail/{materia}") { backStackEntry ->
                        val materia = backStackEntry.arguments?.getString("materia") ?: "Sessione"
                        ExploreDetailScreen(
                            materia = materia,
                            onBack = { navController.popBackStack() },
                            onJoin = {
                                Toast.makeText(context, "Iscrizione a $materia confermata!", Toast.LENGTH_SHORT).show()
                                navController.popBackStack()
                            }
                        )
                    }

                    // NUOVA ROTTA: Dettaglio Sessione Programmata (Futura)
                    composable("detail_future/{materia}") { backStackEntry ->
                        val materia = backStackEntry.arguments?.getString("materia") ?: "Sessione"
                        FutureSessionDetailScreen(
                            materia = materia,
                            onBack = { navController.popBackStack() }
                        )
                    }
                }
            }
        }
    }
}

// --- FUNZIONI DI UI ---
// Le funzioni HomeScreen e MenuButton sono qui per far compilare il file.
// Le altre funzioni (CreateGroupScreen, ExploreScreen, MySessionsScreen, SessionDetailScreen, ChatScreen, ExploreDetailScreen, FutureSessionDetailScreen) DEVONO ESSERE NEI LORO FILE SEPARATI (.kt)

@Composable
fun HomeScreen(
    onNavigateToCreate: () -> Unit,
    onNavigateToExplore: () -> Unit,
    onNavigateToMySessions: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "StudyPal", fontSize = 48.sp, fontWeight = FontWeight.ExtraBold, color = MaterialTheme.colorScheme.onBackground)
        Text(text = "Non studiare mai più da solo", fontSize = 16.sp, color = Color.Gray)
        Spacer(modifier = Modifier.height(60.dp))

        MenuButton(text = "Crea Nuovo Gruppo", color = NeonPink, onClick = onNavigateToCreate)
        Spacer(modifier = Modifier.height(20.dp))
        MenuButton(text = "Esplora Sessioni", color = NeonCyan, onClick = onNavigateToExplore)
        Spacer(modifier = Modifier.height(20.dp))
        MenuButton(text = "Le mie Sessioni", color = NeonYellow, textColor = Color.Black, onClick = onNavigateToMySessions)
    }
}

@Composable
fun MenuButton(text: String, color: Color, textColor: Color = Color.White, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth().height(90.dp),
        colors = ButtonDefaults.buttonColors(containerColor = color),
        shape = RoundedCornerShape(10.dp),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp)
    ) {
        Text(text = text, fontSize = 22.sp, fontWeight = FontWeight.Bold, color = textColor)
    }
}