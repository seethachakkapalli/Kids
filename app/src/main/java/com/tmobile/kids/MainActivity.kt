package com.tmobile.kids

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.dynamiclinks.ShortDynamicLink
import com.tmobile.kids.ui.theme.DeepLinkPOCTheme
import com.tmobile.kids.utils.DeeplinkUtils

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DeepLinkPOCTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CenteredButtonAndText()
                }
            }
        }
    }
}

@Composable
fun CenteredButtonAndText() {
    var deeplink: String = ""

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = {
                DeeplinkUtils.createFirebaseDynamicLink(86778687, 657654)
                    .addOnSuccessListener { p0 ->
                        if (p0 != null) {
                            deeplink = p0.shortLink.toString()
                        }
                    }
            }
        ) {
            Text(text = "Generate Deeplink")
        }

        Text(
            text = deeplink,
            modifier = Modifier.padding(top = 8.dp),

            color = Color.Black,
            textAlign = TextAlign.Center
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CenteredButtonAndTextPreview() {
    DeepLinkPOCTheme {
        CenteredButtonAndText()
    }
}
