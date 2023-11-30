package com.tmobile.kids.utils

import android.net.Uri
import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.dynamiclinks.DynamicLink
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks
import com.google.firebase.dynamiclinks.ShortDynamicLink

class DeeplinkUtils {
    companion object {
        fun createFirebaseDynamicLink(inviteId: Int, groupId: Int): Task<ShortDynamicLink> {
            val deepLink = Uri.parse("https://flywheel.com/connect/group/invite?iid=$inviteId&gid=$groupId")

            val dynamicLink = FirebaseDynamicLinks.getInstance().createDynamicLink()
                .setLink(deepLink)
                .setDomainUriPrefix("https://kidswatch.page.link") // Replace with your actual domain
                .setAndroidParameters(
                    DynamicLink.AndroidParameters.Builder()
                        .setFallbackUrl(Uri.parse("https://play.google.com/store/apps/details?id=com.tmobile.familycontrols"))
                        .build()
                )

            return dynamicLink.buildShortDynamicLink()
                .addOnSuccessListener { shortDynamicLink ->
                    val shortLink = shortDynamicLink.shortLink
                    val previewLink = shortDynamicLink.previewLink
                    Log.i("shortLink", shortLink.toString())
                    Log.i("shortPreviewLink", previewLink.toString())
                }
                .addOnFailureListener { e ->
                    e.printStackTrace()
                }
        }
    }
}
