package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Casino
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.UserPreferences
import com.example.ui.components.AppTopBar
import com.example.ui.screens.AiDietCoachScreen
import com.example.ui.screens.CalorieHomeScreen
import com.example.ui.screens.FoodDiaryScreen
import com.example.ui.screens.LuckyWheelScreen
import com.example.ui.screens.PublishGuideScreen
import com.example.ui.screens.StoreScreen
import com.example.ui.theme.CalorieYarTheme
import com.example.ui.theme.DarkBackgroundFitness
import com.example.ui.theme.DarkSurfaceForest
import com.example.ui.theme.FreshLime
import com.example.ui.theme.MintGreenPrimary
import com.example.ui.theme.TextMuted

sealed class Screen(val title: String) {
    object Home : Screen("داشبورد")
    object FoodDiary : Screen("کالری‌شمار")
    object AiCoach : Screen("مربی AI")
    object LuckyWheel : Screen("گردونه شانس")
    object Store : Screen("فروشگاه سکه و VIP")
    object PublishGuide : Screen("راهنمای انتشار بازار")
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CalorieYarTheme(darkTheme = true) {
                MainApp()
            }
        }
    }
}

@Composable
fun MainApp() {
    val context = LocalContext.current
    val userPrefs = remember { UserPreferences(context) }
    val coins by userPrefs.coinsFlow.collectAsState()
    val isVip by userPrefs.isVipFlow.collectAsState()

    var currentScreen by remember { mutableStateOf<Screen>(Screen.Home) }
    val backstack = remember { mutableStateListOf<Screen>() }

    fun navigateTo(screen: Screen) {
        if (currentScreen != screen) {
            backstack.add(currentScreen)
            currentScreen = screen
        }
    }

    BackHandler(enabled = backstack.isNotEmpty() || currentScreen != Screen.Home) {
        if (backstack.isNotEmpty()) {
            currentScreen = backstack.removeLast()
        } else {
            currentScreen = Screen.Home
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = DarkBackgroundFitness,
        topBar = {
            AppTopBar(
                coins = coins,
                isVip = isVip,
                onStoreClick = { navigateTo(Screen.Store) },
                onGuideClick = { navigateTo(Screen.PublishGuide) }
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = DarkSurfaceForest,
                contentColor = FreshLime,
                tonalElevation = 8.dp
            ) {
                val isHome = currentScreen is Screen.Home
                val isDiary = currentScreen is Screen.FoodDiary
                val isAi = currentScreen is Screen.AiCoach
                val isWheel = currentScreen is Screen.LuckyWheel
                val isStore = currentScreen is Screen.Store

                NavigationBarItem(
                    selected = isHome,
                    onClick = { navigateTo(Screen.Home) },
                    icon = { Icon(Icons.Default.Home, contentDescription = "داشبورد") },
                    label = { Text("داشبورد", fontSize = 11.sp, fontWeight = if (isHome) FontWeight.Bold else FontWeight.Normal) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color(0xFF042F1A),
                        selectedTextColor = FreshLime,
                        indicatorColor = FreshLime,
                        unselectedIconColor = TextMuted,
                        unselectedTextColor = TextMuted
                    ),
                    modifier = Modifier.testTag("nav_home")
                )

                NavigationBarItem(
                    selected = isDiary,
                    onClick = { navigateTo(Screen.FoodDiary) },
                    icon = { Icon(Icons.Default.Restaurant, contentDescription = "کالری‌شمار") },
                    label = { Text("کالری‌شمار", fontSize = 11.sp, fontWeight = if (isDiary) FontWeight.Bold else FontWeight.Normal) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color(0xFF042F1A),
                        selectedTextColor = FreshLime,
                        indicatorColor = FreshLime,
                        unselectedIconColor = TextMuted,
                        unselectedTextColor = TextMuted
                    ),
                    modifier = Modifier.testTag("nav_diary")
                )

                NavigationBarItem(
                    selected = isAi,
                    onClick = { navigateTo(Screen.AiCoach) },
                    icon = { Icon(Icons.Default.Psychology, contentDescription = "مربی هوش مصنوعی") },
                    label = { Text("مربی AI", fontSize = 11.sp, fontWeight = if (isAi) FontWeight.Bold else FontWeight.Normal) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color(0xFF042F1A),
                        selectedTextColor = FreshLime,
                        indicatorColor = FreshLime,
                        unselectedIconColor = TextMuted,
                        unselectedTextColor = TextMuted
                    ),
                    modifier = Modifier.testTag("nav_ai")
                )

                NavigationBarItem(
                    selected = isWheel,
                    onClick = { navigateTo(Screen.LuckyWheel) },
                    icon = { Icon(Icons.Default.Casino, contentDescription = "گردونه") },
                    label = { Text("گردونه", fontSize = 11.sp, fontWeight = if (isWheel) FontWeight.Bold else FontWeight.Normal) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color(0xFF042F1A),
                        selectedTextColor = FreshLime,
                        indicatorColor = FreshLime,
                        unselectedIconColor = TextMuted,
                        unselectedTextColor = TextMuted
                    ),
                    modifier = Modifier.testTag("nav_wheel")
                )

                NavigationBarItem(
                    selected = isStore,
                    onClick = { navigateTo(Screen.Store) },
                    icon = { Icon(Icons.Default.ShoppingBag, contentDescription = "فروشگاه") },
                    label = { Text("فروشگاه", fontSize = 11.sp, fontWeight = if (isStore) FontWeight.Bold else FontWeight.Normal) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color(0xFF042F1A),
                        selectedTextColor = FreshLime,
                        indicatorColor = FreshLime,
                        unselectedIconColor = TextMuted,
                        unselectedTextColor = TextMuted
                    ),
                    modifier = Modifier.testTag("nav_store")
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (currentScreen) {
                is Screen.Home -> CalorieHomeScreen(
                    userPrefs = userPrefs,
                    onNavigateToDiary = { navigateTo(Screen.FoodDiary) },
                    onNavigateToAiCoach = { navigateTo(Screen.AiCoach) },
                    onNavigateToWheel = { navigateTo(Screen.LuckyWheel) },
                    onNavigateToStore = { navigateTo(Screen.Store) }
                )
                is Screen.FoodDiary -> FoodDiaryScreen(userPrefs = userPrefs)
                is Screen.AiCoach -> AiDietCoachScreen(
                    userPrefs = userPrefs,
                    onNavigateToStore = { navigateTo(Screen.Store) }
                )
                is Screen.LuckyWheel -> LuckyWheelScreen(userPrefs = userPrefs)
                is Screen.Store -> StoreScreen(
                    userPrefs = userPrefs,
                    onNavigateToWheel = { navigateTo(Screen.LuckyWheel) }
                )
                is Screen.PublishGuide -> PublishGuideScreen()
            }
        }
    }
}
