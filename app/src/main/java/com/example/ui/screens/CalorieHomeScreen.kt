package com.example.ui.screens

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Casino
import androidx.compose.material.icons.filled.DirectionsRun
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Opacity
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.UserPreferences
import com.example.model.MealType
import com.example.ui.theme.AccentOrange
import com.example.ui.theme.CalorieBurnRed
import com.example.ui.theme.DarkSurfaceCard
import com.example.ui.theme.DarkSurfaceForest
import com.example.ui.theme.DarkSurfaceLightCard
import com.example.ui.theme.FreshLime
import com.example.ui.theme.MintGreenPrimary
import com.example.ui.theme.SecondaryGold
import com.example.ui.theme.TextGold
import com.example.ui.theme.TextLightGreen
import com.example.ui.theme.TextMuted
import com.example.ui.theme.WaterBlue
import com.example.util.PersianUtils
import com.example.util.PersianUtils.toPersian
import kotlinx.coroutines.delay

@Composable
fun CalorieHomeScreen(
    userPrefs: UserPreferences,
    onNavigateToDiary: () -> Unit,
    onNavigateToAiCoach: () -> Unit,
    onNavigateToWheel: () -> Unit,
    onNavigateToStore: () -> Unit,
    modifier: Modifier = Modifier
) {
    val jalaliToday = remember { PersianUtils.getTodayJalali() }
    val consumedCalories by userPrefs.consumedCaloriesFlow.collectAsState()
    val waterGlasses by userPrefs.waterFlow.collectAsState()
    val goalCalories = userPrefs.getDailyCalorieGoal()

    val remainingCalories = (goalCalories - consumedCalories).coerceAtLeast(0)
    val progressFraction = (consumedCalories.toFloat() / goalCalories.toFloat()).coerceIn(0f, 1f)

    var remainingWheelMillis by remember {
        mutableLongStateOf(userPrefs.getRemainingSpinCooldown())
    }

    LaunchedEffect(Unit) {
        while (true) {
            remainingWheelMillis = userPrefs.getRemainingSpinCooldown()
            delay(1000L)
        }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 16.dp, bottom = 90.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // 1. Jalali Date & Calorie Hero Dashboard Card
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = DarkSurfaceForest),
                shape = RoundedCornerShape(22.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("home_calorie_dashboard")
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Brush.verticalGradient(
                                listOf(DarkSurfaceLightCard, DarkSurfaceForest)
                            )
                        )
                        .padding(20.dp)
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
                        // Date header
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = jalaliToday.formatFull(),
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = TextLightGreen
                            )
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(MintGreenPrimary.copy(alpha = 0.2f))
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                            ) {
                                Text(
                                    text = "هدف: لاغری و تثبیت",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = FreshLime,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                        // Calorie Progress Ring / Details
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            // Circular Indicator
                            Box(
                                modifier = Modifier.size(110.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator(
                                    progress = { progressFraction },
                                    strokeWidth = 10.dp,
                                    color = if (consumedCalories > goalCalories) CalorieBurnRed else MintGreenPrimary,
                                    trackColor = Color.White.copy(alpha = 0.1f),
                                    modifier = Modifier.fillMaxSize()
                                )
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text(
                                        text = remainingCalories.toPersian(),
                                        style = MaterialTheme.typography.titleLarge,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White
                                    )
                                    Text(
                                        text = "مانده (کالری)",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = TextMuted,
                                        fontSize = 10.sp
                                    )
                                }
                            }

                            // Calorie breakdown stats
                            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                StatPill(
                                    title = "سقف مجاز روزانه",
                                    value = "${goalCalories.toPersian()} کالری",
                                    color = TextGold
                                )
                                StatPill(
                                    title = "مصرف‌شده تا کنون",
                                    value = "${consumedCalories.toPersian()} کالری",
                                    color = MintGreenPrimary
                                )
                                StatPill(
                                    title = "سوزانده‌شده با فعالیت",
                                    value = "۳۲۰ کالری",
                                    color = CalorieBurnRed
                                )
                            }
                        }

                        HorizontalDivider(color = Color.White.copy(alpha = 0.1f))

                        // Macros summary
                        val totalProtein = userPrefs.todayMeals.sumOf { it.totalProtein.toDouble() }.toInt()
                        val totalCarbs = userPrefs.todayMeals.sumOf { it.totalCarbs.toDouble() }.toInt()
                        val totalFat = userPrefs.todayMeals.sumOf { it.totalFat.toDouble() }.toInt()

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            MacroColumn("پروتئین", "${totalProtein.toPersian()} گرم", FreshLime)
                            MacroColumn("کربوهیدرات", "${totalCarbs.toPersian()} گرم", AccentOrange)
                            MacroColumn("چربی مفید", "${totalFat.toPersian()} گرم", SecondaryGold)
                        }
                    }
                }
            }
        }

        // 2. Daily Lucky Wheel Banner
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = DarkSurfaceCard),
                shape = RoundedCornerShape(18.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onNavigateToWheel() }
                    .testTag("home_lucky_wheel_banner")
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Brush.horizontalGradient(
                                listOf(Color(0xFF144728), Color(0xFF0D2818))
                            )
                        )
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .clip(CircleShape)
                                .background(
                                    Brush.linearGradient(listOf(SecondaryGold, AccentOrange))
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Casino,
                                contentDescription = null,
                                tint = Color(0xFF042F1A),
                                modifier = Modifier.size(26.dp)
                            )
                        }

                        Column {
                            Text(
                                text = "گردونه شانس ۲۴ ساعته تندرستی",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = TextGold
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = if (remainingWheelMillis <= 0) "چرخش رایگان امروز فعال است!"
                                else "تا شانس بعدی: ${PersianUtils.formatRemainingTime(remainingWheelMillis)}",
                                style = MaterialTheme.typography.bodySmall,
                                color = if (remainingWheelMillis <= 0) FreshLime else Color.White.copy(alpha = 0.8f)
                            )
                        }
                    }

                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = "رفتن به گردونه",
                        tint = TextGold,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }

        // 3. Water Tracker Card
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = DarkSurfaceCard),
                shape = RoundedCornerShape(18.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(44.dp)
                                .clip(CircleShape)
                                .background(WaterBlue.copy(alpha = 0.2f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Opacity,
                                contentDescription = null,
                                tint = WaterBlue,
                                modifier = Modifier.size(26.dp)
                            )
                        }

                        Column {
                            Text(
                                text = "مصرف آب امروز: ${waterGlasses.toPersian()} از ۸ لیوان",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Text(
                                text = if (waterGlasses >= 8) "آفرین! هیدراتاسیون بدن کامل شد." else "هر ساعت یک لیوان آب برای افزایش چربی‌سوزی بنوشید.",
                                style = MaterialTheme.typography.bodySmall,
                                color = TextMuted,
                                fontSize = 11.sp
                            )
                        }
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        IconButton(
                            onClick = { userPrefs.removeWaterGlass() },
                            modifier = Modifier.size(36.dp)
                        ) {
                            Icon(Icons.Default.Remove, contentDescription = "کاهش آب", tint = TextMuted)
                        }
                        IconButton(
                            onClick = { userPrefs.addWaterGlass() },
                            modifier = Modifier.size(36.dp)
                        ) {
                            Icon(Icons.Default.Add, contentDescription = "افزایش آب", tint = WaterBlue)
                        }
                    }
                }
            }
        }

        // 4. Quick Action Cards (ثبت غذای جدید / پرسش از مربی هوش مصنوعی)
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Food Diary
                Card(
                    colors = CardDefaults.cardColors(containerColor = DarkSurfaceForest),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .weight(1f)
                        .clickable { onNavigateToDiary() }
                        .testTag("home_add_meal_card")
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(MintGreenPrimary),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.Restaurant, contentDescription = null, tint = Color(0xFF042F1A))
                        }
                        Text(
                            text = "کالری‌شمار غذاها",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = "جستجوی خورش‌ها، پلوها و سفره ایرانی",
                            style = MaterialTheme.typography.bodySmall,
                            color = TextMuted,
                            fontSize = 11.sp
                        )
                    }
                }

                // AI Coach
                Card(
                    colors = CardDefaults.cardColors(containerColor = DarkSurfaceForest),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .weight(1f)
                        .clickable { onNavigateToAiCoach() }
                        .testTag("home_ai_coach_card")
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(SecondaryGold),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.Psychology, contentDescription = null, tint = Color(0xFF1E1238))
                        }
                        Text(
                            text = "مربی هوش مصنوعی",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = "آنالیز سفره شما و پیشنهاد شام رژیمی",
                            style = MaterialTheme.typography.bodySmall,
                            color = TextMuted,
                            fontSize = 11.sp
                        )
                    }
                }
            }
        }

        // 5. BMI & Weight Goal Card
        val weight = userPrefs.getWeight()
        val targetWeight = userPrefs.getTargetWeight()
        val diff = (weight - targetWeight).coerceAtLeast(0f)

        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = DarkSurfaceCard),
                shape = RoundedCornerShape(18.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "مسیر کاهش وزن شما",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = TextLightGreen
                        )
                        Text(
                            text = "هدف: ${diff.toInt().toPersian()} کیلوگرم تا تناسب اندام",
                            style = MaterialTheme.typography.labelSmall,
                            color = SecondaryGold,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "وزن فعلی: ${weight.toInt().toPersian()} کیلوگرم",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.White
                        )
                        Text(
                            text = "وزن ایده‌آل: ${targetWeight.toInt().toPersian()} کیلوگرم",
                            style = MaterialTheme.typography.bodyMedium,
                            color = TextLightGreen
                        )
                    }

                    LinearProgressIndicator(
                        progress = { 0.45f },
                        color = FreshLime,
                        trackColor = Color.White.copy(alpha = 0.1f),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                            .clip(RoundedCornerShape(4.dp))
                    )
                }
            }
        }
    }
}

@Composable
fun StatPill(title: String, value: String, color: Color) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(
            modifier = Modifier
                .size(8.dp)
                .clip(CircleShape)
                .background(color)
        )
        Text(
            text = "$title: ",
            style = MaterialTheme.typography.bodySmall,
            color = TextMuted
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }
}

@Composable
fun MacroColumn(label: String, value: String, color: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(label, style = MaterialTheme.typography.labelSmall, color = TextMuted)
        Spacer(modifier = Modifier.height(2.dp))
        Text(value, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold, color = color)
    }
}
