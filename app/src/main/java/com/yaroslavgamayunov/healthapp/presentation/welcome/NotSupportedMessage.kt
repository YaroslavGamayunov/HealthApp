package com.yaroslavgamayunov.healthapp.presentation.welcome

import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import com.yaroslavgamayunov.healthapp.R
import com.yaroslavgamayunov.healthapp.data.MIN_SUPPORTED_SDK

/**
 * Welcome text shown when the app first starts, where the device is not running a sufficient
 * Android version for Health Connect to be used.
 */
@Composable
fun NotSupportedMessage() {
    val tag = stringResource(R.string.not_supported_tag)
    val url = stringResource(R.string.not_supported_url)
    val handler = LocalUriHandler.current

    val notSupportedText = stringResource(
        id = R.string.not_supported_description,
        MIN_SUPPORTED_SDK
    )
    val notSupportedLinkText = stringResource(R.string.not_supported_link_text)

    val unavailableText = buildAnnotatedString {
        withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.onBackground)) {
            append(notSupportedText)
            append("\n\n")
        }
        pushStringAnnotation(tag = tag, annotation = url)
        withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)) {
            append(notSupportedLinkText)
        }
    }
    ClickableText(
        text = unavailableText,
        style = TextStyle(textAlign = TextAlign.Justify)
    ) { offset ->
        unavailableText.getStringAnnotations(tag = tag, start = offset, end = offset)
            .firstOrNull()?.let {
                handler.openUri(it.item)
            }
    }
}
