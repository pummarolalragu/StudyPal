package com.pummarola.studypal

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.pummarola.studypal.ui.theme.AccentBlue
import com.pummarola.studypal.ui.theme.AccentLightBlue
import com.pummarola.studypal.ui.theme.NeonCyan

@Composable
fun SessionDetailScreen(materia: String, onBack: () -> Unit, navController: NavController) {
    val sessione = mieSessioni.find { it.materia == materia } ?: mieSessioni[0]

    Column(
        modifier = Modifier.fillMaxSize().background(Color.White).padding(24.dp)
    ) {
        Text(text = sessione.materia, fontSize = 26.sp, fontWeight = FontWeight.ExtraBold)
        Badge(containerColor = Color(0xFF2E7D32)) {
            Text("SESSIONE LIVE", color = Color.White, modifier = Modifier.padding(4.dp))
        }

        Spacer(modifier = Modifier.height(20.dp))

        // INFO CARD (Data, Ora, Codice)
        Card(colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5)), shape = RoundedCornerShape(15.dp)) {
            Row(modifier = Modifier.padding(16.dp).fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                InfoItem(icon = Icons.Default.DateRange, label = sessione.data)
                InfoItem(icon = Icons.Default.Info, label = sessione.orario)
                InfoItem(icon = Icons.Default.Lock, label = "Cod: ${sessione.codice}")
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // LISTA PARTECIPANTI
        Text(text = "Partecipanti (${sessione.partecipanti} Totali):", fontWeight = FontWeight.Bold)
        LazyColumn(modifier = Modifier.weight(1f).padding(vertical = 8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(sessione.partecipantiList) { partecipante ->
                PartecipanteRow(partecipante)
            }
        }

        // ALLEGATI
        Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(15.dp)) {
            Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.List, contentDescription = null)
                Spacer(modifier = Modifier.width(10.dp))
                Text("Appunti_Lezione_1.pdf", fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.weight(1f))
                Icon(Icons.Default.ArrowDropDown, contentDescription = null)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // BOTTONE CHAT
        Button(
            onClick = { navController.navigate("chat/$materia") },
            modifier = Modifier.fillMaxWidth().height(60.dp),
            colors = ButtonDefaults.buttonColors(containerColor = NeonCyan),
            shape = RoundedCornerShape(20.dp)
        ) {
            Icon(Icons.Default.Email, contentDescription = null, tint = Color.Black)
            Spacer(modifier = Modifier.width(10.dp))
            Text("ENTRA IN CHAT", color = Color.Black, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(12.dp))

        // TASTO ESCI
        Button(
            onClick = onBack,
            modifier = Modifier.fillMaxWidth().height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFCDD2)),
            shape = RoundedCornerShape(15.dp)
        ) {
            Text("Esci dalla sessione", color = Color(0xFFB71C1C), fontWeight = FontWeight.Bold)
        }
    }
}