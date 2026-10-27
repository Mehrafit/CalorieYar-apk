package com.example.ui.screens

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.RocketLaunch
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.DarkSurfaceCard
import com.example.ui.theme.DarkSurfaceForest
import com.example.ui.theme.FreshLime
import com.example.ui.theme.MintGreenPrimary
import com.example.ui.theme.SecondaryGold
import com.example.ui.theme.TextGold
import com.example.ui.theme.TextLightGreen

@Composable
fun PublishGuideScreen(
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 16.dp, bottom = 90.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .clip(CircleShape)
                        .background(
                            Brush.linearGradient(listOf(FreshLime, MintGreenPrimary))
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.RocketLaunch,
                        contentDescription = null,
                        tint = Color(0xFF042F1A),
                        modifier = Modifier.size(30.dp)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "راهنمای انتشار «کالری‌یار» در بازار و مایکت",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = TextLightGreen
                )

                Text(
                    text = "ویژه اولین تجربه انتشار شما (بدون نیاز به هیچ دانش فنی)",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.White.copy(alpha = 0.85f)
                )
            }
        }

        item {
            GuideStepCard(
                stepNumber = "۱",
                title = "دریافت خروجی APK",
                description = "در نوار بالای محیط AI Studio، از منوی تنظیمات (یا منوی پروژه)، گزینه «Export APK» را بزنید. فایل APK نهایی بدون نیاز به نرم‌افزارهای جانبی دانلود می‌شود."
            )
        }

        item {
            GuideStepCard(
                stepNumber = "۲",
                title = "ثبت‌نام در پنل توسعه‌دهندگان",
                description = "وارد developers.cafebazaar.ir و panel.myket.ir شوید. ثبت‌نام به عنوان توسعه‌دهنده رایگان است و با وارد کردن کدملی و شماره تماس انجام می‌شود (تایید در کمتر از ۲۴ ساعت)."
            )
        }

        item {
            GuideStepCard(
                stepNumber = "۳",
                title = "ایجاد برنامه و آپلود فایل APK",
                description = "روی «برنامه جدید» کلیک کنید و فایل APK را آپلود کنید. عنوان را «کالری‌یار: کالری‌شمار و رژیم لاغری ایرانی» و دسته‌بندی را روی «پزشکی و سلامت» یا «سبک زندگی» انتخاب کنید."
            )
        }

        item {
            GuideStepCard(
                stepNumber = "۴",
                title = "ثبت محصولات درون‌برنامه‌ای ریالی",
                description = "در بخش محصولات درون‌برنامه‌ای بازار، شناسه‌های بسته‌ها (pkg_bronze, pkg_silver, pkg_gold, pkg_diamond, pkg_vip_lifetime) را ثبت کنید تا مبالغ پرداختی کاربران مستقیماً به شماره شبا بانکی شما واریز شود."
            )
        }

        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = DarkSurfaceCard),
                shape = RoundedCornerShape(18.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, FreshLime.copy(alpha = 0.5f), RoundedCornerShape(18.dp))
            ) {
                Column(
                    modifier = Modifier.padding(18.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(Icons.Default.CheckCircle, contentDescription = null, tint = FreshLime)
                        Text(
                            text = "چرا «کالری‌یار» در بازار و مایکت پردانلود می‌شود؟",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = TextGold
                        )
                    }

                    Text(
                        text = "• پایگاه داده اختصاصی غذاهای ایرانی (قورمه‌سبزی، قیمه، کباب کوبیده، نان سنگک و بربری) دقیقا با واحد اندازه‌گیری آشنای ایرانی‌ها (کفگیر و کف دست).\n• مربی هوش مصنوعی که تحلیل سفره شام و جایگزین سبک پیشنهاد می‌دهد.\n• گردونه شانس ۲۴ ساعته که کاربران را ملزم می‌کند هر روز اپ را باز کنند.\n• ردیاب آب و تناسب اندام روزانه بدون نیاز به اینترنت (کاملا آفلاین و سریع).",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.White.copy(alpha = 0.9f),
                        lineHeight = 22.sp
                    )
                }
            }
        }
    }
}

@Composable
fun GuideStepCard(
    stepNumber: String,
    title: String,
    description: String
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = DarkSurfaceForest),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(14.dp),
            verticalAlignment = Alignment.Top
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(FreshLime),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stepNumber,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF042F1A),
                    fontSize = 16.sp
                )
            }

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = TextLightGreen
                )
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.White.copy(alpha = 0.9f),
                    lineHeight = 22.sp
                )
            }
        }
    }
}
