package com.lj.app.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.URL
import kotlin.system.measureTimeMillis

/**
 * Demonstrate coroutine performance with different dispatchers.
 */
@Composable
fun HelloWorldComposable() {

    var message by remember { mutableStateOf("") }
    var elapsedTime by remember { mutableStateOf(0L) }
    val coroutineScope = rememberCoroutineScope()

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(8.dp)) {

        Text(text = "Example 1", fontWeight = FontWeight.Bold)
        Text(text = "Updates the message on the screen 100,000 times. This task is optimized by the Main dispatcher. The speed is such that the message cannot be updated. Only the final result is displayed.")
        Text(text = "Other dispatchers display the results in real-time.")

        Row(modifier = Modifier
            .align(Alignment.CenterHorizontally)
            .padding(bottom = 8.dp, top = 8.dp)) {

            Button(onClick = {
                coroutineScope.launch(Dispatchers.Default) {
                    elapsedTime = measureTimeMillis {
                        repeat(100000) {
                            message = it.toString()
                        }
                    }
                }
            }) {
                Text(text = "Default")
            }
            Button(modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                onClick = {
                coroutineScope.launch(Dispatchers.Main) {
                    elapsedTime = measureTimeMillis {
                        repeat(100000) {
                            message = it.toString()
                        }
                    }
                }
            }) {
                Text(text = "Main")
            }
            Button(onClick = {
                coroutineScope.launch(Dispatchers.IO) {
                    elapsedTime = measureTimeMillis {
                        repeat(100000) {
                            message = it.toString()
                        }
                    }
                }
            }) {
                Text(text = "IO")
            }
        }

        Text(text = "Example 2", fontWeight = FontWeight.Bold)
        Text(text = "Retrieve the content of a Wikipedia page. This task is optimized by the IO dispatcher.")
        Text(text = "The Main dispatcher does not allow making a web request: the task is fast but fails.")

        Row(modifier = Modifier
            .align(Alignment.CenterHorizontally)
            .padding(bottom = 8.dp, top = 8.dp)) {

            Button(onClick = {
                coroutineScope.launch(Dispatchers.Default) {
                    elapsedTime = measureTimeMillis {
                        fetchContentFromUrl("https://en.wikipedia.org/wiki/Main_Page")
                        message = fetchContentFromUrl("https://en.wikipedia.org/wiki/Main_Page")

                    }
                }
            }) {
                Text(text = "Default")
            }
            Button(modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                onClick = {
                coroutineScope.launch(Dispatchers.Main) {
                    elapsedTime = measureTimeMillis {
                        fetchContentFromUrl("https://en.wikipedia.org/wiki/Main_Page")
                        message = fetchContentFromUrl("https://en.wikipedia.org/wiki/Main_Page")

                    }
                }
            }) {
                Text(text = "Main")
            }
            Button(onClick = {
                coroutineScope.launch(Dispatchers.IO) {
                    elapsedTime = measureTimeMillis {
                        message = fetchContentFromUrl("https://en.wikipedia.org/wiki/Main_Page")
                    }
                }
            }) {
                Text(text = "IO")
            }
        }

        Row {
            Text(text = "Result : ", fontWeight = FontWeight.Bold)
            Text(text = message)
        }
        Row {
            Text(text = "Elapsed time : ", fontWeight = FontWeight.Bold)
            Text(text = elapsedTime.toString())
        }
    }
}

/**
 * Fetches content from a given URL and returns a message.
 * @param url The URL from which content is fetched.
 * @return A message indicating the status of the content fetch.
 */
fun fetchContentFromUrl(url: String) : String {
    try {
        val connection = URL(url).openConnection()
        connection.connectTimeout = 3000
        connection.readTimeout = 3000

        val inputStream = connection.getInputStream()
        inputStream.bufferedReader().use { it.readText() }

        inputStream.close()
        return "Downloaded !"
    } catch (e: Exception) {
        e.printStackTrace()
        return "Connection error !"
    }
}
