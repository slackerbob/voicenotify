/*
 * Copyright 2011-2023 Mark Injerd
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.pilot51.voicenotify

import android.content.Intent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun TtsConfigScreen() {
	val context = LocalContext.current
	var ttsEnabled by remember { mutableStateOf(true) }
	var ttsSummary by remember { mutableStateOf("") }
	var ttsIntent by remember { mutableStateOf<Intent?>(null) }
	Intent("com.android.settings.TTS_SETTINGS").let {
		if (it.resolveActivity(context.packageManager) != null) {
			ttsIntent = it
			ttsSummary = stringResource(R.string.tts_settings_summary)
		} else {
			ttsEnabled = false
			ttsSummary = stringResource(R.string.tts_settings_summary_fail)
		}
	}
	var showTtsMessage by remember { mutableStateOf(false) }
	var showTextReplaceDialog by remember { mutableStateOf(false) }
	var showMaxMessage by remember { mutableStateOf(false) }
	var showTtsStream by remember { mutableStateOf(false) }
	var showTtsDelay by remember { mutableStateOf(false) }
	var showTtsRepeat by remember { mutableStateOf(false) }
	MaterialTheme(colorScheme = darkColorScheme()) {
		Column(modifier = Modifier.fillMaxSize()) {
			PreferenceRowLink(
				enabled = ttsEnabled,
				title = stringResource(R.string.tts_settings),
				subtitle = ttsSummary,
				onClick = { ttsIntent?.let { context.startActivity(it) } }
			)
			PreferenceRowLink(
				title = R.string.tts_message,
				subtitle = R.string.tts_message_summary,
				onClick = { showTtsMessage = true }
			)
			PreferenceRowLink(
				title = R.string.tts_text_replace,
				subtitle = R.string.tts_text_replace_summary,
				onClick = { showTextReplaceDialog = true }
			)
			PreferenceRowLink(
				title = R.string.max_length,
				subtitle = R.string.max_length_summary,
				onClick = { showMaxMessage = true }
			)
			PreferenceRowLink(
				title = R.string.tts_stream,
				subtitle = R.string.tts_stream_summary,
				onClick = { showTtsStream = true }
			)
			PreferenceRowLink(
				title = R.string.tts_delay,
				subtitle = R.string.tts_delay_summary,
				onClick = { showTtsDelay = true }
			)
			PreferenceRowLink(
				title = R.string.tts_repeat,
				subtitle = R.string.tts_repeat_summary,
				onClick = { showTtsRepeat = true }
			)
		}
		if (showTtsMessage) {
			TtsMessageDialog { showTtsMessage = false }
		}
		if (showTextReplaceDialog) {
			TextReplaceDialog { showTextReplaceDialog = false }
		}
		if (showMaxMessage) {
			TtsMaxLengthDialog { showMaxMessage = false }
		}
		if (showTtsStream) {
			TtsStreamDialog { showTtsStream = false }
		}
		if (showTtsDelay) {
			TtsDelayDialog { showTtsDelay = false }
		}
		if (showTtsRepeat) {
			TtsRepeatDialog { showTtsRepeat = false }
		}
	}
}

@Preview
@Composable
private fun TtsConfigScreenPreview() {
	TtsConfigScreen()
}
