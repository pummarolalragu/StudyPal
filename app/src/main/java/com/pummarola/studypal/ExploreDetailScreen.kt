package com.pummarola.studypal

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pummarola.studypal.ui.theme.AccentBlue
import com.pummarola.studypal.ui.theme.NeonCyan
import com.pummarola.studypal.ui.theme.NeonYellow

@Composable
fun ExploreDetailScreen(materia: String, onBack: () -> Unit, onJoin: () -> Unit) {
    // 1. RECUPERO I DATI REALI DELLA SESSIONE DALLA LISTA FINTA
    // Cerchiamo nella lista 'sessioniFinte' (definita in ExploreScreen.kt) la sessione che corrisponde alla materia cliccata.
    // Se non la trova (caso raro), usa un oggetto "fallback" vuoto per non far crashare l'app.
    val sessione = sessioniFinte.find { it.materia == materia } ?: Sessione(
        materia = materia, orario = "--:--", partecipanti = 0, data = "--/--/--", codice = "000000"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(24.dp)
    ) {
        // --- HEADER ---
        Text(text = sessione.materia, fontSize = 28.sp, fontWeight = FontWeight.ExtraBold)

        Spacer(modifier = Modifier.height(8.dp))

        Badge(containerColor = Color.Gray) {
            Text("SESSIONE PUBBLICA", color = Color.White, modifier = Modifier.padding(4.dp))
        }

        Spacer(modifier = Modifier.height(24.dp))

        // --- INFO SESSIONE (Data, Ora, Codice) ---
        Card(
            colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5)),
            shape = RoundedCornerShape(15.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.padding(16.dp).fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                InfoItem(icon = Icons.Default.DateRange, label = sessione.data)
                InfoItem(icon = Icons.Default.Info, label = sessione.orario) // Usiamo icona generica per l'ora se Clock non c'è
                InfoItem(icon = Icons.Default.Lock, label = "Cod: ${sessione.codice}")
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // --- LISTA PARTECIPANTI ---
        Text(
            text = "Partecipanti (${sessione.partecipanti} Totali):",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Usiamo LazyColumn per la lista degli utenti
        LazyColumn(
            modifier = Modifier.weight(1f), // Occupa lo spazio disponibile
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(sessione.partecipantiList) { partecipante ->
                PartecipanteRow(partecipante)
            }

            // Se la lista è vuota o incompleta nei dati finti, mostriamo un placeholder
            if (sessione.partecipantiList.isEmpty()) {
                item { Text("Nessun partecipante visibile.", color = Color.Gray) }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // --- MATERIALI ---
        Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(15.dp)) {
            Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Info, contentDescription = null, tint = Color.Gray)
                Spacer(modifier = Modifier.width(10.dp))
                Text("Materiali disponibili (Anteprima)", color = Color.Gray)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // --- BOTTONE UNISCITI ---
        Button(
            onClick = onJoin,
            modifier = Modifier.fillMaxWidth().height(60.dp),
            colors = ButtonDefaults.buttonColors(containerColor = NeonCyan),
            shape = RoundedCornerShape(20.dp)
        ) {
            Text("UNISCITI ORA", color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 18.sp)
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Bottone Indietro
    }
}

// Componente per visualizzare una singola riga di partecipante
// Sostituisci questa funzione in fondo a ExploreDetailScreen.kt
@Composable
fun PartecipanteRow(partecipante: Partecipante) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFFAFAFA), RoundedCornerShape(10.dp))
            .padding(10.dp)
    ) {
        // NUOVO AVATAR
        UserAvatar(name = partecipante.nome)

        Spacer(modifier = Modifier.width(12.dp))

        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = partecipante.nome, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                if (partecipante.isOwner) {
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(text = "👑", fontSize = 16.sp)
                }
            }
            if (partecipante.isMe) {
                Text(text = "(Tu)", fontSize = 12.sp, color = Color.Gray)
            } else if (partecipante.isOwner) {
                Text(text = "Organizzatore", fontSize = 12.sp, color = AccentBlue)
            }
        }
    }
}

// Componente helper per le info (Data, Ora, Codice)
@Composable
fun InfoItem(icon: androidx.compose.ui.graphics.vector.ImageVector, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(imageVector = icon, contentDescription = null, tint = Color.DarkGray)
        Text(text = label, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
    }
}