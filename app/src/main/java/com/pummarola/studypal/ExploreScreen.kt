package com.pummarola.studypal

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pummarola.studypal.ui.theme.NeonCyan

// --- MODELLO DATI AGGIORNATO (Sempre incluso per sicurezza) ---
data class Partecipante(
    val nome: String,
    val isMe: Boolean = false,
    val isOwner: Boolean = false
)

data class Sessione(
    val materia: String,
    val orario: String,
    val partecipanti: Int,
    val data: String,
    val codice: String,
    val isOwner: Boolean = false,
    val partecipantiList: List<Partecipante> = emptyList()
)

// --- DATI FINTI AGGIORNATI CON PARTECIPANTI E CORONE ---
val sessioniFinte = listOf(
    Sessione(
        materia = "Fisica I",
        orario = "14:00 - 16:00",
        partecipanti = 6,
        data = "18/07/25",
        codice = "654321",
        isOwner = false,
        partecipantiList = listOf(
            Partecipante("Marco Rossi", isMe = false, isOwner = true), // CAPO (Corona)
            Partecipante("Giulia Verdi", isMe = false, isOwner = false),
            Partecipante("Alessandro Re", isMe = false, isOwner = false),
            Partecipante("Beatrice Luz", isMe = false, isOwner = false)
        )
    ),
    Sessione(
        materia = "Programmazione Java",
        orario = "09:00 - 11:00",
        partecipanti = 3,
        data = "19/07/25",
        codice = "112233",
        isOwner = false,
        partecipantiList = listOf(
            Partecipante("Luca Bianchi", isMe = false, isOwner = true), // CAPO (Corona)
            Partecipante("Sofia Esposito", isMe = false, isOwner = false)
        )
    ),
    Sessione(
        materia = "Chimica Organica",
        orario = "16:30 - 18:30",
        partecipanti = 2,
        data = "20/07/25",
        codice = "445566",
        isOwner = false,
        partecipantiList = listOf(
            Partecipante("Enrico Artist", isMe = false, isOwner = true) // CAPO (Corona)
        )
    )
)

@Composable
fun ExploreScreen(onBack: () -> Unit, onNavigateToDetail: (String) -> Unit) {
    var searchText by remember { mutableStateOf("") }

    val filteredSessions = sessioniFinte.filter { sessione ->
        searchText.isEmpty() ||
                sessione.materia.contains(searchText, ignoreCase = true) ||
                sessione.codice.contains(searchText, ignoreCase = true)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFAFAFA))
            .padding(16.dp)
    ) {
        Text(
            text = "Esplora Sessioni",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = searchText,
            onValueChange = { searchText = it },
            label = { Text("Cerca per Nome Corso o Codice") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            shape = RoundedCornerShape(25.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(filteredSessions) { sessione ->
                SessionCard(
                    sessione = sessione,
                    onJoinClick = { onNavigateToDetail(sessione.materia) }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onBack,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray)
        ) {
            Text("Torna alla Home", color = Color.Black)
        }
    }
}

@Composable
fun SessionCard(sessione: Sessione, onJoinClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(text = sessione.materia, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Text(
                    text = "Data: ${sessione.data} | Ora: ${sessione.orario}",
                    color = Color.Gray,
                    fontSize = 14.sp
                )
                Text(
                    text = "Partecipanti: ${sessione.partecipanti}",
                    color = Color.DarkGray,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Button(
                onClick = onJoinClick,
                colors = ButtonDefaults.buttonColors(containerColor = NeonCyan),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Unisciti", color = Color.Black, fontWeight = FontWeight.Bold)
            }
        }
    }
}