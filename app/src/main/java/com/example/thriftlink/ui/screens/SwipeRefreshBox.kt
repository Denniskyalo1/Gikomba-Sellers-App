package com.example.thriftlink.ui.components

import android.widget.FrameLayout
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.platform.ComposeView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

@Composable
fun SwipeRefreshBox(
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    AndroidView(
        modifier = modifier,
        factory = { context ->
            SwipeRefreshLayout(context).apply {
                val composeView = ComposeView(context)
                addView(composeView)

                // Link swipe gesture to your refresh callback
                setOnRefreshListener { onRefresh() }

                // Attach Compose content inside the view
                composeView.setContent {
                    content()
                }
            }
        },
        update = { swipeRefresh ->
            swipeRefresh.isRefreshing = isRefreshing
        }
    )
}
