package net.aucutt.thebubbler

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.Lifecycle
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

class MainActivity : ComponentActivity() {

    private val _stateFlow = MutableStateFlow(0)
    val stateFlow = _stateFlow.asStateFlow()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                while (true) { //TODO be smarter about this
                    delay(500.milliseconds)
                    val newValue = (Math.random() *9 ).toInt()
                    _stateFlow.value = newValue
                   
                }
            }
        }
        enableEdgeToEdge()
        setContent {
            Login()
        }
    }


    @Composable
    fun Login() {

        var username by remember { mutableStateOf(TextFieldValue("")) }
        var password by remember { mutableStateOf(TextFieldValue("")) }
        var validUserName by remember { mutableStateOf(false) }
        var validPassword by remember { mutableStateOf(false) }
        var canBubble by remember { mutableStateOf(false) }
        val context = LocalContext.current


        Column(
            modifier = Modifier
                .padding(16.dp)
                .padding(top = 64.dp)
                .fillMaxSize()
        ) {

            Row(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier
                        .width(150.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Username: ", style = MaterialTheme.typography.headlineMedium)

                }
                Column() {

                    TextField(
                        value = username,
                        onValueChange = { newText ->
                            username = newText
                            validUserName = username.text.length >= 2
                        },
                    )
                }
            }
            Row(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier.width(150.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Password: ", style = MaterialTheme.typography.headlineMedium)

                }
                Column {
                    TextField(
                        value = password,
                        onValueChange = { newText ->
                            password = newText
                            validPassword = password.text.length >= 2
                        }

                    )
                }
            }
            Row(
                horizontalArrangement = Arrangement.Center, modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {

                Button(
                    onClick = {
                        if (!loginPressed(username.text, password.text)) {
                            username = TextFieldValue("")
                            password = TextFieldValue("")
                            validUserName = false
                            validPassword = false
                            Toast.makeText(context, "invalid combo", Toast.LENGTH_SHORT).show()
                        } else {
                            canBubble = true
                        }

                    },
                    enabled = validUserName && validPassword,
                )
                {
                    Text("Login")
                }
                if (canBubble) {
                    BubbleTime()
                }
            }

        }
    }

    @Composable
    fun BubbleTime() {

        var updateData by remember { mutableStateOf(TextFieldValue("")) }
        Text( text = updateData.text)
        LaunchedEffect(Unit) {
            stateFlow.collect { newData ->
                updateData = TextFieldValue(newData.toString())
            }
        }
    }


    private fun loginPressed(username: String, password: String): Boolean {
        val stupidCheck = hashMapOf<String, String>(
            Pair("test", "passwword1"),
            Pair("test1", "password1"), Pair("test", "test"), Pair("cat", "cat")
        )
        if (stupidCheck.containsKey(username)) {
            if (stupidCheck[username].equals(password)) {
                return true
            }
        }

        return false
    }
}

//@Preview(showBackground = true)
//@Composable
//fun LoginPreview() {
//   Login()
//  //  LoginTwo()
//}