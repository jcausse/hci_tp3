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
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.grupo9.easyiot.R
import com.grupo9.easyiot.model.routines.RoutineResult


@Composable
fun RoutinesScreen(
    routinesState: RoutinesState,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
){

    when ( routinesState ){
        is RoutinesState.Loading -> {
            LoadingRoutineScreen()
        }
        is RoutinesState.Success -> {
            SuccessRoutineScreen(routinesState.get.result)
        }
        is RoutinesState.Error -> {
            ErrorRoutineScreen()
        }
    }
}

@Composable
fun LoadingRoutineScreen(){
    // TODO
}

@Composable
fun SuccessRoutineScreen(routines: List<RoutineResult>){
    Column (
        modifier = Modifier.fillMaxSize()
    ){
        Title(text = stringResource(id = R.string.routines))
        CardList()
    }
}

@Composable
fun ErrorRoutineScreen(){
    // TODO
}
/*
@Preview
@Composable
fun RoutinesScreenPreview(){
    RoutinesScreen()
}
*/


@Composable
fun RoutineCard(name: String, time: String, description: String) {
    Card(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .height(200.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White, contentColor = MaterialTheme.colorScheme.primary),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column (
            modifier = Modifier.padding(16.dp).fillMaxSize()
        ) {
            Row (
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Title(name)
                Icon(
                    imageVector = Icons.Rounded.PlayArrow,
                    contentDescription = "Play",
                    modifier = Modifier.padding(32.dp).clickable {
                        playRoutine()
                    }
                )
            }
            Text(
                modifier = Modifier.padding(16.dp),
                text = description)
            Text(text = time, Modifier.align(Alignment.End))
        }
    }
}

@Composable
fun Title ( text: String ){
    Text(
        modifier = Modifier.padding(16.dp),
        fontSize = 32.sp,
        color = MaterialTheme.colorScheme.primary,
        text = text
    )
}
@Preview(showBackground = true)
@Composable
fun CardList() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        items((1..20).toList()) { index ->
            RoutineCard(name = "A mimir", time = "LMMJVSD", description = "Zzzzzz" )
        }
    }
}

fun playRoutine(){
    //TODO

}