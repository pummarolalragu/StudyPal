package com.pummarola.studypal

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.pummarola.studypal.ui.theme.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StudyPalTheme {
                val navController = rememberNavController()
                val context = LocalContext.current

                // Capiamo in che pagina siamo per mostrare/nascondere i tasti giusti
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                // LO SCAFFOLD: La cornice globale dell'app
                Scaffold(
                    topBar = {
                        // Mostriamo la TopBar ovunque
                        TopAppBarGlobal(navController = navController, currentRoute = currentRoute)
                    },
                    bottomBar = {
                        // Mostriamo la BottomBar ovunque
                        BottomAppBarGlobal(navController = navController, currentRoute = currentRoute)
                    }
                ) { innerPadding ->
                    // Il contenuto delle pagine va qui dentro, rispettando i margini (innerPadding)
                    NavHost(
                        navController = navController,
                        startDestination = "home",
                        modifier = Modifier.padding(innerPadding) // Fondamentale per non sovrapporre
                    ) {
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
                                navController = navController
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
                            ChatScreen(onBack = { navController.popBackStack() }, materia = materia)
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
                        composable("detail_future/{materia}") { backStackEntry ->
                            val materia = backStackEntry.arguments?.getString("materia") ?: "Sessione"
                            FutureSessionDetailScreen(materia = materia, onBack = { navController.popBackStack() })
                        }

                        // NUOVE ROTTE
                        composable("friends") {
                            FriendsScreen()
                        }
                        composable("profile") {
                            ProfileScreen(onLogout = {
                                Toast.makeText(context, "Logout effettuato", Toast.LENGTH_SHORT).show()
                                navController.navigate("home") { popUpTo(0) } // Torna alla home pulendo la cronologia
                            })
                        }
                    }
                }
            }
        }
    }
}

// --- BARRA SUPERIORE GLOBALE ---
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarGlobal(navController: NavController, currentRoute: String?) {
    TopAppBar(
        title = { Text("") }, // Nessun titolo centrale
        colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background),
        navigationIcon = {
            // Tasto Amici a Sinistra (Nascondi se siamo già in Amici)
            if (currentRoute != "friends") {
                IconButton(onClick = { navController.navigate("friends") }) {
                    Icon(Icons.Default.Face, contentDescription = "Amici", tint = AccentBlue, modifier = Modifier.size(30.dp))
                }
            }
        },
        actions = {
            // Tasto Profilo a Destra (Nascondi se siamo già in Profilo)
            if (currentRoute != "profile") {
                IconButton(onClick = { navController.navigate("profile") }) {
                    Icon(Icons.Default.Person, contentDescription = "Profilo", tint = AccentBlue, modifier = Modifier.size(30.dp))
                }
            }
        }
    )
}

// --- BARRA INFERIORE GLOBALE ---
@Composable
fun BottomAppBarGlobal(navController: NavController, currentRoute: String?) {
    BottomAppBar(
        containerColor = Color.White,
        contentColor = AccentBlue,
        tonalElevation = 8.dp
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Tasto Back (Nascondi se siamo nella Home)
            if (currentRoute != "home") {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Indietro", modifier = Modifier.size(30.dp))
                }
            } else {
                Spacer(modifier = Modifier.size(48.dp)) // Spazio vuoto per bilanciare
            }

            // Tasto Home (Nascondi se siamo già nella Home)
            if (currentRoute != "home") {
                IconButton(onClick = { navController.navigate("home") { popUpTo("home") { inclusive = true } } }) {
                    Icon(Icons.Default.Home, contentDescription = "Home", modifier = Modifier.size(30.dp))
                }
            } else {
                Spacer(modifier = Modifier.size(48.dp))
            }
        }
    }
}

// --- HOME SCREEN ---
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

        MenuButton(text = "Crea Nuovo Gruppo", color = AccentBlue, onClick = onNavigateToCreate)
        Spacer(modifier = Modifier.height(20.dp))
        MenuButton(text = "Esplora Sessioni", color = AccentBlue, onClick = onNavigateToExplore)
        Spacer(modifier = Modifier.height(20.dp))
        MenuButton(text = "Le mie Sessioni", color = AccentBlue, textColor = Color.White, onClick = onNavigateToMySessions)
    }
}

@Composable
fun MenuButton(text: String, color: Color, textColor: Color = Color.White, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth().height(90.dp),
        colors = ButtonDefaults.buttonColors(containerColor = color),
        shape = RoundedCornerShape(15.dp), // Più minimalista
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
    ) {
        Text(text = text, fontSize = 22.sp, fontWeight = FontWeight.Bold, color = textColor)
    }
}


// --- COMPONENTE AVATAR PERSONALIZZATO ---
@Composable
fun UserAvatar(name: String, size: androidx.compose.ui.unit.Dp = 40.dp) {
    // Prende la prima lettera del nome
    val initial = if (name.isNotBlank()) name.take(1).uppercase() else "?"

    // Lista di colori pastello stile Apple/Google
    val colorList = listOf(
        Color(0xFFFFADAD), Color(0xFFCE93D8), Color(0xFF90CAF9),
        Color(0xFFA5D6A7), Color(0xFFFFE082), Color(0xFFFFCC80)
    )
    // Assegna sempre lo stesso colore alla stessa persona basandosi sul nome
    val bgColor = colorList[kotlin.math.abs(name.hashCode()) % colorList.size]

    Surface(
        modifier = Modifier.size(size),
        shape = CircleShape,
        color = bgColor
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(
                text = initial,
                color = Color.Black.copy(alpha = 0.7f),
                fontWeight = FontWeight.Bold,
                fontSize = (size.value / 2.2).sp
            )
        }
    }
}