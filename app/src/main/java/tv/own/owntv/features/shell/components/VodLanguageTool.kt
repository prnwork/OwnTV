package tv.own.owntv.features.shell.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import tv.own.owntv.R
import tv.own.owntv.ui.components.OwnTVIcon
import tv.own.owntv.ui.stage.GuideMenuHost
import tv.own.owntv.ui.stage.StageMenuHeader
import tv.own.owntv.ui.stage.StageMenuItem
import tv.own.owntv.ui.stage.StageTool
import tv.own.owntv.ui.theme.mpx

/**
 * Dropdown trigger button for Language filtering, styled identically to [VodSortTool].
 */
@Composable
internal fun VodLanguageTool(
    selectedLanguage: String?,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    val anyLabel = stringResource(R.string.sports_any)
    val displayValue = selectedLanguage ?: anyLabel
    val title = stringResource(R.string.settings_language)
    StageTool(
        text = title,
        icon = OwnTVIcon.LANGUAGE,
        value = displayValue,
        trailingIcon = OwnTVIcon.CHEVRON_DOWN,
        onClick = onClick,
        modifier = modifier,
    )
}

/**
 * Android TV popup menu for Language selection, styled like [VodSortMenu].
 */
@Composable
internal fun VodLanguageMenu(
    languages: List<String>,
    current: String?,
    x: Float,
    top: Dp,
    onPick: (String?) -> Unit,
    onDismiss: () -> Unit,
) {
    val focus = remember { FocusRequester() }
    LaunchedEffect(Unit) { runCatching { focus.requestFocus() } }
    GuideMenuHost(x = x, top = top, width = 560.mpx, onDismiss = onDismiss) {
        StageMenuHeader(
            title = stringResource(R.string.settings_language),
            subtitle = stringResource(R.string.content_sort_applies_hint),
        )
        val allSelected = current == null
        StageMenuItem(
            text = stringResource(R.string.sports_any),
            icon = OwnTVIcon.LANGUAGE,
            checked = allSelected,
            onClick = { onPick(null); onDismiss() },
            modifier = if (allSelected) Modifier.focusRequester(focus) else Modifier,
        )
        languages.forEach { lang ->
            val on = lang.equals(current, ignoreCase = true)
            StageMenuItem(
                text = lang,
                icon = OwnTVIcon.LANGUAGE,
                checked = on,
                onClick = { onPick(lang); onDismiss() },
                modifier = if (on) Modifier.focusRequester(focus) else Modifier,
            )
        }
    }
}
