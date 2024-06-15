package com.grupo9.easyiot.screens


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.grupo9.easyiot.R
import com.grupo9.easyiot.model.routines.Actions
import com.grupo9.easyiot.model.routines.RoutineResult

@Composable
fun RoutinesScreen(
    routinesState: RoutinesState,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
){
    when ( routinesState ){
        is RoutinesState.Loading -> {
            LoadingRoutineScreen( modifier, contentPadding)
        }
        is RoutinesState.Success -> {
            SuccessRoutineScreen(routinesState.get.result, modifier, contentPadding)
        }
        is RoutinesState.Error -> {
            Text(text = routinesState.message)
            ErrorRoutineScreen()
        }
    }
}


@Composable
fun RoutinesScreenPreview(){
    RoutinesScreen(routinesState = RoutinesViewModel().routinesState )
}

@Composable
fun ErrorRoutineScreen() {
    Text(text = "Error")
}
@Composable
fun LoadingRoutineScreen(modifier: Modifier, contentPadding: PaddingValues){
    Text(text ="loading", modifier = modifier.padding(contentPadding))
}

@Composable
fun SuccessRoutineScreen(routines: List<RoutineResult>, modifier: Modifier, contentPadding: PaddingValues){
    Column (
        modifier = modifier.padding(contentPadding)
    ){
        Title(text = stringResource(id = R.string.routines))
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            items(routines) { routine ->
                RoutineCard(
                    id = routine.id,
                    name = routine.name,
                    time = routine.meta.weekdays,
                    description = routine.meta.description,
                    routine.actions
                )
            }
        }

    }
}


@Composable
fun RoutineCard(id: String, name: String, time: String, description: String, actions: ArrayList<Actions>) {
    Card(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .height(300.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White, contentColor = MaterialTheme.colorScheme.primary),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column (
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize()
        ) {
            Row (
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                CardTitle(name)
                Button(
                    onClick = {
                    }
                ) {
                    PlayRoutine(id = id)
                }
                    Icon(
                        imageVector = Icons.Rounded.PlayArrow,
                        contentDescription = "Play",
                        modifier = Modifier
                            .padding(32.dp)
                            .clickable {

                            }
                    )

            }
            Text(
                modifier = Modifier.padding(16.dp),
                text = description)
            ScrollableList(actions)
            Text(text = time, Modifier.align(Alignment.End))
        }
    }
}

@Composable
fun ScrollableList(actions: ArrayList<Actions>) {
    LazyColumn (
        modifier = Modifier.height(100.dp)
    ){
        items(actions) { action ->
            Text(
                modifier = Modifier.padding(16.dp),
                text = "${action.device.name} -> ${action.actionName}"
            )
        }
    }
}

@Composable
fun Title ( text: String ){
    Text(
        modifier = Modifier.padding(5.dp),
        fontSize = 64.sp,
        color = MaterialTheme.colorScheme.primary,
        text = text,
        fontFamily = kodchasan,
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun CardTitle ( text: String ){
    Text(
        modifier = Modifier.padding(5.dp),
        fontSize = 24.sp,
        color = MaterialTheme.colorScheme.primary,
        text = text
    )
}


@Composable
fun PlayRoutine(id: String, routineViewModel: RoutinesViewModel = RoutinesViewModel()){
    val openAlertDialog = remember { mutableStateOf(false) }
    routineViewModel.executeRoutine(id)
    AlertDialogExample(
        onDismissRequest = { openAlertDialog.value = false },
        onConfirmation = {
            openAlertDialog.value = false
        },
        dialogTitle = "Routine executed",
        dialogText = if (routineViewModel.executeResult) "Routine executed correctly" else "Routine failed",
        icon = Icons.Default.Info
    )
}

@Composable
fun AlertDialogExample(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String,
    icon: ImageVector,
) {
    AlertDialog(
        icon = {
            Icon(icon, contentDescription = "Example Icon")
        },
        title = {
            Text(text = dialogTitle)
        },
        text = {
            Text(text = dialogText)
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmation()
                }
            ) {
                Text("Confirm")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text("Dismiss")
            }
        }
    )
}