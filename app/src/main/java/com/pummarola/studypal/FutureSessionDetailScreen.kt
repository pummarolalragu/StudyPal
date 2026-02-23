package com.pummarola.studypal

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun FutureSessionDetailScreen(materia: String, onBack: () -> Unit) {
    val sessione = mieSessioni.find { it.materia == materia } ?: mieSessioni[1]
    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize().background(Color.White).padding(24.dp)
    ) {
        Text(text = sessione.materia, fontSize = 26.sp, fontWeight = FontWeight.ExtraBold)
        Badge(containerColor = Color.Blue) {
            Text("PROGRAMMATA", color = Color.White, modifier = Modifier.padding(4.dp))
        }

        Spacer(modifier = Modifier.height(20.dp))

        // INFO CARD
        Card(colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5)), shape = RoundedCornerShape(15.dp)) {
            Row(modifier = Modifier.padding(16.dp).fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                InfoItem(icon = Icons.Default.DateRange, label = sessione.data)
                InfoItem(icon = Icons.Default.Info, label = sessione.orario)
                InfoItem(icon = Icons.Default.Lock, label = "Cod: ${sessione.codice}")
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // LISTA PARTECIPANTI
        Text(text = "Partecipanti iscritti (${sessione.partecipanti} Totali):", fontWeight = FontWeight.Bold)
        LazyColumn(modifier = Modifier.weight(1f).padding(vertical = 8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(sessione.partecipantiList) { partecipante ->
                PartecipanteRow(partecipante)
            }
        }

        // MATERIALI (Anteprima)
        Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(15.dp)) {
            Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Info, contentDescription = null, tint = Color.Gray)
                Spacer(modifier = Modifier.width(10.dp))
                Text("Materiali disponibili all'inizio", color = Color.Gray)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // TASTO ANNULLA ISCRIZIONE
        Button(
            onClick = {
                Toast.makeText(context, "Iscrizione annullata", Toast.LENGTH_SHORT).show()
                onBack()
            },
            modifier = Modifier.fillMaxWidth().height(60.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFCDD2)),
            shape = RoundedCornerShape(20.dp)
        ) {
            Text("ANNULLA ISCRIZIONE", color = Color(0xFFB71C1C), fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(10.dp))

    }
}