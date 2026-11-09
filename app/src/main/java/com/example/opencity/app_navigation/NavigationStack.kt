package com.example.opencity.app_navigation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.graphics.vector.ImageVector


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

    fun removeLast(): Boolean {
        // get the current L1 stack, if it does not exist, return false
        val currentStack = topLevelStacks[topLevelKey] ?: return false
        // If L1 is more than 1(root), simply remove sub
        if (currentStack.size > 1) {
            currentStack.removeAt(currentStack.lastIndex)
            updateBackStack()
            return true
        }
        // Else, if L1 had only one, confirm if L0 is more than 1, if it is, go back to the last toplevel
        if (topLevelStacks.size > 1) {
            topLevelStacks.remove(topLevelKey)
            topLevelKey = topLevelStacks.keys.last()
            updateBackStack()
            return true
        }
        return false  // Already at the root of the last top-level stack.
    }
}
