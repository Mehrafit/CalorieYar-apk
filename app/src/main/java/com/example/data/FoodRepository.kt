package com.example.data

import com.example.model.IranianFoodItem

object FoodRepository {

    val categories = listOf(
        "همه",
        "پلو و خورش سنتی",
        "کباب و گوشت",
        "نان و غلات",
        "خوراک و کوکو",
        "میوه و سبزیجات",
        "لبنیات و نوشیدنی"
    )

    val foods = listOf(
        IranianFoodItem(
            id = "f1",
            name = "چلو قورمه‌سبزی با گوشت گوسفندی",
            category = "پلو و خورش سنتی",
            portionUnit = "یک بشقاب متوسط (برنج + خورش)",
            caloriesPerPortion = 620,
            carbsGrams = 78f,
            proteinGrams = 28f,
            fatGrams = 22f,
            burnMinutesWalk = 75,
            dietaryTip = "برای کاهش چربی، روغن روی خورش را جدا کرده و برنج را کته بدون روغن مصرف کنید."
        ),
        IranianFoodItem(
            id = "f2",
            name = "چلو کباب کوبیده زعفرانی (۲ سیخ)",
            category = "کباب و گوشت",
            portionUnit = "یک پرس با کره و گوجه کبابی",
            caloriesPerPortion = 750,
            carbsGrams = 70f,
            proteinGrams = 38f,
            fatGrams = 34f,
            burnMinutesWalk = 90,
            dietaryTip = "حذف کره و مصرف سالاد شیرازی به جای نوشابه، کالری را ۲۰۰ واحد کاهش می‌دهد."
        ),
        IranianFoodItem(
            id = "f3",
            name = "زرشک‌پلو با مرغ مجلسی",
            category = "پلو و خورش سنتی",
            portionUnit = "یک ران مرغ + برنج زعفرانی",
            caloriesPerPortion = 580,
            carbsGrams = 68f,
            proteinGrams = 34f,
            fatGrams = 18f,
            burnMinutesWalk = 65,
            dietaryTip = "پوست مرغ را پیش از پخت جدا کنید تا کالری چربی اشباع شده به حداقل برسد."
        ),
        IranianFoodItem(
            id = "f4",
            name = "آش رشته سنتی با پیازداغ و کشک",
            category = "خوراک و کوکو",
            portionUnit = "یک کاسه متوسط (۳۰۰ گرم)",
            caloriesPerPortion = 420,
            carbsGrams = 55f,
            proteinGrams = 14f,
            fatGrams = 16f,
            burnMinutesWalk = 50,
            dietaryTip = "سرشار از فیبر و حبوبات؛ نعناداغ و پیازداغ را به اندازه یک قاشق چایخوری کنترل کنید."
        ),
        IranianFoodItem(
            id = "f5",
            name = "دیزی و آبگوشت سنتی با نخود",
            category = "خوراک و کوکو",
            portionUnit = "یک کاسه آبگوشت + گوشت‌کوبیده",
            caloriesPerPortion = 680,
            carbsGrams = 52f,
            proteinGrams = 36f,
            fatGrams = 32f,
            burnMinutesWalk = 80,
            dietaryTip = "از نان سنگک سبوس‌دار استفاده کنید و دنبه چرب را حذف نمایید."
        ),
        IranianFoodItem(
            id = "f6",
            name = "جوجه‌کباب با استخوان و گوجه",
            category = "کباب و گوشت",
            portionUnit = "یک سیخ ۳۰۰ گرمی",
            caloriesPerPortion = 380,
            carbsGrams = 4f,
            proteinGrams = 45f,
            fatGrams = 18f,
            burnMinutesWalk = 45,
            dietaryTip = "یکی از بهترین گزینه‌های پروتئینی رژیم لاغری است."
        ),
        IranianFoodItem(
            id = "f7",
            name = "کوکو سبزی با گردو و زرشک",
            category = "خوراک و کوکو",
            portionUnit = "دو برش مثلثی (۱۵۰ گرم)",
            caloriesPerPortion = 290,
            carbsGrams = 12f,
            proteinGrams = 10f,
            fatGrams = 22f,
            burnMinutesWalk = 35,
            dietaryTip = "پخت در فر بدون روغن اضافی، کالری آن را به نصف می‌رساند."
        ),
        IranianFoodItem(
            id = "f8",
            name = "کشک بادمجان با گردو و سیرداغ",
            category = "خوراک و کوکو",
            portionUnit = "یک پیاله متوسط (۲۰۰ گرم)",
            caloriesPerPortion = 340,
            carbsGrams = 18f,
            proteinGrams = 11f,
            fatGrams = 24f,
            burnMinutesWalk = 40,
            dietaryTip = "به جای سرخ کردن عمیق، بادمجان را کبابی یا بخارپز کنید."
        ),
        IranianFoodItem(
            id = "f9",
            name = "عدس‌پلو با کشمش و خرمای تفت‌داده",
            category = "پلو و خورش سنتی",
            portionUnit = "یک بشقاب (۸ قاشق غذاخوری)",
            caloriesPerPortion = 480,
            carbsGrams = 82f,
            proteinGrams = 16f,
            fatGrams = 8f,
            burnMinutesWalk = 55,
            dietaryTip = "ترکیب آهن و پروتئین گیاهی؛ برای احساس سیری طولانی‌مدت عالی است."
        ),
        IranianFoodItem(
            id = "f10",
            name = "نان سنگک سنتی کنجدی",
            category = "نان و غلات",
            portionUnit = "یک کف دست (۳۰ گرم)",
            caloriesPerPortion = 75,
            carbsGrams = 15f,
            proteinGrams = 3f,
            fatGrams = 0.5f,
            burnMinutesWalk = 10,
            dietaryTip = "بهترین نان سنتی به دلیل داشتن سبوس طبیعی و شاخص گلیسمی کنترل‌شده."
        ),
        IranianFoodItem(
            id = "f11",
            name = "نان بربری داغ",
            category = "نان و غلات",
            portionUnit = "یک کف دست (۳۵ گرم)",
            caloriesPerPortion = 85,
            carbsGrams = 17f,
            proteinGrams = 2.8f,
            fatGrams = 0.6f,
            burnMinutesWalk = 12,
            dietaryTip = "مصرف در وعده صبحانه همراه با پنیر کم‌چرب و گردو توصیه می‌شود."
        ),
        IranianFoodItem(
            id = "f12",
            name = "سالاد شیرازی با آبغوره و نعناع",
            category = "میوه و سبزیجات",
            portionUnit = "یک کاسه متوسط (خیار، گوجه، پیاز)",
            caloriesPerPortion = 45,
            carbsGrams = 9f,
            proteinGrams = 2f,
            fatGrams = 0.2f,
            burnMinutesWalk = 5,
            dietaryTip = "کالری منفی و چربی‌سوز عالی؛ قبل از غذا میل کنید تا حجم معده پر شود."
        ),
        IranianFoodItem(
            id = "f13",
            name = "ماست و خیار محلی با گل سرخ و نعناع",
            category = "لبنیات و نوشیدنی",
            portionUnit = "یک پیاله ماست کم‌چرب (۱۵۰ گرم)",
            caloriesPerPortion = 95,
            carbsGrams = 8f,
            proteinGrams = 6f,
            fatGrams = 3f,
            burnMinutesWalk = 12,
            dietaryTip = "پروبیوتیک طبیعی و تقویت‌کننده هضم غذا و خنک‌کننده طبع بدن."
        ),
        IranianFoodItem(
            id = "f14",
            name = "تخم‌مرغ آب‌پز کامل",
            category = "خوراک و کوکو",
            portionUnit = "یک عدد بزرگ (۵۰ گرم)",
            caloriesPerPortion = 78,
            carbsGrams = 0.6f,
            proteinGrams = 6.5f,
            fatGrams = 5.3f,
            burnMinutesWalk = 10,
            dietaryTip = "پروتئین خالص برای ساخت عضله و سرکوب هورمون گرسنگی (گرلین)."
        ),
        IranianFoodItem(
            id = "f15",
            name = "دوغ سنتی بدون گاز با پونه",
            category = "لبنیات و نوشیدنی",
            portionUnit = "یک لیوان (۲۰۰ میلی‌لیتر)",
            caloriesPerPortion = 60,
            carbsGrams = 5f,
            proteinGrams = 3.5f,
            fatGrams = 2f,
            burnMinutesWalk = 8,
            dietaryTip = "جایگزین عالی نوشابه در سفره ایرانی، غنی از کلسیم."
        ),
        IranianFoodItem(
            id = "f16",
            name = "سیب درختی سرخ دماوند",
            category = "میوه و سبزیجات",
            portionUnit = "یک عدد متوسط (۱۵۰ گرم)",
            caloriesPerPortion = 80,
            carbsGrams = 21f,
            proteinGrams = 0.5f,
            fatGrams = 0.3f,
            burnMinutesWalk = 10,
            dietaryTip = "حاوی پکتین و فیبر بالا که سرعت جذب قند را کنترل می‌کند."
        ),
        IranianFoodItem(
            id = "f17",
            name = "خرما مضافتی بم",
            category = "میوه و سبزیجات",
            portionUnit = "دو عدد دانه درشت",
            caloriesPerPortion = 45,
            carbsGrams = 11f,
            proteinGrams = 0.5f,
            fatGrams = 0.1f,
            burnMinutesWalk = 6,
            dietaryTip = "شیرین‌کننده طبیعی عالی همراه چای، حاوی منیزیم و پتاسیم."
        )
    )

    fun search(query: String, category: String = "همه"): List<IranianFoodItem> {
        val q = query.trim().lowercase()
        return foods.filter {
            val matchCategory = category == "همه" || it.category == category
            val matchText = q.isEmpty() || it.name.contains(q) || it.portionUnit.contains(q)
            matchCategory && matchText
        }
    }
}
