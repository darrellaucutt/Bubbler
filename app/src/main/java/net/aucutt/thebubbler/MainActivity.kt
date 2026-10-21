package net.aucutt.thebubbler

import android.R
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
import net.aucutt.thebubbler.ui.theme.Annoy
import net.aucutt.thebubbler.ui.theme.Annoy1
import net.aucutt.thebubbler.ui.theme.Annoy2
import net.aucutt.thebubbler.ui.theme.Annoy3
import net.aucutt.thebubbler.ui.theme.Annoy4
import net.aucutt.thebubbler.ui.theme.Annoy5
import net.aucutt.thebubbler.ui.theme.Annoy6
import net.aucutt.thebubbler.ui.theme.Annoy7
import net.aucutt.thebubbler.ui.theme.Annoy8
import net.aucutt.thebubbler.ui.theme.Pink80
import net.aucutt.thebubbler.ui.theme.Purple80
import kotlin.time.Duration.Companion.milliseconds

class MainActivity : ComponentActivity() {

    private val _stateFlow = MutableStateFlow(0)
    val stateFlow = _stateFlow.asStateFlow()

    private val _colorFlow = MutableStateFlow(Color.Cyan)
    val colorFlow = _colorFlow.asStateFlow()

    private val _colorFlow2 = MutableStateFlow(Color.Cyan)
    val colorFlow2 = _colorFlow.asStateFlow()

    val colors = arrayOf( Color.Cyan,Pink80, Annoy, Annoy1, Annoy2, Annoy3, Annoy4,
        Annoy5, Annoy6, Annoy7, Annoy8)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                while (true) { //TODO be smarter about this
                    delay(100.milliseconds)
                    val newValue = (Math.random() * 430 ).toInt()
                    _stateFlow.value = newValue
                    delay(100.milliseconds)
                    val colorValue = (Math.random()* colors.size).toInt()
                    _colorFlow.value = colors[colorValue]
                    delay(100.milliseconds)
                    val colorValue2 = (Math.random()* colors.size).toInt()
                    _colorFlow2.value = colors[colorValue2]
                }
            }
        }
        enableEdgeToEdge()
        setContent {
            BubbleTime()
//                if (login()) {
//                    println("can bubble")
//                    BubbleTime()
//                }

        }
    }


    @Composable
    fun login() : Boolean {

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

            }

        }
        return canBubble
    }

    @Composable
    fun BubbleTime() {
        var updateColor by remember { mutableStateOf(Color.Cyan) }
        var updateRadius by remember { mutableIntStateOf(0) }
        var updateColor2 by remember { mutableStateOf(Color.Black) }

        Column( verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier =  Modifier
            .fillMaxSize()
            .padding(16.dp)
            .padding(top = 64.dp)) {
            Box {
                Box(
                    modifier = Modifier
                        .size(updateRadius.dp)
                        .clip(RoundedCornerShape(100))
                        .background(updateColor)
                )
                Box(
                    modifier = Modifier
                        .size((updateRadius/2).dp)
                        .clip(RoundedCornerShape(10))
                        .background(updateColor2)
                        .padding(all = 32.dp)
                )
            }
        }
        LaunchedEffect(Unit) {
            stateFlow.collect { newData ->
                println("Collected $newData")
                updateRadius = newData
            }

        }
        LaunchedEffect(Unit) {

            colorFlow.collect { newColor ->
                updateColor = newColor
            }
        }

        LaunchedEffect(Unit) {

            colorFlow2.collect { newColor ->
                updateColor2 = newColor
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
@Composable
fun BubbleTime() {
    //Surface {
        Column( verticalArrangement = Arrangement.Center,
                 horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
        .fillMaxSize()
            .padding(16.dp)
            .padding(top = 64.dp)) {

            Box(
                modifier = Modifier
                    .size(150.dp)
                    .clip(RoundedCornerShape(100))
                    .background(Color.Cyan)
            )
        }
    //}

}

@Preview
@Composable
fun BubbleTimePreview() {
   BubbleTime()
}
