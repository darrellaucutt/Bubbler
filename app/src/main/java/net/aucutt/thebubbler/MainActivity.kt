package net.aucutt.thebubbler

import android.os.Bundle
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import net.aucutt.thebubbler.ui.theme.TheBubblerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

        }
    }
}

@Composable
fun Login() {

    var username by remember { mutableStateOf(TextFieldValue(""))}
    var password by remember { mutableStateOf(TextFieldValue(""))}
    var validUserName by remember { mutableStateOf(false )}
    var validPassword by remember { mutableStateOf(false )}


        Column( modifier = Modifier
            .padding(16.dp)
            .padding(top = 32.dp)
            .fillMaxSize()) {
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
                        onValueChange = { newText -> password = newText
                            validPassword = password.text.length >= 2
                        }

                    )
                }
            }
            Row(horizontalArrangement = Arrangement.Center, modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)) {
                Button(onClick = {if(!loginPressed( username.text, password.text)) {
                       username = TextFieldValue("")
                       password = TextFieldValue("")
                       validUserName = false
                       validPassword = false
                    } else {

                      }
                    }
                                  ,
                    Modifier
                        .clickable{ validUserName && validPassword}) {
                    Text("Login")
                }
            }

        }
    }

fun loginPressed( username : String, password : String) : Boolean {
    val stupidCheck = hashMapOf<String, String>(Pair("test", "passwword1"),
    Pair("test1","password1"), Pair("test","test"))

    if (stupidCheck.containsKey(username)) {
        if (stupidCheck["username"].equals(password) ) {
           return true
        }
    }

    return false
}

@Composable
fun LoginTwo() {

    var username by remember { mutableStateOf(TextFieldValue(""))}
    var password by remember { mutableStateOf(TextFieldValue(""))}
    Row {
        Column(modifier = Modifier.fillMaxWidth() ) {
            Column( modifier = Modifier
                .width(150.dp),
                horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Username: ", style = MaterialTheme.typography.headlineMedium)

            }
            Column ( ){
                TextField(
                    value = username,
                    onValueChange = { newText -> username = newText }

                )
            }
        }
        Column(modifier = Modifier.fillMaxWidth()) {
            Column( modifier = Modifier.
            width(150.dp),
                horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Password: ", style = MaterialTheme.typography.headlineMedium)

            }
            Column {
                TextField(
                    value = password,
                    onValueChange = { newText -> password = newText }

                )
            }
        }

    }

}

@Preview(showBackground = true)
@Composable
fun LoginPreview() {
   Login()
  //  LoginTwo()
}