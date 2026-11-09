package com.example.opencity.app_navigation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.graphics.vector.ImageVector
import kotlin.collections.remove


// had to remove 'sealed'
interface TopLevelRoute {
    val icon: ImageVector
    val labelResourceId: Int
}

interface SubLevelRoute {
    val returnableTo: Boolean
}


class NavigationStack<T: Any>(startKey: T) {
    // Maintain a stack for each top level route
    private var topLevelStacks : LinkedHashMap<T, SnapshotStateList<T>> = linkedMapOf(startKey to mutableStateListOf(startKey))

    // Expose the current top level route for consumers
    var topLevelKey by mutableStateOf(startKey)
        private set

    // Expose the back stack so it can be rendered by the NavDisplay
    val backStack = mutableStateListOf(startKey)

    private fun updateBackStack() = backStack.apply { clear(); addAll(topLevelStacks.flatMap { it.value }) }

    private fun pruneNonReturnableCurrentTopLevel() {
        topLevelStacks[topLevelKey]?.removeAll { route -> route is SubLevelRoute && !route.returnableTo }
    }

    fun addTopLevel(key: T){
        pruneNonReturnableCurrentTopLevel()
        // If the top level doesn't exist, add it
        if (topLevelStacks[key] == null){ topLevelStacks[key] = mutableStateListOf(key) }
        // Otherwise just move it to the end of the stacks
        else { topLevelStacks.apply { remove(key)?.let { put(key, it) } } }
        topLevelKey = key
        pruneNonReturnableCurrentTopLevel()
        updateBackStack()
    }

    fun add(key: T){
        pruneNonReturnableCurrentTopLevel()
        topLevelStacks[topLevelKey]?.add(key)
        updateBackStack()
    }

    fun removeLast(){
        val removedKey = topLevelStacks[topLevelKey]?.removeLastOrNull()
        // If the removed key was a top level key, remove the associated top level stack
        topLevelStacks.remove(removedKey)
        topLevelKey = topLevelStacks.keys.last()
        updateBackStack()
    }
}
