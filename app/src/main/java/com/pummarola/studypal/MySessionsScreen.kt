package com.pummarola.studypal

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.pummarola.studypal.ui.theme.NeonYellow

// --- DATI MIE SESSIONI (Accessibili globalmente) ---
val mieSessioni = listOf(
    Sessione(
        materia = "Interazione Uomo Macchina",
        orario = "08:00 - 10:00",
        partecipanti = 4,
        data = "16/07/25",
        codice = "123456",
        isOwner = true, // TU SEI IL CAPO
        partecipantiList = listOf(
            Partecipante("Tu", isMe = true, isOwner = true), // Corona per te
            Partecipante("Roman Giangi", isMe = false, isOwner = false),
            Partecipante("Anthony Shepherd", isMe = false, isOwner = false),
            Partecipante("Enrico Artist", isMe = false, isOwner = false)
        )
    ),
    Sessione(
        materia = "Analisi Matematica II",
        orario = "15:00 - 17:00",
        partecipanti = 2,
        data = "18/07/25",
        codice = "556677",
        isOwner = false, // NON SEI IL CAPO
        partecipantiList = listOf(
            Partecipante("Tu", isMe = true, isOwner = false),
            Partecipante("Marco Rossi", isMe = false, isOwner = true) // Corona per Marco
        )
    )
)

@Composable
fun MySessionsScreen(onBack: () -> Unit, onNavigateToDetail: (String) -> Unit, navController: NavController) {
    var searchText by remember { mutableStateOf("") }

    val filteredSessions = mieSessioni.filter { sessione ->
        searchText.isEmpty() ||
                sessione.materia.contains(searchText, ignoreCase = true) ||
                sessione.codice.contains(searchText, ignoreCase = true)
    }

    Column(
        modifier = Modifier.fillMaxSize().background(Color(0xFFFAFAFA)).padding(16.dp)
    ) {
        Text(text = "Le mie Sessioni", fontSize = 32.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = searchText,
            onValueChange = { searchText = it },
            label = { Text("Filtra per Materia o Codice") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(25.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            items(filteredSessions) { sessione ->
                MySessionCard(
                    sessione = sessione,
                    onAccessClick = { onNavigateToDetail(sessione.materia) },
                    onDetailClick = { navController.navigate("detail_future/${sessione.materia}") }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

    }
}

@Composable
fun MySessionCard(sessione: Sessione, onAccessClick: () -> Unit, onDetailClick: () -> Unit) {
    val isLive = sessione.materia == "Interazione Uomo Macchina" // Simuliamo che questa sia live

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isLive) Color(0xFFE8F5E9) else Color.White
        ),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier.padding(20.dp).fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(text = sessione.materia, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Text(
                    text = "Data: ${sessione.data} | Ora: ${sessione.orario}",
                    color = if (isLive) Color(0xFF2E7D32) else Color.Gray,
                    fontSize = 13.sp
                )
                Text(text = "Partecipanti: ${sessione.partecipanti}", fontSize = 13.sp, fontWeight = FontWeight.Bold)
            }

            if (isLive) {
                Button(
                    onClick = onAccessClick,
                    colors = ButtonDefaults.buttonColors(containerColor = NeonYellow),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("ACCEDI", color = Color.Black, fontWeight = FontWeight.Bold)
                }
            } else {
                TextButton(onClick = onDetailClick) {
                    Text("Dettagli", color = Color.Blue)
                }
            }
        }
    }
}