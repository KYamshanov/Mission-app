package ru.kyamshanov.mission.core.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun Surface(
    modifier: Modifier = Modifier,
    verticalScroll: Boolean = true,
    topContent: @Composable (() -> Unit)? = null,
    bottomContent: @Composable (() -> Unit)? = null,
    content: @Composable (ColumnScope.() -> Unit),
) {

    /*   SubcomposeLayout(
           modifier = modifier
       ) { constraints ->
           val layoutWidth = constraints.maxWidth
           val layoutHeight = constraints.maxHeight

           val looseConstraints = constraints.copy(minWidth = 0, minHeight = 0)

           layout(layoutWidth, layoutHeight) {
               val topContentPlaceables = if (topContent != null) {
                   subcompose(SurfaceLayoutContent.TOP_CONTENT, topContent).fastMap {
                       it.measure(looseConstraints)
                   }
               } else null

               val topContentHeight = topContentPlaceables?.fastMaxBy { it.height }?.height ?: 0

               val bottomContentPlaceables = if (bottomContent != null) {
                   subcompose(SurfaceLayoutContent.BOTTOM_CONTENT, bottomContent).fastMap {
                       it.measure(looseConstraints)
                   }
               } else null

               val bottomContentHeight = bottomContentPlaceables?.fastMaxBy { it.height }?.height ?: 0

               val bodyContentMaxHeight = layoutHeight - topContentHeight

               val bodyContentPlaceables = subcompose(SurfaceLayoutContent.CONTENT) {
                   //val innerPadding = PaddingValues(bottom = bottomBarHeight.toDp())
                   content.invoke()
               }.fastMap { it.measure(looseConstraints) }

               val bodyContentHeight = bodyContentPlaceables.fastMaxBy { it.height }?.height ?: 0

               bodyContentPlaceables.fastForEach {
                   it.place(0, topContentHeight)
               }
               topContentPlaceables?.fastForEach {
                   it.place(0, 0)
               }

               // The bottom bar is always at the bottom of the layout
               if (topContentHeight + bodyContentHeight + bottomContentHeight < layoutHeight) {
                   bottomContentPlaceables?.fastForEach {
                       it.place(0, topContentHeight + bodyContentHeight)
                   }
               }

           }
           private enum class SurfaceLayoutContent {
       TOP_CONTENT,
       CONTENT,
       BOTTOM_CONTENT
   }
       }*/
    Column {
        topContent?.invoke()
        Column(
            modifier = modifier
                .fillMaxHeight()
                .run { if (verticalScroll) verticalScroll(rememberScrollState()) else this }
                .padding(16.dp)
        ) {
            content()
            Spacer(modifier = Modifier.height(15.dp))
            Spacer(modifier = Modifier.weight(1f))
            bottomContent?.invoke()
        }
    }
}