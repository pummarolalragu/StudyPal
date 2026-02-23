package com.pummarola.studypal

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pummarola.studypal.ui.theme.LiveGreen

data class Amico(val id: Int, val nome: String, val isOnline: Boolean)

@Composable
fun FriendsScreen() {
    val initialFriends = listOf(
        Amico(1, "Marco Rossi", true),
        Amico(2, "Giulia Verdi", false),
        Amico(3, "Luca Bianchi", true),
        Amico(4, "Sofia Esposito", false)
    )

    var friendsList by remember { mutableStateOf(initialFriends) }
    var searchText by remember { mutableStateOf("") }

    val filteredFriends = friendsList.filter {
        it.nome.contains(searchText, ignoreCase = true)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        Text(text = "I miei Amici", fontSize = 32.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = searchText,
            onValueChange = { searchText = it },
            label = { Text("Cerca o aggiungi amico...") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(25.dp),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            items(filteredFriends) { amico ->
                FriendRow(
                    amico = amico,
                    onRemove = { friendsList = friendsList.filter { it.id != amico.id } }
                )
            }
            if (filteredFriends.isEmpty()) {
                item { Text("Nessun amico trovato.", color = Color.Gray, modifier = Modifier.padding(top = 20.dp)) }
            }
        }
    }
}

@Composable
fun FriendRow(amico: Amico, onRemove: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(15.dp),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp).fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                // NUOVO AVATAR
                UserAvatar(name = amico.nome)
                Spacer(modifier = Modifier.width(12.dp))

                Column {
                    Text(text = amico.nome, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(
                            modifier = Modifier.size(10.dp),
                            shape = CircleShape,
                            color = if (amico.isOnline) LiveGreen else Color.Gray
                        ) {}
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = if (amico.isOnline) "In sessione" else "Offline",
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                    }
                }
            }
            IconButton(onClick = onRemove) {
                Icon(Icons.Default.Close, contentDescription = "Rimuovi", tint = Color.Gray)
            }
        }
    }
}