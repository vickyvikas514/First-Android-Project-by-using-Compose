package com.example.basicscodelab

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.basicscodelab.ui.theme.BasicsCodelabTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight






@Composable
fun OnboardingScreen(
    //function parameter to the onboarding screen defined as onContinueClicked: () -> Unit
    // so you can mutate the state from MyApp.
    onContinueClicked: () -> Unit,
                     modifier: Modifier = Modifier,
) {
    // TODO: This state should be hoisted
    //by rememberSaveable UI does not initialsie
    // itself each time when we rotate the device
    var shouldShowOnboarding by rememberSaveable { mutableStateOf(true) }

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Welcome to the Basics Codelab!")
        Button(
            modifier = Modifier.padding(vertical = 24.dp),
            onClick =onContinueClicked
        ) {
            Text("Continue")
        }
    }
}

@Preview(showBackground = true, widthDp = 320, heightDp = 320)
@Composable
fun OnboardingPreview() {
    BasicsCodelabTheme {
        OnboardingScreen(onContinueClicked = {}
        )
        //Assinged empty lamda expression to on ContinueClicked
    // means it do nothing which we need!
    }
}

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            //BasicsCodelabTheme is present in ui.theme it contain material theme
            //which decide the theme for app
            BasicsCodelabTheme {
                // A surface container ..
                // used background's color for the themining of this app
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MyApp()
                }
            }
        }
    }
}


@Composable
private fun Greeting(name: String) {

    var expanded by rememberSaveable { mutableStateOf(false) }
//added animation
    //animateDpAsState takes target value whose value is in dp
    //animation spec help you to coustomize your app
    //added spring effect
    val extrapadding by animateDpAsState(
        if(expanded) 48.dp else 0.dp,


    )
     androidx.compose.material3.Surface(color = MaterialTheme.colorScheme.primary,
    modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ){
        Row(modifier = Modifier.padding(24.dp)
                //Applied the animateContentSize modifier to the Row.
            // This is going to automate the process of creating the animation, which would be hard to do manually.
            // Also, it removes the need to coerceAtLeast
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow,

                    )
            )) {

            Column(modifier = Modifier
                .weight(1f)
                //padding can't be negative so we use coerceAtLeast
                //otherwise app crashes
                .padding(bottom = extrapadding.coerceAtLeast(0.dp))
                ) {
                
                Text(text = "Hii, ")
                //"style "is used to style the text
                Text(text = name, style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.ExtraBold
                ))
                if(expanded){
                    //Text added to the the box when it opened.
                    Text(
                        text = ("Composem ipsum color sit lazy, " +
                                "padding theme elit, sed do bouncy. ").repeat(4),
                    )
                }


               
            }
            //Removed Elelvated button and used Icon button
            IconButton(
                onClick = { expanded = !expanded }
            ) {
                Icon(
                    imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                    contentDescription = if (expanded) {
                        stringResource(R.string.show_less)
                    } else {
                        stringResource(R.string.show_more)
                    }
                )
            }
            
        }
        //Use Column modifier to write text vertically line by line



    }

}

@Composable
fun MyApp(modifier: Modifier = Modifier) {
    var shouldShowOnboarding by remember { mutableStateOf(true) }

    Surface(modifier) {
        if (shouldShowOnboarding) {
            OnboardingScreen(onContinueClicked = { shouldShowOnboarding = false })
        } else {
            Greetings()
        }
    }
}


@Preview(showBackground = true, widthDp = 320)
@Composable
fun GreetingsPreview() {
    BasicsCodelabTheme {
        Greetings()
    }
}
@Preview
@Composable
private fun Greetings(
    modifier: Modifier = Modifier,
    //1000 boxes.
    names: List<String> = List(1000){"$it"}
){
    // provide scrolling.
    LazyColumn(modifier = modifier.padding(vertical = 4.dp)) {
        items(items = names) { name ->
            Greeting(name = name)
        }
    }

}
//want to change colors GO TO
//Theme.kt
//Color.kt
//and change
@Preview(
    showBackground = true,
    widthDp = 320,
    uiMode = UI_MODE_NIGHT_YES,
    name = "DarkMode"
)


//@Preview
@Composable
fun MyAppPreview() {
    BasicsCodelabTheme {
        MyApp(Modifier.fillMaxSize())
    }
}