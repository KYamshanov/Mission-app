package ru.kyamshanov.mission.core.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.IntSize
import ru.kyamshanov.mission.MissionTheme

@Composable
fun EditTextField(
    modifier: Modifier = Modifier,
    text: String,
    label: String?,
    editable: Boolean = false,
    maxLines: Int = 1,
    rightIcon: (@Composable () -> Unit)? = null,
    underlined: Boolean = true,
    textStyle: TextStyle = MissionTheme.typography.inputText,
    suffix: String? = null,
    isMasked: Boolean = false,
    onValueChange: (String) -> Unit,
) {
    MissionTextField(
        modifier = modifier,
        text = text,
        label = label?.let { { Text(text = it, style = MissionTheme.typography.inputHint) } },
        editable = editable,
        maxLines = maxLines,
        rightIcon = rightIcon,
        underlined = underlined,
        textStyle = textStyle,
        suffix = suffix?.let { AnnotatedString(it) },
        onValueChange = onValueChange,
        isMasked = isMasked,
    )
}

@Composable
fun MissionTextField(
    modifier: Modifier = Modifier,
    text: String,
    label: (@Composable () -> Unit)? = null,
    editable: Boolean = false,
    maxLines: Int = 1,
    rightIcon: (@Composable () -> Unit)? = null,
    underlined: Boolean = true,
    textStyle: TextStyle = MissionTheme.typography.inputText,
    suffix: AnnotatedString? = null,
    isMasked: Boolean = false,
    onValueChange: (String) -> Unit,
) {
    val localDensity = LocalDensity.current

    BasicTextField(
        modifier = modifier,
        textStyle = textStyle,
        value = text,
        onValueChange = onValueChange,
        singleLine = maxLines == 1,
        maxLines = maxLines,
        readOnly = editable.not(),
        cursorBrush = SolidColor(MissionTheme.colors.darkSecondary),
        keyboardOptions = if (isMasked) KeyboardOptions(keyboardType = KeyboardType.Password, autoCorrect = false) else KeyboardOptions.Default,
        visualTransformation = if (suffix != null || isMasked) {
            val s = suffix ?: AnnotatedString("")
            VisualTransformation { annotatedString ->
                val textWithSuffixMapping = object : OffsetMapping {
                    override fun originalToTransformed(offset: Int): Int {
                        return offset
                    }

                    override fun transformedToOriginal(offset: Int): Int {
                        if (text.isEmpty()) return 0
                        if (offset >= text.length) return text.length
                        return offset
                    }
                }
                TransformedText(
                    if (isMasked) AnnotatedString("*".repeat(annotatedString.length)) else annotatedString.plus(s),
                    textWithSuffixMapping
                )
            }
        } else VisualTransformation.None,
        decorationBox = { innerTextField ->
            BoxWithConstraints {
                val iconSizeState = remember { mutableStateOf(IntSize.Zero) }

                Column(
                    modifier = Modifier.align(Alignment.TopStart)
                        .width(this.maxWidth - with(localDensity) { iconSizeState.value.width.toDp() })
                ) {
                    label?.invoke()
                    innerTextField()
                }
                rightIcon?.let { icon ->
                    Box(
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .wrapContentSize()
                            .onSizeChanged { iconSizeState.value = it },
                        contentAlignment = Alignment.Center,
                        content = { icon() },
                    )
                }
                if (underlined) CellLine(modifier = Modifier.align(Alignment.BottomStart))
            }
        },
    )
}