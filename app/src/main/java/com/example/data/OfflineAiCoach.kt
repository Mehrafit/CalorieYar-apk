package com.example.data

import com.example.model.AiDietConsultation
import java.util.UUID
import kotlin.random.Random

object OfflineAiCoach {

    fun analyzeMealOrDiet(userPrompt: String): AiDietConsultation {
        val q = userPrompt.lowercase().trim()

        val estimatedCalories: Int
        val dietVerdict: String
        val analysisText: String
        val practicalAlternatives: List<String>
        val workoutBurnTip: String
        val healthScore: Int

        when {
            q.contains("کباب") || q.contains("کوبیده") || q.contains("برگر") || q.contains("فست‌فود") || q.contains("پیتزا") -> {
                estimatedCalories = Random.nextInt(750, 980)
                dietVerdict = "پرکالری و چربی بالا (نیاز به مدیریت وعده بعدی)"
                healthScore = 58
                analysisText = "این وعده پروتئین خوبی برای عضلات دارد اما حجم چربی اشباع و سدیم آن بالاست که باعث احتباس آب در بافت‌ها می‌شود. برای متعادل‌سازی، وعده بعدی باید بر پایه فیبر و سبزیجات تازه باشد."
                practicalAlternatives = listOf(
                    "برای وعده شام از سوپ سبزیجات بدون خامه یا ماست و خیار با گردو استفاده کنید.",
                    "حداقل ۲ لیوان آب ولرم همراه با چند قطره لیموترش تازه بنوشید تا سدیم اضافی دفع شود.",
                    "نان یا برنج را در وعده بعد کاملاً حذف کنید تا سطح انسولین خون پایین بیاید."
                )
                workoutBurnTip = "برای سوزاندن کالری اضافی این وعده، ۴۵ دقیقه پیاده‌روی تند یا ۲۰ دقیقه طناب زدن توصیه می‌شود."
            }
            q.contains("قورمه") || q.contains("قیمه") || q.contains("فسنجان") || q.contains("دیزی") || q.contains("آبگوشت") -> {
                estimatedCalories = Random.nextInt(600, 780)
                dietVerdict = "غذای سنتی غنی (کالری متوسط رو به بالا)"
                healthScore = 72
                analysisText = "غذاهای سنتی و خورش‌های اصیل ایرانی حاوی آهن و روی فراوان هستند. نکته کلیدی در رژیم لاغری ایرانی، کنترل روغن تفت‌دادن سبزی و مقدار کفگیرهای برنج سفید است."
                practicalAlternatives = listOf(
                    "همراه خورش به جای برنج چرب، سالاد شیرازی یا سبزی‌خوردن فراوان میل کنید.",
                    "روغن زرد یا دنبه روی خورش را با قاشق جدا کنید تا ۱۸۰ کالری کمتر دریافت کنید.",
                    "دوغ سنتی کم‌نمک را جایگزین هر نوع نوشیدنی شیرین نمایید."
                )
                workoutBurnTip = "۳۵ دقیقه پیاده‌روی معمولی در هوای آزاد، این انرژی را به چرخه سوخت‌وساز مفید تبدیل می‌کند."
            }
            q.contains("شیرینی") || q.contains("کیک") || q.contains("شکلات") || q.contains("بستنی") || q.contains("قند") -> {
                estimatedCalories = Random.nextInt(350, 550)
                dietVerdict = "قند ساده بالا (خطر انباشت چربی شکمی)"
                healthScore = 45
                analysisText = "قندهای ساده باعث افت و خیز شدید قند خون و هوس غذایی مجدد پس از ۲ ساعت می‌شوند. مصرف این کالری خالی در روند چربی‌سوزی وقفه ایجاد می‌کند."
                practicalAlternatives = listOf(
                    "هنگام هوس شیرینی، ۲ عدد خرمای مضافتی با گردو یا یک قاشق مرباخوری عسل میل کنید.",
                    "یک فنجان چای سبز یا دمنوش دارچین بنوشید تا حساسیت به انسولین تقویت شود.",
                    "یک پیاله ماست ایسلندی پروتئینی با تکه‌های توت یا سیب تهیه کنید."
                )
                workoutBurnTip = "۲۵ دقیقه ورزش هوازی (کاردیو) در منزل یا بالا رفتن از پله‌ها برای بازگرداندن قند خون به سطح پایه."
            }
            q.contains("مرغ") || q.contains("تخم") || q.contains("ماهی") || q.contains("سالاد") || q.contains("رژیم") || q.contains("سبزی") -> {
                estimatedCalories = Random.nextInt(280, 420)
                dietVerdict = "بسیار عالی، سلامت‌محور و چربی‌سوز"
                healthScore = 94
                analysisText = "انتخابی فوق‌العاده هوشمندانه! این ترکیب سرشار از پروتئین باکیفیت و فیبر است که سرعت متابولیسم را تا ۳۰ درصد بالا برده و احساس سیری ماندگار ایجاد می‌کند."
                practicalAlternatives = listOf(
                    "یک قاشق چایخوری روغن زیتون فرابکر برای جذب ویتامین‌های محلول در چربی بیفزایید.",
                    "به عنوان میان‌وعده عصر، ۱۰ عدد بادام خام درختی اضافه کنید.",
                    "همین ریتم غذایی تمیز را برای روزهای آتی حفظ کنید."
                )
                workoutBurnTip = "این وعده انرژی پایداری به شما می‌دهد و برای یک جلسه تمرین قدرتی یا پیاده‌روی صبحگاهی ایده‌آل است."
            }
            else -> {
                estimatedCalories = Random.nextInt(400, 580)
                dietVerdict = "تغذیه نیازمند توازن و پایش"
                healthScore = 78
                analysisText = "برای رسیدن به وزن ایده‌آل پایدار، نیازی به گرسنگی کشیدن نیست؛ بلکه هنر در جایگزین کردن غذاهای حجیم و کم‌کالری ایرانی به جای خوراکی‌های فشرده و پرکالری است."
                practicalAlternatives = listOf(
                    "بشقاب غذای خود را طبق قانون ۵۰٪ سبزیجات، ۲۵٪ پروتئین و ۲۵٪ غلات کامل بچینید.",
                    "قبل از شروع غذا یک لیوان آب بنوشید و غذا را آهسته (حداقل ۲۰ دقیقه) بجوید.",
                    "شام را حداقل ۳ ساعت قبل از خواب به اتمام برسانید."
                )
                workoutBurnTip = "روزی دست‌کم ۸ هزار قدم پیاده‌روی تند برای حفظ کسری کالری و تثبیت وزن الزامی است."
            }
        }

        return AiDietConsultation(
            id = UUID.randomUUID().toString(),
            userMealDescription = userPrompt,
            estimatedCalories = estimatedCalories,
            dietVerdict = dietVerdict,
            analysisText = analysisText,
            practicalAlternatives = practicalAlternatives,
            workoutBurnTip = workoutBurnTip,
            healthScore = healthScore
        )
    }
}
