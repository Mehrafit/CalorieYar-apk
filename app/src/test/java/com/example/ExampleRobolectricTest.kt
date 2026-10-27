package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.FoodRepository
import com.example.data.OfflineAiCoach
import com.example.data.UserPreferences
import com.example.util.PersianUtils
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

  @Test
  fun `read string from context`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("کالری‌یار", appName)
  }

  @Test
  fun `verify persian digits conversion`() {
    val result = PersianUtils.toPersianDigits("1850")
    assertEquals("۱۸۵۰", result)
  }

  @Test
  fun `verify food repository search`() {
    val searchResults = FoodRepository.search("قورمه")
    assertTrue("Should find ghormeh sabzi", searchResults.isNotEmpty())
    assertEquals("چلو قورمه‌سبزی با گوشت گوسفندی", searchResults.first().name)
  }

  @Test
  fun `verify offline AI diet coach reasoning`() {
    val query = "ظهر دو سیخ کباب کوبیده با برنج خوردم"
    val result = OfflineAiCoach.analyzeMealOrDiet(query)
    assertNotNull(result)
    assertTrue("Should estimate calories", result.estimatedCalories > 500)
    assertTrue("Should have alternatives", result.practicalAlternatives.isNotEmpty())
  }
}
