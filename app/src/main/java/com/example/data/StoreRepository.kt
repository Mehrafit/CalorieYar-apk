package com.example.data

import androidx.compose.ui.graphics.Color
import com.example.model.StorePackage
import com.example.model.WheelSlice

object StoreRepository {

    val wheelSlices = listOf(
        WheelSlice(
            id = 0,
            title = "۵۰ سکه",
            coins = 50,
            primaryColor = Color(0xFF10B981),
            secondaryColor = Color(0xFF059669)
        ),
        WheelSlice(
            id = 1,
            title = "۱۰۰ سکه",
            coins = 100,
            primaryColor = Color(0xFF22C55E),
            secondaryColor = Color(0xFF16A34A)
        ),
        WheelSlice(
            id = 2,
            title = "۷۰ سکه",
            coins = 70,
            primaryColor = Color(0xFF0EA5E9),
            secondaryColor = Color(0xFF0284C7)
        ),
        WheelSlice(
            id = 3,
            title = "۲۰۰ سکه",
            coins = 200,
            primaryColor = Color(0xFFFFB703),
            secondaryColor = Color(0xFFF59E0B)
        ),
        WheelSlice(
            id = 4,
            title = "۱۵۰ سکه",
            coins = 150,
            primaryColor = Color(0xFFFB8500),
            secondaryColor = Color(0xFFEA580C)
        ),
        WheelSlice(
            id = 5,
            title = "۵۰۰ سکه",
            coins = 500,
            primaryColor = Color(0xFFFFD700),
            secondaryColor = Color(0xFFFFC107),
            isJackpot = true
        ),
        WheelSlice(
            id = 6,
            title = "۳۰۰ سکه",
            coins = 300,
            primaryColor = Color(0xFF8B5CF6),
            secondaryColor = Color(0xFF7C3AED)
        ),
        WheelSlice(
            id = 7,
            title = "۱۰۰۰ سکه!",
            coins = 1000,
            primaryColor = Color(0xFFEF4444),
            secondaryColor = Color(0xFFDC2626),
            isJackpot = true
        )
    )

    val coinPackages = listOf(
        StorePackage(
            id = "pkg_bronze",
            title = "بسته برنزی فیتنس",
            coins = 500,
            priceToman = 5000,
            discountPercent = 0,
            isPopular = false,
            description = "مناسب برای چندین تحلیل هوش مصنوعی غذا و برنامه رژیم هفتگی"
        ),
        StorePackage(
            id = "pkg_silver",
            title = "بسته نقره‌ای لاغری",
            coins = 1500,
            priceToman = 12000,
            discountPercent = 20,
            isPopular = false,
            description = "شامل ۱۵۰۰ سکه با ۲۰٪ پاداش برای رصد کامل کالری‌های ماهانه"
        ),
        StorePackage(
            id = "pkg_gold",
            title = "بسته طلایی تندرستی (پرفروش)",
            coins = 4000,
            priceToman = 25000,
            discountPercent = 38,
            isPopular = true,
            description = "محبوب‌ترین بسته در بازار و مایکت با ۳۸٪ تخفیف ویژه"
        ),
        StorePackage(
            id = "pkg_diamond",
            title = "گنجینه الماس مربیگری",
            coins = 10000,
            priceToman = 49000,
            discountPercent = 50,
            isPopular = false,
            description = "بسته پرحجم برای اهداف بلندمدت تناسب اندام با نصف قیمت"
        ),
        StorePackage(
            id = "pkg_vip_lifetime",
            title = "عضویت طلایی VIP مادام‌العمر",
            coins = 99999,
            priceToman = 99000,
            discountPercent = 40,
            isPopular = true,
            isVIP = true,
            description = "دسترسی بی‌نهایت به مربی هوش مصنوعی، رژیم‌های اختصاصی و حذف کامل تبلیغات برای همیشه"
        )
    )
}
