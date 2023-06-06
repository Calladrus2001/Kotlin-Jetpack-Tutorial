package com.example.tutorial1

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                com.example.tutorial1.CircularProgressIndicator(percentage = 0.75f, number = 75)
            }

        }
    }
}

@Composable
fun LazyColumnExample() {
    val itemslist = listOf<String>("This", "is", "jetpack", "compose")
    LazyColumn(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        items(itemslist) {
            Text(text = it, style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.ExtraBold))
        }
    }
}

@Composable
fun CircularProgressIndicator(
    percentage: Float,
    number: Int,
    fontSize: TextUnit = 28.sp,
    radius: Dp = 50.dp,
    color: Color = Color.Green,
    strokeWidth: Dp = 8.dp,
    animDuration: Int = 1000,
    animDelay : Int = 0
) {
    var animationPlayed by remember {
        mutableStateOf(false)
    }
    val currPercentage = animateFloatAsState(
        targetValue = if (animationPlayed) percentage else 0f,
        animationSpec = tween(
            durationMillis = animDuration,
            delayMillis = animDelay
        )
    )

    LaunchedEffect(key1 = true) {
        animationPlayed = true
    }
    
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.size(radius * 2f)
    ) {
        Canvas(modifier = Modifier.size(radius * 2f)) {
            drawArc(
                color,
                startAngle = -90f,
                sweepAngle = 360 * currPercentage.value,
                useCenter = false,
                style = Stroke(strokeWidth.toPx(), cap = StrokeCap.Round)
            )
        }
        
        Text(text = "hi", style = TextStyle(fontSize = fontSize))
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun GreetingBox() {
    val ScaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    var textFieldVal by remember {
        mutableStateOf("")
    }
    Scaffold(
        modifier = Modifier.fillMaxSize(), scaffoldState = ScaffoldState
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp, vertical = 12.dp)
        ) {
            OutlinedTextField(value = textFieldVal,
                label = {
                    Text("Enter your name")
                },
                singleLine = true,
                onValueChange = {
                    textFieldVal = it
                })
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                scope.launch {
                    ScaffoldState.snackbarHostState.showSnackbar("Hello! $textFieldVal")
                }
            }) {
                Text(text = "Greet me")
            }

        }
    }
}

@Composable
fun ColorBox() {
    val clr = remember {
        mutableStateOf(Color.Yellow)
    }
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(fraction = 0.5f)
            .background(color = Color.Red)
            .clickable { clr.value = Color.Cyan }) {}
    }
}

@Composable
fun ImageCard(
    painter: Painter, description: String, title: String, modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .background(color = Color.White)
            .fillMaxWidth(),
        shape = RoundedCornerShape(size = 15.dp),
        elevation = 5.dp,
    ) {
        Box(
            modifier = Modifier.height(150.dp)
        ) {
            Image(
                painter = painter,
                contentDescription = description,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(4.dp)
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(Color.Transparent, Color.Black), startY = 290f
                        )
                    )
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(4.dp),
                contentAlignment = Alignment.BottomCenter
            ) {
                Text(title, style = TextStyle(color = Color.White, fontSize = 16.sp))
            }
        }

    }
}
