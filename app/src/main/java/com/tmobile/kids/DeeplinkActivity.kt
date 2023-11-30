package com.tmobile.kids

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks
import com.tmobile.kids.ui.theme.DeepLinkPOCTheme

class DeeplinkActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        handleIntent(intent)

        setContent {
            DeepLinkPOCTheme {
                DisplayGidAndIid()
            }
        }
    }

    private fun handleIntent(intent: Intent) {
        FirebaseDynamicLinks.getInstance()
            .getDynamicLink(intent)
            .addOnSuccessListener(this) { pendingDynamicLinkData ->
                val deepLink: Uri? = pendingDynamicLinkData?.link

                deepLink?.let { link ->
                    val inviteId = link.getQueryParameter("iid")
                    val groupId = link.getQueryParameter("gid")

                    Log.i("gid and iid", inviteId + "  ,,,,, " + groupId)
                    AppViewModel.inviteId = inviteId ?: ""
                    AppViewModel.groupId = groupId ?: ""
                }
            }
            .addOnFailureListener(this) { e ->
                Log.i("DeeplinkActivity", "getDynamicLink:onFailure", e)
            }
    }
}

@Composable
fun DisplayGidAndIid() {
    val inviteId = AppViewModel.inviteId
    val groupId = AppViewModel.groupId

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "inviteId = $inviteId")
        Text(text = "groupId = $groupId")
    }
}

@Preview(showBackground = true)
@Composable
fun DisplayGidAndIidPreview() {
    DeepLinkPOCTheme {
        DisplayGidAndIid()
    }
}

object AppViewModel {
    var inviteId by mutableStateOf("")
    var groupId by mutableStateOf("")
}
