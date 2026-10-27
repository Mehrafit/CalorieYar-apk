package com.example.model

import androidx.compose.ui.graphics.Color

enum class MealType(val title: String) {
    BREAKFAST("صبحانه"),
    LUNCH("ناهار"),
    DINNER("شام"),
    SNACK("میان‌وعده")
}

data class IranianFoodItem(
    val id: String,
    val name: String,
    val category: String,
    val portionUnit: String,
    val caloriesPerPortion: Int,
    val carbsGrams: Float,
    val proteinGrams: Float,
    val fatGrams: Float,
    val burnMinutesWalk: Int,
    val dietaryTip: String
)

data class LoggedMeal(
    val id: String,
    val food: IranianFoodItem,
    val mealType: MealType,
    val portions: Float = 1.0f,
    val timestamp: Long = System.currentTimeMillis()
) {
    val totalCalories: Int get() = (food.caloriesPerPortion * portions).toInt()
    val totalProtein: Float get() = food.proteinGrams * portions
    val totalCarbs: Float get() = food.carbsGrams * portions
    val totalFat: Float get() = food.fatGrams * portions
}

data class UserFitnessProfile(
    val weightKg: Float = 75f,
    val targetWeightKg: Float = 68f,
    val heightCm: Int = 175,
    val age: Int = 28,
    val isMale: Boolean = true,
    val activityLevel: String = "متوسط (۳ روز ورزش در هفته)",
    val dailyCalorieGoal: Int = 1850,
    val waterGlasses: Int = 5
) {
    val bmi: Float get() = weightKg / ((heightCm / 100f) * (heightCm / 100f))
    val bmiCategory: String get() = when {
        bmi < 18.5f -> "کمبود وزن"
        bmi < 24.9f -> "وزن نرمال و سلامت"
        bmi < 29.9f -> "اضافه وزن خفیف"
        else -> "چاقی نیازمند رژیم"
    }
}

data class AiDietConsultation(
    val id: String,
    val userMealDescription: String,
    val estimatedCalories: Int,
    val dietVerdict: String,
    val analysisText: String,
    val practicalAlternatives: List<String>,
    val workoutBurnTip: String,
    val healthScore: Int
)

data class WheelSlice(
    val id: Int,
    val title: String,
    val coins: Int,
    val primaryColor: Color,
    val secondaryColor: Color,
    val isJackpot: Boolean = false
)

data class StorePackage(
    val id: String,
    val title: String,
    val coins: Int,
    val priceToman: Long,
    val discountPercent: Int = 0,
    val isPopular: Boolean = false,
    val isVIP: Boolean = false,
    val description: String
)
