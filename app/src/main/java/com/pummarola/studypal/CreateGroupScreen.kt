package com.pummarola.studypal

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.pummarola.studypal.ui.theme.AccentBlue

val amiciDisponibili = listOf("Marco Rossi", "Giulia Verdi", "Luca Bianchi", "Sofia Esposito")

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateGroupScreen(onBack: () -> Unit, onCreate: () -> Unit) {
    var courseName by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    var time by remember { mutableStateOf("") }
    var isPrivate by remember { mutableStateOf(false) }

    var showInviteDialog by remember { mutableStateOf(false) }
    var selectedFriends by remember { mutableStateOf(setOf<String>()) }

    Column(
        modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background).padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Nuovo Gruppo", fontSize = 32.sp, fontWeight = FontWeight.Bold, color = Color.Black)
        Spacer(modifier = Modifier.height(30.dp))

        OutlinedTextField(value = courseName, onValueChange = { courseName = it }, label = { Text("Nome del Corso") }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(15.dp))
        Spacer(modifier = Modifier.height(16.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            OutlinedTextField(value = date, onValueChange = { date = it }, label = { Text("Data") }, modifier = Modifier.weight(1f), shape = RoundedCornerShape(15.dp))
            OutlinedTextField(value = time, onValueChange = { time = it }, label = { Text("Ora") }, modifier = Modifier.weight(1f), shape = RoundedCornerShape(15.dp))
        }
        Spacer(modifier = Modifier.height(24.dp))

        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = if (isPrivate) "Sessione Privata" else "Sessione Pubblica", fontSize = 18.sp)
            Switch(checked = isPrivate, onCheckedChange = { isPrivate = it }, colors = SwitchDefaults.colors(checkedThumbColor = AccentBlue, checkedTrackColor = AccentBlue.copy(alpha = 0.5f)))
        }
        Spacer(modifier = Modifier.height(24.dp))

        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Text(text = "Invita Amici:", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.width(16.dp))

            IconButton(onClick = { showInviteDialog = true }, modifier = Modifier.background(AccentBlue.copy(alpha = 0.1f), CircleShape)) {
                Icon(Icons.Default.Add, contentDescription = "Aggiungi", tint = AccentBlue)
            }
            Spacer(modifier = Modifier.width(16.dp))

            Row(horizontalArrangement = Arrangement.spacedBy((-10).dp)) {
                selectedFriends.forEach { friendName ->
                    // NUOVO AVATAR PER GLI INVITATI
                    UserAvatar(name = friendName, size = 40.dp)
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(onClick = onCreate, modifier = Modifier.fillMaxWidth().height(60.dp), colors = ButtonDefaults.buttonColors(containerColor = AccentBlue), shape = RoundedCornerShape(15.dp)) {
            Text("CREA SESSIONE", fontWeight = FontWeight.Bold, fontSize = 18.sp)
        }
    }

    if (showInviteDialog) {
        Dialog(onDismissRequest = { showInviteDialog = false }) {
            Card(modifier = Modifier.fillMaxWidth().padding(16.dp), shape = RoundedCornerShape(20.dp), colors = CardDefaults.cardColors(containerColor = Color.White)) {
                Column(modifier = Modifier.padding(24.dp)) {
                    Text("Seleziona Amici", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(16.dp))

                    LazyColumn(modifier = Modifier.heightIn(max = 300.dp)) {
                        items(amiciDisponibili) { amico ->
                            Row(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    UserAvatar(name = amico, size = 30.dp) // Avatar piccolo nel popup
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Text(amico, fontSize = 16.sp)
                                }
                                Checkbox(
                                    checked = selectedFriends.contains(amico),
                                    onCheckedChange = { isChecked -> selectedFriends = if (isChecked) selectedFriends + amico else selectedFriends - amico },
                                    colors = CheckboxDefaults.colors(checkedColor = AccentBlue)
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = { showInviteDialog = false }, modifier = Modifier.fillMaxWidth(), colors = ButtonDefaults.buttonColors(containerColor = AccentBlue)) {
                        Text("Conferma")
                    }
                }
            }
        }
    }
}