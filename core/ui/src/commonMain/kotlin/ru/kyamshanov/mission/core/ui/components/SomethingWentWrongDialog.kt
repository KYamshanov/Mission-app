package ru.kyamshanov.mission.core.ui.components

import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier

@Composable
fun SomethingWentWrongDialog(
    modifier: Modifier = Modifier,
    visibleState: MutableState<Boolean>,
    onConfirm: () -> Unit,
) {
    if (visibleState.value)
        SomethingWentWrongDialog(
            modifier = modifier,
            onDismissRequest = { visibleState.value = false },
            onConfirm = {
                visibleState.value = false
                onConfirm.invoke()
            }
        )
}

@Composable
fun SomethingWentWrongDialog(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,
) = SomethingWentWrongDialog(
    onDismissRequest = onDismissRequest,
    onConfirm = onDismissRequest
)

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SomethingWentWrongDialog(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,
    onConfirm: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        text = { Text("Что-то пошло не так") },
        modifier = modifier,
        confirmButton = {
            Button(onClick = onConfirm) { Text(text = "По**й") }
        }
    )
}