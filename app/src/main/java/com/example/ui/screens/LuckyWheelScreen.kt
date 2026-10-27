package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Casino
import androidx.compose.material.icons.filled.Celebration
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material.icons.filled.OndemandVideo
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.UserPreferences
import com.example.model.WheelSlice
import com.example.ui.components.LuckyWheelCanvas
import com.example.ui.theme.AccentOrange
import com.example.ui.theme.DarkSurfaceCard
import com.example.ui.theme.DarkSurfaceForest
import com.example.ui.theme.FreshLime
import com.example.ui.theme.MintGreenPrimary
import com.example.ui.theme.SecondaryGold
import com.example.ui.theme.TextGold
import com.example.ui.theme.TextLightGreen
import com.example.ui.theme.TextMuted
import com.example.util.PersianUtils
import com.example.util.PersianUtils.toPersian
import kotlinx.coroutines.delay

@Composable
fun LuckyWheelScreen(
    userPrefs: UserPreferences,
    modifier: Modifier = Modifier
) {
    var isSpinning by remember { mutableStateOf(false) }
    var winningSlice by remember { mutableStateOf<WheelSlice?>(null) }
    var showRewardDialog by remember { mutableStateOf(false) }
    var showAdSimulation by remember { mutableStateOf(false) }

    var remainingMillis by remember {
        mutableLongStateOf(userPrefs.getRemainingSpinCooldown())
    }

    LaunchedEffect(Unit) {
        while (true) {
            remainingMillis = userPrefs.getRemainingSpinCooldown()
            delay(1000L)
        }
    }

    val canSpin = remainingMillis <= 0L && !isSpinning

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "گردونه شانس ۲۴ ساعته تندرستی",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = TextLightGreen
        )

        Text(
            text = "هر ۲۴ ساعت یکبار گردونه را بچرخانید و تا ۱۰۰۰ سکه برای مشاوره‌های رژیم لاغری برنده شوید!",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.White.copy(alpha = 0.85f),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        LuckyWheelCanvas(
            isSpinning = isSpinning,
            onSpinStart = { isSpinning = true },
            onSpinEnd = { prize ->
                isSpinning = false
                winningSlice = prize
                userPrefs.recordLuckySpin()
                userPrefs.addCoins(prize.coins)
                remainingMillis = userPrefs.getRemainingSpinCooldown()
                showRewardDialog = true
            }
        )

        Spacer(modifier = Modifier.height(12.dp))

        Card(
            colors = CardDefaults.cardColors(containerColor = DarkSurfaceForest),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (canSpin) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Casino,
                            contentDescription = null,
                            tint = FreshLime,
                            modifier = Modifier.size(24.dp)
                        )
                        Text(
                            text = "شانس امروز شما آماده است!",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = FreshLime
                        )
                    }

                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "دکمه مرکز گردونه را لمس کنید تا چرخش آغاز شود.",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.White.copy(alpha = 0.8f)
                    )
                } else {
                    Text(
                        text = "زمان تا چرخش رایگان بعدی:",
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextMuted
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = PersianUtils.formatRemainingTime(remainingMillis),
                        style = MaterialTheme.typography.displayMedium,
                        fontWeight = FontWeight.Bold,
                        color = TextGold
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Button(
                        onClick = { showAdSimulation = true },
                        colors = ButtonDefaults.buttonColors(containerColor = MintGreenPrimary),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("watch_ad_for_spin_button")
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.OndemandVideo,
                                contentDescription = null,
                                tint = Color(0xFF042F1A),
                                modifier = Modifier.size(20.dp)
                            )
                            Text(
                                text = "چرخش فوری با تماشای تبلیغ اسپانسر",
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF042F1A)
                            )
                        }
                    }
                }
            }
        }

        Card(
            colors = CardDefaults.cardColors(containerColor = DarkSurfaceCard),
            shape = RoundedCornerShape(14.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(14.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.MonetizationOn,
                    contentDescription = null,
                    tint = SecondaryGold,
                    modifier = Modifier.size(24.dp)
                )
                Text(
                    text = "سکه‌های برنده شده به حساب شما اضافه می‌شوند و برای تحلیل وعده‌های غذایی و دریافت برنامه‌های رژیم لاغری از مربی هوش مصنوعی استفاده می‌شوند.",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.White.copy(alpha = 0.85f),
                    lineHeight = 20.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(70.dp))
    }

    if (showRewardDialog && winningSlice != null) {
        val prize = winningSlice!!
        AlertDialog(
            onDismissRequest = { showRewardDialog = false },
            containerColor = DarkSurfaceForest,
            icon = {
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape)
                        .background(
                            Brush.linearGradient(listOf(SecondaryGold, AccentOrange))
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Celebration,
                        contentDescription = "جایزه",
                        tint = Color(0xFF1E1238),
                        modifier = Modifier.size(36.dp)
                    )
                }
            },
            title = {
                Text(
                    text = "تبریک! برنده شدید 🎉",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = TextGold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            },
            text = {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "گردونه روی پاداش زیر ایستاد:",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White.copy(alpha = 0.8f)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "${prize.coins.toPersian()} سکه طلایی",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = if (prize.isJackpot) SecondaryGold else FreshLime
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = { showRewardDialog = false },
                    colors = ButtonDefaults.buttonColors(containerColor = FreshLime),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("claim_wheel_reward_button")
                ) {
                    Text("دریافت سکه‌ها", color = Color(0xFF042F1A), fontWeight = FontWeight.Bold)
                }
            }
        )
    }

    if (showAdSimulation) {
        var adSecondsLeft by remember { mutableStateOf(4) }

        LaunchedEffect(Unit) {
            while (adSecondsLeft > 0) {
                delay(1000L)
                adSecondsLeft -= 1
            }
        }

        AlertDialog(
            onDismissRequest = {},
            containerColor = Color(0xFF0B2113),
            title = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(Icons.Default.OndemandVideo, contentDescription = null, tint = SecondaryGold)
                    Text("ویدیوی اسپانسر بازار و مایکت", style = MaterialTheme.typography.titleMedium, color = TextGold)
                }
            },
            text = {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(130.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color.Black),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Default.Star, contentDescription = null, tint = FreshLime, modifier = Modifier.size(36.dp))
                        Spacer(modifier = Modifier.height(6.dp))
                        Text("در حال پخش تیزر تبلیغاتی...", color = Color.White, style = MaterialTheme.typography.bodyMedium)
                        Text(
                            text = if (adSecondsLeft > 0) "پایان تا: ${adSecondsLeft.toPersian()} ثانیه" else "پاداش آماده دریافت است!",
                            color = FreshLime,
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        showAdSimulation = false
                        userPrefs.addCoins(75)
                        remainingMillis = 0L
                    },
                    enabled = adSecondsLeft == 0,
                    colors = ButtonDefaults.buttonColors(containerColor = FreshLime),
                    modifier = Modifier.testTag("ad_reward_complete_button")
                ) {
                    Text(
                        text = if (adSecondsLeft == 0) "دریافت ۷۵ سکه و چرخش آزاد" else "کمی صبر کنید...",
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF042F1A)
                    )
                }
            }
        )
    }
}
