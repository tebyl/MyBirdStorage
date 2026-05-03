package com.tebyl.aviariolocal.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.AutoStories
import androidx.compose.material.icons.outlined.GridView
import androidx.compose.material.icons.outlined.Map
import androidx.compose.material.icons.automirrored.outlined.ShowChart
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.tebyl.aviariolocal.ui.screens.AddScreen
import com.tebyl.aviariolocal.ui.screens.DetailScreen
import com.tebyl.aviariolocal.ui.screens.GalleryScreen
import com.tebyl.aviariolocal.ui.screens.MapScreen
import com.tebyl.aviariolocal.ui.screens.ProfileScreen
import com.tebyl.aviariolocal.ui.screens.StatsScreen
import com.tebyl.aviariolocal.ui.theme.BgWarm
import com.tebyl.aviariolocal.ui.theme.InkMute
import com.tebyl.aviariolocal.ui.theme.Line
import com.tebyl.aviariolocal.ui.theme.Moss100
import com.tebyl.aviariolocal.ui.theme.Moss700
import com.tebyl.aviariolocal.viewmodel.BirdViewModel

private data class NavItem(val route: String, val label: String, val icon: ImageVector)

@Composable
fun AviarioNav(vm: BirdViewModel) {
    val nav = rememberNavController()
    val backStack by nav.currentBackStackEntryAsState()
    val current = backStack?.destination?.route

    val hideBar = current?.startsWith("detail") == true || current == "add"

    val items = listOf(
        NavItem("gallery", "Galería",  Icons.Outlined.GridView),
        NavItem("map",     "Mapa",     Icons.Outlined.Map),
        NavItem("add",     "Añadir",   Icons.Outlined.Add),
        NavItem("stats",   "Cifras",   Icons.AutoMirrored.Outlined.ShowChart),
        NavItem("profile", "Cuaderno", Icons.Outlined.AutoStories)
    )

    Scaffold(
        bottomBar = {
            if (!hideBar) AviarioBottomBar(items, current, nav)
        }
    ) { padding ->
        NavHost(
            navController = nav,
            startDestination = "gallery",
            modifier = Modifier.padding(padding),
            enterTransition = { fadeIn(tween(180)) },
            exitTransition  = { fadeOut(tween(180)) }
        ) {
            composable("gallery") {
                GalleryScreen(vm = vm, onOpenBird = { nav.navigate("detail/$it") })
            }
            composable("map") {
                MapScreen(vm = vm, onOpenBird = { nav.navigate("detail/$it") })
            }
            composable("add") {
                AddScreen(vm = vm, onClose = { nav.popBackStack() })
            }
            composable("stats") {
                StatsScreen(vm = vm)
            }
            composable("profile") {
                ProfileScreen(vm = vm)
            }
            composable(
                route = "detail/{id}",
                arguments = listOf(navArgument("id") { type = NavType.LongType })
            ) { entry ->
                DetailScreen(
                    id = entry.arguments!!.getLong("id"),
                    vm = vm,
                    onBack = { nav.popBackStack() }
                )
            }
        }
    }
}

@Composable
private fun AviarioBottomBar(
    items: List<NavItem>,
    current: String?,
    nav: NavHostController
) {
    NavigationBar(
        containerColor = BgWarm.copy(alpha = 0.96f),
        tonalElevation = 0.dp
    ) {
        items.forEach { item ->
            val isAdd = item.route == "add"
            NavigationBarItem(
                selected = current == item.route,
                onClick = {
                    nav.navigate(item.route) {
                        popUpTo(nav.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = !isAdd
                    }
                },
                icon = {
                    if (isAdd) {
                        Surface(
                            shape = CircleShape,
                            color = Moss700,
                            modifier = Modifier.size(42.dp)
                        ) {
                            Icon(
                                item.icon,
                                contentDescription = item.label,
                                tint = BgWarm,
                                modifier = Modifier
                                    .padding(10.dp)
                                    .size(22.dp)
                            )
                        }
                    } else {
                        Icon(item.icon, contentDescription = item.label)
                    }
                },
                label = { Text(item.label, fontSize = 9.5.sp) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor   = Moss700,
                    selectedTextColor   = Moss700,
                    indicatorColor      = Moss100.copy(alpha = 0.6f),
                    unselectedIconColor = InkMute,
                    unselectedTextColor = InkMute
                )
            )
        }
    }
}
