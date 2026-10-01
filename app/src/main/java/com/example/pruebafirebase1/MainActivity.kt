package com.example.pruebafirebase1

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pruebafirebase1.ui.theme.Pruebafirebase1Theme
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import kotlin.jvm.java

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Pruebafirebase1Theme {
                LoginScreen()
            }
        }
    }
}

@Composable
fun LoginScreen(){

    val context = LocalContext.current
    val db = Firebase.firestore

    val auth = FirebaseAuth.getInstance()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 50.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = email,
            onValueChange = {email = it},
            label = {Text("Email")}
        )

        OutlinedTextField(
            value = password,
            onValueChange = {password = it},
            label = {Text("Contraseña")},
            visualTransformation = PasswordVisualTransformation()
        )

        Button(
            onClick = {autentificacion(email, password, auth, context)}
        ) {
            Text("Login")
        }

        Button(
            onClick = {}
        ) {
            Text("Registratu")
        }

    }

}

fun autentificacion(email: String, password: String, auth: FirebaseAuth, context: Context){
    auth.signInWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val usuario = auth.currentUser

                Toast.makeText(context, "Bien hecho", Toast.LENGTH_LONG).show()

                val intent = Intent(context, InicioActivity::class.java)
                intent.putExtra("usuario", email)
                context.startActivity(intent)

            }else {
                Toast.makeText(context, "No va", Toast.LENGTH_LONG).show()
            }

        }
}

fun registroUsuario(email: String, password: String, auth: FirebaseAuth, context: Context){
    auth.createUserWithEmailAndPassword(email, password)
        .addOnCompleteListener(context as ComponentActivity) { task ->
            if(task.isSuccessful){
                val uid = auth.currentUser?.uid
                if(uid != null){

                }
            }
        }
}
