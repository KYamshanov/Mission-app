package ru.kyamshanov.mission.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import ru.kyamshanov.mission.MissionTheme

@Composable
fun ComplexCell(
    modifier: Modifier = Modifier,
    content: CellScope.() -> Unit,
) = Cell(
    modifier = modifier,
    autoPaddings = false
) {
    val cellScope = CellScopeImpl(this)
    content.invoke(cellScope)
    val items = cellScope.items
    items.forEachIndexed { index: Int, item ->
        Box(modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .run {
                if (item.autoPaddings) {
                    padding(horizontal = 5.dp)
                        .run { if (index != 0) padding(top = 1.dp) else this }
                        .run { if (index != items.lastIndex) padding(bottom = 1.dp) else this }
                } else this
            }
        ) {
            item.content.invoke(this)
        }
        if (item.divided && index != items.lastIndex) CellLine()
    }
}

@Composable
fun Cell(
    modifier: Modifier = Modifier,
    autoPaddings: Boolean = true,
    content: @Composable ColumnScope.() -> Unit,
) = Column(
    modifier = modifier
        .clip(MissionTheme.shapes.medium)
        .background(MissionTheme.colors.primary)
        .border(
            color = MissionTheme.colors.darkSecondary,
            width = 2.dp,
            shape = MissionTheme.shapes.medium
        )
        .padding(vertical = 5.dp)
        .run { if (autoPaddings) padding(horizontal = 5.dp) else this },
    content = content,
)

interface CellScope : ColumnScope {

    fun item(
        divided: Boolean = true,
        autoPaddings: Boolean = true,
        content: @Composable BoxScope.() -> Unit,
    )
}

private class CellScopeImpl(columnScope: ColumnScope) : CellScope, ColumnScope by columnScope {

    private val _items = mutableListOf<Item>()
    val items: List<Item>
        get() = _items

    override fun item(divided: Boolean, autoPaddings: Boolean, content: @Composable BoxScope.() -> Unit) {
        _items.add(Item(divided = divided, autoPaddings = autoPaddings, content = content))
    }

    data class Item(
        val divided: Boolean,
        val autoPaddings: Boolean,
        val content: @Composable BoxScope.() -> Unit,
    )
}
