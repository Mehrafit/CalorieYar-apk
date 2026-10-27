package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DirectionsWalk
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
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
import com.example.data.OfflineAiCoach
import com.example.data.UserPreferences
import com.example.model.AiDietConsultation
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
import com.example.util.PersianUtils.toPersian

@Composable
fun AiDietCoachScreen(
    userPrefs: UserPreferences,
    onNavigateToStore: () -> Unit,
    modifier: Modifier = Modifier
) {
    var queryText by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    val consultations = remember { mutableStateListOf<AiDietConsultation>() }

    val sampleDietQueries = listOf(
        "ظهر چلوکباب خوردم، شام چی بخورم؟",
        "چطور در ۳۰ روز ۵ کیلو کم کنم؟",
        "برای مهار هوس شیرینی چه کنم؟",
        "آیا نان سنگک برای لاغری بهتر است؟"
    )

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 16.dp, bottom = 90.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Header
        item {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape)
                        .background(
                            Brush.linearGradient(listOf(MintGreenPrimary, SecondaryGold))
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Psychology,
                        contentDescription = "مربی هوش مصنوعی",
                        tint = Color(0xFF042F1A),
                        modifier = Modifier.size(34.dp)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "مربی هوشمند لاغری و رژیم",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = TextLightGreen
                )

                Text(
                    text = "غذایی که خورده‌اید یا هدف کاهش وزنتان را بنویسید؛ هوش مصنوعی آن را آنالیز کرده و برنامه شام و پیاده‌روی جایگزین به شما می‌دهد.",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.White.copy(alpha = 0.85f),
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                    lineHeight = 22.sp
                )
            }
        }

        // Input Card
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = DarkSurfaceForest),
                shape = RoundedCornerShape(18.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "سوال یا شرح غذای شما:",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = TextGold
                    )

                    OutlinedTextField(
                        value = queryText,
                        onValueChange = { queryText = it },
                        placeholder = {
                            Text("مثلا: امروز ناهار قورمه‌سبزی با ۲ کفگیر پلو خوردم، تا شب چه برنامه‌ای داشته باشم؟", color = TextMuted)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(110.dp)
                            .testTag("ai_coach_query_input"),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = FreshLime,
                            unfocusedBorderColor = MintGreenPrimary.copy(alpha = 0.5f),
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White
                        )
                    )

                    // Quick Chips
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(sampleDietQueries) { chip ->
                            SuggestionChip(
                                onClick = { queryText = chip },
                                label = { Text(chip, fontSize = 11.sp) },
                                colors = SuggestionChipDefaults.suggestionChipColors(
                                    containerColor = DarkSurfaceCard,
                                    labelColor = TextLightGreen
                                )
                            )
                        }
                    }

                    if (errorMessage != null) {
                        Text(
                            text = errorMessage!!,
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }

                    Button(
                        onClick = {
                            if (queryText.trim().length < 4) {
                                errorMessage = "لطفاً توضیحات غذای خود را دقیق‌تر بنویسید."
                                return@Button
                            }
                            if (userPrefs.spendCoins(UserPreferences.COST_AI_MEAL_ANALYSIS)) {
                                errorMessage = null
                                val advice = OfflineAiCoach.analyzeMealOrDiet(queryText)
                                consultations.add(0, advice)
                                queryText = ""
                            } else {
                                errorMessage = "سکه کافی ندارید! برای تحلیل به ۲۰ سکه نیاز است."
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = FreshLime),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .testTag("submit_ai_diet_coach_button")
                    ) {
                        Icon(Icons.Default.AutoAwesome, contentDescription = null, tint = Color(0xFF042F1A))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = if (userPrefs.isVip()) "تحلیل هوشمند رژیم (رایگان VIP)" else "تحلیل هوشمند رژیم (۲۰ سکه)",
                            color = Color(0xFF042F1A),
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        // Consultations list
        if (consultations.isNotEmpty()) {
            item {
                Text(
                    text = "نتایج تحلیل و مشاوره‌های تغذیه:",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = TextGold
                )
            }

            items(consultations) { item ->
                AiDietAdviceCard(item)
            }
        }
    }
}

@Composable
fun AiDietAdviceCard(item: AiDietConsultation) {
    Card(
        colors = CardDefaults.cardColors(containerColor = DarkSurfaceCard),
        shape = RoundedCornerShape(18.dp),
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, MintGreenPrimary.copy(alpha = 0.4f), RoundedCornerShape(18.dp))
    ) {
        Column(
            modifier = Modifier.padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Header: Verdict & Score
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(MintGreenPrimary.copy(alpha = 0.25f))
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = item.dietVerdict,
                        style = MaterialTheme.typography.labelSmall,
                        color = FreshLime,
                        fontWeight = FontWeight.Bold
                    )
                }

                Text(
                    text = "نمره سلامت: ${item.healthScore.toPersian()} از ۱۰۰",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold,
                    color = if (item.healthScore > 75) FreshLime else AccentOrange
                )
            }

            LinearProgressIndicator(
                progress = { item.healthScore / 100f },
                color = if (item.healthScore > 75) FreshLime else AccentOrange,
                trackColor = Color.White.copy(alpha = 0.1f),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .clip(RoundedCornerShape(3.dp))
            )

            // User query
            Text(
                text = "«${item.userMealDescription}»",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold,
                color = TextGold
            )

            HorizontalDivider(color = Color.White.copy(alpha = 0.1f))

            // Analysis
            Text(
                text = item.analysisText,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White,
                lineHeight = 23.sp
            )

            // Alternatives & Dinner Tips
            Text(
                text = "توصیه‌های کاربردی و وعده جایگزین سبک:",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = TextLightGreen
            )

            item.practicalAlternatives.forEachIndexed { index, tip ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.Top,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = null,
                        tint = FreshLime,
                        modifier = Modifier
                            .size(18.dp)
                            .padding(top = 2.dp)
                    )
                    Text(
                        text = tip,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.White.copy(alpha = 0.9f),
                        lineHeight = 21.sp
                    )
                }
            }

            HorizontalDivider(color = Color.White.copy(alpha = 0.1f))

            // Workout Burn Tip
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color(0xFF0F2615))
                    .padding(12.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.DirectionsWalk,
                        contentDescription = null,
                        tint = CalorieBurnRed,
                        modifier = Modifier.size(24.dp)
                    )
                    Text(
                        text = item.workoutBurnTip,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.White,
                        lineHeight = 20.sp
                    )
                }
            }
        }
    }
}
