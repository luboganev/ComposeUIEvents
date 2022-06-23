package dev.luboganev.composeuievents

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import dev.luboganev.composeuievents.ui.theme.ComposeUIEventsTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val TAG = "ComposeUIEvents"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val mainViewModel: MainViewModel by viewModels()

        mainViewModel.liveData.observe(this) {
            Log.d(TAG, "[A] LiveData: $it")
        }

        lifecycleScope.launch {
            mainViewModel.stateFlow.collect {
                Log.d(TAG, "[A] StateFlow: $it")
            }
        }

        lifecycleScope.launch {
            mainViewModel.sharedFlow.collect {
                Log.d(TAG, "[A] SharedFlow: $it")
            }
        }

        lifecycleScope.launch {
            mainViewModel.sharedFlow1.collect {
                Log.d(TAG, "[A] SharedFlow1: $it")
            }
        }

        setContent {
            val liveData = mainViewModel.liveData.observeAsState()
            val stateFlow = mainViewModel.stateFlow.collectAsState()
            val sharedFlow = mainViewModel.sharedFlow.collectAsState(null)
            val sharedFlow1 = mainViewModel.sharedFlow1.collectAsState(null)

            LaunchedEffect(key1 = liveData.value) {
                Log.d(TAG, "[C] LiveData: ${liveData.value}")
            }
            LaunchedEffect(key1 = stateFlow.value) {
                Log.d(TAG, "[C] StateFlow: ${stateFlow.value}")
            }
            LaunchedEffect(key1 = sharedFlow.value) {
                Log.d(TAG, "[C] SharedFlow: ${sharedFlow.value}")
            }
            LaunchedEffect(key1 = sharedFlow1.value) {
                Log.d(TAG, "[C] SharedFlow1: ${sharedFlow1.value}")
            }

            ComposeUIEventsTheme {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "With delay")
                        Checkbox(
                            checked = mainViewModel.withDelayState.value,
                            onCheckedChange = { mainViewModel.withDelayState.value = it })
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "LiveData")
                        Spacer(modifier = Modifier.weight(1.0f))
                        Button(onClick = mainViewModel::postLiveData) {
                            Text(text = "Post")
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Button(onClick = mainViewModel::setLiveData) {
                            Text(text = "Set")
                        }
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "StateFlow")
                        Spacer(modifier = Modifier.weight(1.0f))
                        Button(onClick = mainViewModel::setStateFlow) {
                            Text(text = "Set")
                        }
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "SharedFlow")
                        Spacer(modifier = Modifier.weight(1.0f))
                        Button(onClick = mainViewModel::tryEmitSharedFlow) {
                            Text(text = "TryEmit")
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Button(onClick = mainViewModel::emitSharedFlow) {
                            Text(text = "Emit")
                        }
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "SharedFlow(1)")
                        Spacer(modifier = Modifier.weight(1.0f))
                        Button(onClick = mainViewModel::tryEmitSharedFlow1) {
                            Text(text = "TryEmit")
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Button(onClick = mainViewModel::emitSharedFlow1) {
                            Text(text = "Emit")
                        }
                    }
                }
            }
        }
    }
}