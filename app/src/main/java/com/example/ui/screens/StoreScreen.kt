package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Casino
import androidx.compose.material.icons.filled.Diamond
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material.icons.filled.OndemandVideo
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import com.example.data.StoreRepository
import com.example.data.UserPreferences
import com.example.model.StorePackage
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

@Composable
fun StoreScreen(
    userPrefs: UserPreferences,
    onNavigateToWheel: () -> Unit,
    modifier: Modifier = Modifier
) {
    val coins by userPrefs.coinsFlow.collectAsState()
    val isVip by userPrefs.isVipFlow.collectAsState()

    var selectedPackageToBuy by remember { mutableStateOf<StorePackage?>(null) }
    var purchaseSuccessMessage by remember { mutableStateOf<String?>(null) }
    var showRewardAdDialog by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 16.dp, bottom = 90.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Balance Card
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = DarkSurfaceForest),
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, MintGreenPrimary.copy(alpha = 0.4f), RoundedCornerShape(20.dp))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Brush.verticalGradient(
                                listOf(Color(0xFF1B4D2B), Color(0xFF0F311A))
                            )
                        )
                        .padding(20.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "موجودی سکه تندرستی شما",
                                style = MaterialTheme.typography.bodyMedium,
                                color = TextMuted
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = if (isVip) "نامحدود (عضویت طلایی VIP)" else "${coins.toPersian()} سکه",
                                style = MaterialTheme.typography.headlineMedium,
                                fontWeight = FontWeight.Bold,
                                color = TextGold
                            )
                        }

                        Box(
                            modifier = Modifier
                                .size(56.dp)
                                .clip(CircleShape)
                                .background(SecondaryGold),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.MonetizationOn,
                                contentDescription = "سکه",
                                tint = Color(0xFF1E1238),
                                modifier = Modifier.size(36.dp)
                            )
                        }
                    }
                }
            }
        }

        // Free Coins Section
        item {
            Text(
                text = "راه‌های دریافت سکه رایگان:",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = TextLightGreen
            )
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Card(
                    colors = CardDefaults.cardColors(containerColor = DarkSurfaceCard),
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier
                        .weight(1f)
                        .clickable { onNavigateToWheel() }
                        .testTag("store_go_to_wheel")
                ) {
                    Column(
                        modifier = Modifier.padding(12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(Icons.Default.Casino, contentDescription = null, tint = FreshLime)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("گردونه شانس", style = MaterialTheme.typography.labelMedium, color = Color.White)
                        Text("رایگان ۲۴ ساعته", style = MaterialTheme.typography.labelSmall, color = FreshLime)
                    }
                }

                Card(
                    colors = CardDefaults.cardColors(containerColor = DarkSurfaceCard),
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier
                        .weight(1f)
                        .clickable { showRewardAdDialog = true }
                        .testTag("store_watch_video_ad")
                ) {
                    Column(
                        modifier = Modifier.padding(12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(Icons.Default.OndemandVideo, contentDescription = null, tint = SecondaryGold)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("مشاهده تبلیغ", style = MaterialTheme.typography.labelMedium, color = Color.White)
                        Text("+۵۰ سکه هدیه", style = MaterialTheme.typography.labelSmall, color = TextGold)
                    }
                }

                Card(
                    colors = CardDefaults.cardColors(containerColor = DarkSurfaceCard),
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier
                        .weight(1f)
                        .clickable {
                            userPrefs.addCoins(100)
                            purchaseSuccessMessage = "۱۰۰ سکه پاداش دعوت از دوستان اضافه شد!"
                        }
                        .testTag("store_referral_bonus")
                ) {
                    Column(
                        modifier = Modifier.padding(12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(Icons.Default.Share, contentDescription = null, tint = MintGreenPrimary)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("دعوت دوستان", style = MaterialTheme.typography.labelMedium, color = Color.White)
                        Text("+۱۰۰ سکه هدیه", style = MaterialTheme.typography.labelSmall, color = TextGold)
                    }
                }
            }
        }

        // Store Packages
        item {
            Text(
                text = "بسته‌های خرید سکه و اشتراک VIP:",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = TextLightGreen,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        items(StoreRepository.coinPackages) { pkg ->
            StorePackageCard(
                pkg = pkg,
                isOwnedVip = isVip && pkg.isVIP,
                onBuyClick = { selectedPackageToBuy = pkg }
            )
        }
    }

    if (selectedPackageToBuy != null) {
        val pkg = selectedPackageToBuy!!

        AlertDialog(
            onDismissRequest = { selectedPackageToBuy = null },
            containerColor = DarkSurfaceForest,
            icon = {
                Icon(
                    imageVector = if (pkg.isVIP) Icons.Default.Diamond else Icons.Default.MonetizationOn,
                    contentDescription = null,
                    tint = SecondaryGold,
                    modifier = Modifier.size(36.dp)
                )
            },
            title = {
                Text(
                    text = "درگاه پرداخت درون‌برنامه‌ای بازار و مایکت",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = TextLightGreen,
                    textAlign = TextAlign.Center
                )
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        text = "بسته انتخابی: ${pkg.title}",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = "مبلغ پرداختی: ${PersianUtils.formatToman(pkg.priceToman)}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = FreshLime
                    )
                    Text(
                        text = "توضیحات: ${pkg.description}",
                        style = MaterialTheme.typography.bodySmall,
                        color = TextMuted
                    )
                    HorizontalDivider(color = Color.White.copy(alpha = 0.1f))
                    Text(
                        text = "💡 این درگاه شبیه‌سازی پرداخت درون‌برنامه‌ای بازار است و با زدن دکمه زیر تراکنش بلافاصله ثبت می‌شود.",
                        style = MaterialTheme.typography.labelSmall,
                        color = TextGold,
                        lineHeight = 18.sp
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (pkg.isVIP) {
                            userPrefs.setVip(true)
                            purchaseSuccessMessage = "اشتراک طلایی VIP لاغری فعال شد!"
                        } else {
                            userPrefs.addCoins(pkg.coins)
                            purchaseSuccessMessage = "${pkg.coins.toPersian()} سکه به حساب شما اضافه شد!"
                        }
                        selectedPackageToBuy = null
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = FreshLime),
                    modifier = Modifier.testTag("confirm_purchase_button")
                ) {
                    Text("پرداخت و فعال‌سازی فوری", color = Color(0xFF042F1A), fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                OutlinedButton(onClick = { selectedPackageToBuy = null }) {
                    Text("انصراف", color = TextMuted)
                }
            }
        )
    }

    if (purchaseSuccessMessage != null) {
        AlertDialog(
            onDismissRequest = { purchaseSuccessMessage = null },
            containerColor = DarkSurfaceForest,
            title = {
                Text(
                    text = "عملیات موفق",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = FreshLime
                )
            },
            text = {
                Text(text = purchaseSuccessMessage!!, style = MaterialTheme.typography.bodyMedium, color = Color.White)
            },
            confirmButton = {
                Button(
                    onClick = { purchaseSuccessMessage = null },
                    colors = ButtonDefaults.buttonColors(containerColor = FreshLime)
                ) {
                    Text("متشکرم", color = Color(0xFF042F1A), fontWeight = FontWeight.Bold)
                }
            }
        )
    }

    if (showRewardAdDialog) {
        AlertDialog(
            onDismissRequest = { showRewardAdDialog = false },
            containerColor = DarkSurfaceForest,
            title = {
                Text("تبلیغ ویدیویی جایزه‌دار", style = MaterialTheme.typography.titleMedium, color = TextGold)
            },
            text = {
                Text(
                    "با مشاهده ویدیوی اسپانسر بازار، ۵۰ سکه هدیه دریافت خواهید کرد.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        userPrefs.addCoins(50)
                        showRewardAdDialog = false
                        purchaseSuccessMessage = "۵۰ سکه جایزه به کیفتان اضافه شد!"
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = FreshLime)
                ) {
                    Text("مشاهده و دریافت پاداش", color = Color(0xFF042F1A), fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                OutlinedButton(onClick = { showRewardAdDialog = false }) {
                    Text("انصراف", color = TextMuted)
                }
            }
        )
    }
}

@Composable
fun StorePackageCard(
    pkg: StorePackage,
    isOwnedVip: Boolean,
    onBuyClick: () -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = if (pkg.isVIP) Color(0xFF263D18) else DarkSurfaceForest
        ),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = if (pkg.isVIP || pkg.isPopular) 1.5.dp else 1.dp,
                color = if (pkg.isVIP) SecondaryGold else if (pkg.isPopular) FreshLime else Color.White.copy(alpha = 0.15f),
                shape = RoundedCornerShape(16.dp)
            )
            .testTag("package_${pkg.id}")
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = if (pkg.isVIP) Icons.Default.Diamond else Icons.Default.MonetizationOn,
                        contentDescription = null,
                        tint = if (pkg.isVIP) SecondaryGold else FreshLime,
                        modifier = Modifier.size(24.dp)
                    )
                    Text(
                        text = pkg.title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }

                if (pkg.discountPercent > 0) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(FreshLime)
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = "${pkg.discountPercent.toPersian()}٪ تخفیف",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF042F1A)
                        )
                    }
                }
            }

            Text(
                text = pkg.description,
                style = MaterialTheme.typography.bodySmall,
                color = TextMuted,
                lineHeight = 20.sp
            )

            HorizontalDivider(color = Color.White.copy(alpha = 0.1f))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = PersianUtils.formatToman(pkg.priceToman),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = TextGold
                )

                if (isOwnedVip) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(FreshLime.copy(alpha = 0.2f))
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = "فعال است ✓",
                            color = FreshLime,
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                } else {
                    Button(
                        onClick = onBuyClick,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (pkg.isVIP) SecondaryGold else FreshLime
                        ),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text(
                            text = "خرید و فعال‌سازی",
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF042F1A)
                        )
                    }
                }
            }
        }
    }
}
