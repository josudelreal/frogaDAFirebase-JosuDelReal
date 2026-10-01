package com.example.pruebafirebase1


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.pruebafirebase1.ui.theme.Pruebafirebase1Theme
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import kotlin.text.get

data class ikasleakData (
    val nan: String = "",
    val izena: String = "",
    val abizena: String = ""
)

class IkasleakActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Pruebafirebase1Theme {
                Alumnos()
            }
        }
    }
}

@Composable
fun Alumnos(){

    val context = LocalContext.current
    //val db = Firebase.firestore

    val db = FirebaseFirestore.getInstance()

    val auth = FirebaseAuth.getInstance()

    var text by remember { mutableStateOf("Todavía no hay datos") }
    var nan by remember { mutableStateOf("") }
    var izena by remember { mutableStateOf("") }
    var abizena by remember { mutableStateOf("") }

    fun garbituElementuak(){
        nan = ""
        izena = ""
        abizena = ""
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 50.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            OutlinedTextField(
                value = nan,
                onValueChange = { nan = it },
                label = { Text("NANa") }
            )

            OutlinedTextField(
                value = izena,
                onValueChange = { izena = it },
                label = { Text("Izena") }
            )

            OutlinedTextField(
                value = abizena,
                onValueChange = { abizena = it },
                label = { Text("Abizena") }
            )


            Spacer(modifier = Modifier.padding(10.dp))

            Text(text)

            Spacer(modifier = Modifier.padding(10.dp))

            Button(
                onClick = {
                    val alumno = ikasleakData(
                        nan = nan,
                        izena = izena,
                        abizena = abizena
                    )

                    db.collection("alumnos")
                        .add(alumno)
                        .addOnSuccessListener { documentReference ->
                            Log.d("Firestore", "Documento guardado con éxito. ID: ${documentReference.id}")
                            Toast.makeText(context, "Documento guardado con éxito. ID: ${documentReference.id}", Toast.LENGTH_SHORT).show()
                        }
                        .addOnFailureListener { e ->
                            Log.d("Firestore", "Error al añadir el alumno", e)
                            Toast.makeText(context, "Error al añadir el alumno", Toast.LENGTH_SHORT).show()
                        }

                    garbituElementuak()
                }
            ) {
                Text("Gorde")
            }

            Button(
                onClick = {}
            ) {
                Text("Ezabatu")
            }

            Button(
                onClick = {//Erakutsi(db){text = it}
                }
            ) {
                Text("Erakutsi")
            }

            Button(
                onClick = {
                    val intent = Intent(context, MainActivity::class.java)
                    context.startActivity(intent)
                }
            ) {
                Text("Itzuli")
            }
        }
    }

    fun Erakutsi(){
        db.collection("alumnos").get()
            .addOnSuccessListener { alumnosList ->
                var datuak = ""
                for (alumno in alumnosList) {
                    datuak += "${alumno.id} : ${alumno.data}\n"
                }
                text = if (datuak.isEmpty()) "Ez dago ikaslerik" else datuak
            }.addOnFailureListener { e ->
                text = "Error: ${e.message}"
            }
    }
}

