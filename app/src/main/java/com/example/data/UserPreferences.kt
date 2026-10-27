package com.example.data

import android.content.Context
import android.content.SharedPreferences
import com.example.model.IranianFoodItem
import com.example.model.LoggedMeal
import com.example.model.MealType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.UUID

class UserPreferences(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences("calorie_yar_prefs", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_COINS = "user_coins"
        private const val KEY_IS_VIP = "is_vip_member"
        private const val KEY_LAST_SPIN_TIME = "last_spin_time_millis"
        private const val KEY_FIRST_LAUNCH = "is_first_launch"
        private const val KEY_WATER_GLASSES = "water_glasses_today"
        private const val KEY_WEIGHT_KG = "user_weight_kg"
        private const val KEY_TARGET_WEIGHT_KG = "user_target_weight_kg"
        private const val KEY_HEIGHT_CM = "user_height_cm"
        private const val KEY_DAILY_CALORIE_GOAL = "daily_calorie_goal"

        const val WHEEL_COOLDOWN_MILLIS = 24 * 60 * 60 * 1000L // 24 hours
        const val COST_AI_MEAL_ANALYSIS = 20
        const val COST_CUSTOM_DIET_PLAN = 30
    }

    private val _coinsFlow = MutableStateFlow(getCoins())
    val coinsFlow: StateFlow<Int> = _coinsFlow.asStateFlow()

    private val _isVipFlow = MutableStateFlow(isVip())
    val isVipFlow: StateFlow<Boolean> = _isVipFlow.asStateFlow()

    private val _waterFlow = MutableStateFlow(getWaterGlasses())
    val waterFlow: StateFlow<Int> = _waterFlow.asStateFlow()

    // In-memory list of meals logged today
    val todayMeals = mutableListOf<LoggedMeal>()
    private val _consumedCaloriesFlow = MutableStateFlow(0)
    val consumedCaloriesFlow: StateFlow<Int> = _consumedCaloriesFlow.asStateFlow()

    init {
        if (prefs.getBoolean(KEY_FIRST_LAUNCH, true)) {
            prefs.edit()
                .putBoolean(KEY_FIRST_LAUNCH, false)
                .putInt(KEY_COINS, 200) // 200 welcome coins!
                .putInt(KEY_WATER_GLASSES, 4)
                .putFloat(KEY_WEIGHT_KG, 76.5f)
                .putFloat(KEY_TARGET_WEIGHT_KG, 68.0f)
                .putInt(KEY_HEIGHT_CM, 174)
                .putInt(KEY_DAILY_CALORIE_GOAL, 1850)
                .apply()
            _coinsFlow.value = 200
            _waterFlow.value = 4
        }

        // Pre-populate 2 initial realistic meals for day 1 so user immediately sees populated stats!
        val breakfast = FoodRepository.foods.find { it.id == "f10" } ?: FoodRepository.foods[9]
        val egg = FoodRepository.foods.find { it.id == "f14" } ?: FoodRepository.foods[13]
        logMeal(breakfast, MealType.BREAKFAST, 2f) // 2 kaf-e dast sangak (150 cal)
        logMeal(egg, MealType.BREAKFAST, 2f) // 2 boiled eggs (156 cal)
    }

    fun getCoins(): Int = prefs.getInt(KEY_COINS, 200)

    fun isVip(): Boolean = prefs.getBoolean(KEY_IS_VIP, false)

    fun addCoins(amount: Int) {
        val current = getCoins()
        val updated = current + amount
        prefs.edit().putInt(KEY_COINS, updated).apply()
        _coinsFlow.value = updated
    }

    fun spendCoins(cost: Int): Boolean {
        if (isVip()) return true
        val current = getCoins()
        if (current >= cost) {
            val updated = current - cost
            prefs.edit().putInt(KEY_COINS, updated).apply()
            _coinsFlow.value = updated
            return true
        }
        return false
    }

    fun setVip(active: Boolean) {
        prefs.edit().putBoolean(KEY_IS_VIP, active).apply()
        _isVipFlow.value = active
    }

    fun getLastSpinTime(): Long = prefs.getLong(KEY_LAST_SPIN_TIME, 0L)

    fun recordLuckySpin(): Long {
        val now = System.currentTimeMillis()
        prefs.edit().putLong(KEY_LAST_SPIN_TIME, now).apply()
        return now
    }

    fun getRemainingSpinCooldown(): Long {
        val lastSpin = getLastSpinTime()
        if (lastSpin == 0L) return 0L
        val elapsed = System.currentTimeMillis() - lastSpin
        val remaining = WHEEL_COOLDOWN_MILLIS - elapsed
        return if (remaining > 0L) remaining else 0L
    }

    // Water intake
    fun getWaterGlasses(): Int = prefs.getInt(KEY_WATER_GLASSES, 4)

    fun addWaterGlass() {
        val current = getWaterGlasses()
        val updated = (current + 1).coerceAtMost(12)
        prefs.edit().putInt(KEY_WATER_GLASSES, updated).apply()
        _waterFlow.value = updated
    }

    fun removeWaterGlass() {
        val current = getWaterGlasses()
        val updated = (current - 1).coerceAtLeast(0)
        prefs.edit().putInt(KEY_WATER_GLASSES, updated).apply()
        _waterFlow.value = updated
    }

    // Meal Logging
    fun logMeal(food: IranianFoodItem, mealType: MealType, portions: Float = 1.0f) {
        val entry = LoggedMeal(
            id = UUID.randomUUID().toString(),
            food = food,
            mealType = mealType,
            portions = portions
        )
        todayMeals.add(0, entry)
        recomputeCalories()
    }

    fun removeMeal(mealId: String) {
        todayMeals.removeAll { it.id == mealId }
        recomputeCalories()
    }

    private fun recomputeCalories() {
        val total = todayMeals.sumOf { it.totalCalories }
        _consumedCaloriesFlow.value = total
    }

    // Fitness goals
    fun getWeight(): Float = prefs.getFloat(KEY_WEIGHT_KG, 76.5f)
    fun getTargetWeight(): Float = prefs.getFloat(KEY_TARGET_WEIGHT_KG, 68.0f)
    fun getHeight(): Int = prefs.getInt(KEY_HEIGHT_CM, 174)
    fun getDailyCalorieGoal(): Int = prefs.getInt(KEY_DAILY_CALORIE_GOAL, 1850)

    fun updateProfile(weight: Float, targetWeight: Float, height: Int, calorieGoal: Int) {
        prefs.edit()
            .putFloat(KEY_WEIGHT_KG, weight)
            .putFloat(KEY_TARGET_WEIGHT_KG, targetWeight)
            .putInt(KEY_HEIGHT_CM, height)
            .putInt(KEY_DAILY_CALORIE_GOAL, calorieGoal)
            .apply()
    }
}
