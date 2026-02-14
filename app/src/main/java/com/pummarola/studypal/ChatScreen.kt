package com.pummarola.studypal

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pummarola.studypal.ui.theme.NeonCyan
import com.pummarola.studypal.ui.theme.NeonYellow

// Modello per un messaggio finto
data class ChatMessage(val sender: String, val text: String, val isMe: Boolean)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(onBack: () -> Unit, materia: String) {
    // Lista di messaggi finti, coerenti con il contesto "Studio"
    val initialMessages = listOf(
        ChatMessage("Antonio", "Ciao a tutti, possiamo iniziare?", isMe = false),
        ChatMessage("Tu", "Certo, io sono pronto! Ho salvato gli appunti della lezione di ieri in allegati.", isMe = true),
        ChatMessage("Gabriele", "Perfetto, aspettate 2 minuti che finisco un'altra cosa.", isMe = false),
        ChatMessage("Tu", "Ok, non c'è fretta. Io resto qui.", isMe = true)
    )

    // Stato per memorizzare i messaggi (anche se qui è statico, è buona pratica)
    var messages by remember { mutableStateOf(initialMessages) }
    var messageInput by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Header della Chat (con il titolo della sessione)
        TopAppBar(
            title = { Text("$materia Chat", fontWeight = FontWeight.Bold) },
            navigationIcon = {
                IconButton(onClick = onBack) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Indietro")
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = NeonCyan)
        )

        // Lista dei messaggi (il corpo della chat)
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            reverseLayout = true // Per far vedere l'ultimo messaggio in basso
        ) {
            items(messages.reversed()) { message ->
                MessageBubble(message = message)
            }
        }

        // Area per scrivere il nuovo messaggio
        BottomAppBar(
            containerColor = Color.LightGray,
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = messageInput,
                onValueChange = { messageInput = it },
                label = { Text("Scrivi un messaggio...") },
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp),
                shape = RoundedCornerShape(25.dp)
            )

            IconButton(
                onClick = {
                    if (messageInput.isNotBlank()) {
                        // Aggiunge il messaggio (finto, visto che non abbiamo un backend)
                        messages = messages + ChatMessage("Tu", messageInput, isMe = true)
                        messageInput = ""
                    }
                },
                modifier = Modifier.padding(end = 8.dp)
            ) {
                Icon(Icons.AutoMirrored.Filled.Send, contentDescription = "Invia", tint = NeonCyan)
            }
        }
    }
}

@Composable
fun MessageBubble(message: ChatMessage) {
    val alignment = if (message.isMe) Alignment.End else Alignment.Start
    val bubbleColor = if (message.isMe) NeonYellow else Color.LightGray
    val textColor = Color.Black

    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = alignment) {
        Text(
            text = message.sender,
            fontSize = 12.sp,
            color = Color.DarkGray,
            modifier = Modifier.padding(horizontal = 8.dp)
        )
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = bubbleColor
        ) {
            Text(
                text = message.text,
                modifier = Modifier.padding(10.dp),
                color = textColor
            )
        }
    }
}
