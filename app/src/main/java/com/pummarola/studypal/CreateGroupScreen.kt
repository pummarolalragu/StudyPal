package com.pummarola.studypal

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pummarola.studypal.ui.theme.NeonPink

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateGroupScreen(onBack: () -> Unit, onCreate: () -> Unit) {
    // Variabili per memorizzare quello che scrive l'utente
    var courseName by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    var time by remember { mutableStateOf("") }
    var isPrivate by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFAFAFA))
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Nuovo Gruppo",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(30.dp))

        // Campo Nome del Corso (Correzione Task 3)
        OutlinedTextField(
            value = courseName,
            onValueChange = { courseName = it },
            label = { Text("Nome del Corso") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(15.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Campo Data
        OutlinedTextField(
            value = date,
            onValueChange = { date = it },
            label = { Text("Data (es. 15/07/25)") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(15.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Campo Ora
        OutlinedTextField(
            value = time,
            onValueChange = { time = it },
            label = { Text("Ora (es. 15:00)") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(15.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Switch Privacy
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = if (isPrivate) "Sessione Privata" else "Sessione Pubblica", fontSize = 18.sp)
            Switch(
                checked = isPrivate,
                onCheckedChange = { isPrivate = it },
                colors = SwitchDefaults.colors(checkedThumbColor = NeonPink)
            )
        }

        Spacer(modifier = Modifier.height(40.dp))

        // Bottone Crea
        Button(
            onClick = onCreate,
            modifier = Modifier.fillMaxWidth().height(60.dp),
            colors = ButtonDefaults.buttonColors(containerColor = NeonPink),
            shape = RoundedCornerShape(20.dp)
        ) {
            Text("CREA SESSIONE", fontWeight = FontWeight.Bold, fontSize = 18.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Bottone Annulla
        TextButton(onClick = onBack) {
            Text("Annulla", color = Color.Gray)
        }
    }
}