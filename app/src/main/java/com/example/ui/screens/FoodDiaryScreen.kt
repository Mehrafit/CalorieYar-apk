package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.DirectionsWalk
import androidx.compose.material.icons.filled.ListAlt
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.FoodRepository
import com.example.data.UserPreferences
import com.example.model.IranianFoodItem
import com.example.model.LoggedMeal
import com.example.model.MealType
import com.example.ui.theme.AccentOrange
import com.example.ui.theme.CalorieBurnRed
import com.example.ui.theme.DarkSurfaceCard
import com.example.ui.theme.DarkSurfaceForest
import com.example.ui.theme.FreshLime
import com.example.ui.theme.MintGreenPrimary
import com.example.ui.theme.SecondaryGold
import com.example.ui.theme.TextGold
import com.example.ui.theme.TextLightGreen
import com.example.ui.theme.TextMuted
import com.example.util.PersianUtils.toPersian

@Composable
fun FoodDiaryScreen(
    userPrefs: UserPreferences,
    modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableIntStateOf(0) } // 0: Search & Add, 1: Today's Log
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("همه") }

    var selectedFoodForLogging by remember { mutableStateOf<IranianFoodItem?>(null) }
    var chosenMealType by remember { mutableStateOf(MealType.LUNCH) }
    var chosenPortionMultiplier by remember { mutableStateOf(1.0f) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "کالری‌شمار سفره ایرانی",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = TextLightGreen,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Tabs
        TabRow(
            selectedTabIndex = selectedTab,
            containerColor = DarkSurfaceForest,
            contentColor = FreshLime,
            indicator = { tabPositions ->
                TabRowDefaults.SecondaryIndicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                    color = FreshLime
                )
            },
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .fillMaxWidth()
        ) {
            Tab(
                selected = selectedTab == 0,
                onClick = { selectedTab = 0 },
                text = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(Icons.Default.Search, contentDescription = null, modifier = Modifier.size(18.dp))
                        Text("جستجوی غذاها", fontWeight = if (selectedTab == 0) FontWeight.Bold else FontWeight.Normal)
                    }
                }
            )
            Tab(
                selected = selectedTab == 1,
                onClick = { selectedTab = 1 },
                text = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(Icons.Default.ListAlt, contentDescription = null, modifier = Modifier.size(18.dp))
                        Text("وعده‌های امروز (${userPrefs.todayMeals.size.toPersian()})", fontWeight = if (selectedTab == 1) FontWeight.Bold else FontWeight.Normal)
                    }
                }
            )
        }

        Spacer(modifier = Modifier.height(14.dp))

        if (selectedTab == 0) {
            // Search & Category Filter
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 90.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item {
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        placeholder = { Text("نام غذا (مثلا: قورمه‌سبزی، کباب، آش، سنگک...)", color = TextMuted) },
                        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = FreshLime) },
                        trailingIcon = {
                            if (searchQuery.isNotEmpty()) {
                                IconButton(onClick = { searchQuery = "" }) {
                                    Icon(Icons.Default.Clear, contentDescription = "پاک کردن", tint = TextMuted)
                                }
                            }
                        },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("food_search_input"),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = FreshLime,
                            unfocusedBorderColor = MintGreenPrimary.copy(alpha = 0.5f),
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White
                        )
                    )
                }

                item {
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        items(FoodRepository.categories) { cat ->
                            FilterChip(
                                selected = selectedCategory == cat,
                                onClick = { selectedCategory = cat },
                                label = { Text(cat) },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = FreshLime,
                                    selectedLabelColor = Color(0xFF042F1A),
                                    containerColor = DarkSurfaceForest,
                                    labelColor = Color.White
                                )
                            )
                        }
                    }
                }

                val results = FoodRepository.search(searchQuery, selectedCategory)
                items(results) { food ->
                    FoodItemCard(
                        food = food,
                        onAddClick = {
                            selectedFoodForLogging = food
                            chosenPortionMultiplier = 1.0f
                        }
                    )
                }
            }
        } else {
            // Today's Logged Meals
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 90.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                if (userPrefs.todayMeals.isEmpty()) {
                    item {
                        Card(
                            colors = CardDefaults.cardColors(containerColor = DarkSurfaceForest),
                            shape = RoundedCornerShape(14.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(24.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "هنوز غذایی برای امروز ثبت نکرده‌اید.",
                                    color = TextMuted,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Button(
                                    onClick = { selectedTab = 0 },
                                    colors = ButtonDefaults.buttonColors(containerColor = FreshLime)
                                ) {
                                    Text("ثبت اولین وعده", color = Color(0xFF042F1A), fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                    }
                } else {
                    items(userPrefs.todayMeals) { meal ->
                        LoggedMealCard(
                            meal = meal,
                            onDelete = { userPrefs.removeMeal(meal.id) }
                        )
                    }
                }
            }
        }
    }

    // Add to Meal Dialog
    if (selectedFoodForLogging != null) {
        val food = selectedFoodForLogging!!

        AlertDialog(
            onDismissRequest = { selectedFoodForLogging = null },
            containerColor = DarkSurfaceForest,
            title = {
                Text(
                    text = "ثبت وعده: ${food.name}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = TextLightGreen
                )
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text(
                        text = "واحد اندازه: ${food.portionUnit}",
                        style = MaterialTheme.typography.bodySmall,
                        color = TextMuted
                    )

                    Text(
                        text = "انتخاب وعده غذایی:",
                        style = MaterialTheme.typography.labelMedium,
                        color = Color.White
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        MealType.values().forEach { type ->
                            FilterChip(
                                selected = chosenMealType == type,
                                onClick = { chosenMealType = type },
                                label = { Text(type.title, fontSize = 11.sp) },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = FreshLime,
                                    selectedLabelColor = Color(0xFF042F1A),
                                    containerColor = DarkSurfaceCard,
                                    labelColor = Color.White
                                )
                            )
                        }
                    }

                    Text(
                        text = "مقدار مصرفی (سهم):",
                        style = MaterialTheme.typography.labelMedium,
                        color = Color.White
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        listOf(0.5f to "نصف", 1.0f to "۱ سهم", 1.5f to "۱.۵ سهم", 2.0f to "۲ سهم").forEach { (portion, label) ->
                            FilterChip(
                                selected = chosenPortionMultiplier == portion,
                                onClick = { chosenPortionMultiplier = portion },
                                label = { Text(label, fontSize = 11.sp) },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = SecondaryGold,
                                    selectedLabelColor = Color(0xFF1E1238),
                                    containerColor = DarkSurfaceCard,
                                    labelColor = Color.White
                                )
                            )
                        }
                    }

                    val totalCalculated = (food.caloriesPerPortion * chosenPortionMultiplier).toInt()
                    Text(
                        text = "کالری کل دریافتی: ${totalCalculated.toPersian()} کالری",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = FreshLime
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        userPrefs.logMeal(food, chosenMealType, chosenPortionMultiplier)
                        selectedFoodForLogging = null
                        selectedTab = 1 // Switch to log tab to see result!
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = FreshLime),
                    modifier = Modifier.testTag("confirm_log_food_button")
                ) {
                    Text("ثبت در برنامه امروز", color = Color(0xFF042F1A), fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                OutlinedButton(onClick = { selectedFoodForLogging = null }) {
                    Text("انصراف", color = TextMuted)
                }
            }
        )
    }
}

@Composable
fun FoodItemCard(
    food: IranianFoodItem,
    onAddClick: () -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = DarkSurfaceForest),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = food.name,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = food.portionUnit,
                        style = MaterialTheme.typography.bodySmall,
                        color = TextMuted
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "${food.caloriesPerPortion.toPersian()} کالری",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = TextGold
                    )

                    IconButton(
                        onClick = onAddClick,
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(FreshLime)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "افزودن",
                            tint = Color(0xFF042F1A)
                        )
                    }
                }
            }

            // Macros Row & Walk Burn Time
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("پ: ${food.proteinGrams.toInt().toPersian()}g", color = FreshLime, style = MaterialTheme.typography.labelSmall)
                    Text("ک: ${food.carbsGrams.toInt().toPersian()}g", color = AccentOrange, style = MaterialTheme.typography.labelSmall)
                    Text("چ: ${food.fatGrams.toInt().toPersian()}g", color = SecondaryGold, style = MaterialTheme.typography.labelSmall)
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.DirectionsWalk,
                        contentDescription = null,
                        tint = CalorieBurnRed,
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        text = "${food.burnMinutesWalk.toPersian()} دقیقه پیاده‌روی",
                        style = MaterialTheme.typography.labelSmall,
                        color = CalorieBurnRed
                    )
                }
            }
        }
    }
}

@Composable
fun LoggedMealCard(
    meal: LoggedMeal,
    onDelete: () -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = DarkSurfaceCard),
        shape = RoundedCornerShape(14.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(MintGreenPrimary.copy(alpha = 0.25f))
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = meal.mealType.title,
                            style = MaterialTheme.typography.labelSmall,
                            color = FreshLime,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Text(
                        text = meal.food.name,
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "${meal.portions.toPersian()} سهم • ${meal.totalCalories.toPersian()} کالری کل",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextMuted
                )
            }

            IconButton(onClick = onDelete) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "حذف وعده",
                    tint = CalorieBurnRed.copy(alpha = 0.8f)
                )
            }
        }
    }
}
