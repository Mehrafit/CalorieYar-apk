package com.example.util

import java.util.Calendar

object PersianUtils {

    /**
     * Converts any ASCII digits in the string to standard Persian digits (۰-۹).
     */
    fun toPersianDigits(text: String): String {
        val persianDigits = charArrayOf('۰', '۱', '۲', '۳', '۴', '۵', '۶', '۷', '۸', '۹')
        val builder = StringBuilder()
        for (ch in text) {
            if (ch in '0'..'9') {
                builder.append(persianDigits[ch - '0'])
            } else {
                builder.append(ch)
            }
        }
        return builder.toString()
    }

    fun Number.toPersian(): String = toPersianDigits(this.toString())

    /**
     * Format price with Persian thousands separator (e.g. ۱۲,۰۰۰ تومان)
     */
    fun formatToman(amount: Long): String {
        val formatted = String.format("%,d", amount).replace(',', '،')
        return "${toPersianDigits(formatted)} تومان"
    }

    /**
     * Jalali Date representation.
     */
    data class JalaliDate(
        val year: Int,
        val month: Int,
        val day: Int,
        val monthName: String,
        val dayOfWeekName: String
    ) {
        fun formatFull(): String {
            return "$dayOfWeekName، ${day.toPersian()} $monthName ${year.toPersian()}"
        }

        fun formatNumeric(): String {
            val mStr = toPersianDigits(month.toString().padStart(2, '0'))
            val dStr = toPersianDigits(day.toString().padStart(2, '0'))
            return "${year.toPersian()}/$mStr/$dStr"
        }
    }

    private val persianMonths = listOf(
        "فروردین", "اردیبهشت", "خرداد",
        "تیر", "مرداد", "شهریور",
        "مهر", "آبان", "آذر",
        "دی", "بهمن", "اسفند"
    )

    private val persianWeekdays = listOf(
        "یکشنبه", "دوشنبه", "سه‌شنبه", "چهارشنبه", "پنج‌شنبه", "جمعه", "شنبه"
    )

    /**
     * Converts a Gregorian Date to Jalali (Solar Hijri) Date.
     */
    fun getTodayJalali(calendar: Calendar = Calendar.getInstance()): JalaliDate {
        val gYear = calendar.get(Calendar.YEAR)
        val gMonth = calendar.get(Calendar.MONTH) + 1
        val gDay = calendar.get(Calendar.DAY_OF_MONTH)
        val dayOfWeekIdx = calendar.get(Calendar.DAY_OF_WEEK) - 1 // Calendar.SUNDAY is 1

        val (jYear, jMonth, jDay) = gregorianToJalali(gYear, gMonth, gDay)
        val monthName = persianMonths.getOrElse(jMonth - 1) { "نامشخص" }
        val dayOfWeekName = persianWeekdays.getOrElse(dayOfWeekIdx) { "شنبه" }

        return JalaliDate(jYear, jMonth, jDay, monthName, dayOfWeekName)
    }

    /**
     * Standard Gregorian to Jalali algorithm.
     */
    fun gregorianToJalali(gy: Int, gm: Int, gd: Int): Triple<Int, Int, Int> {
        val gDaysInMonth = intArrayOf(0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31)
        var gy2 = if (gm > 2) gy + 1 else gy
        val gLeap = (gy % 4 == 0 && gy % 100 != 0) || (gy % 400 == 0)
        if (gLeap) gDaysInMonth[2] = 29

        var gDayCount = 0
        for (i in 1 until gm) {
            gDayCount += gDaysInMonth[i]
        }
        gDayCount += gd

        val gDays = 365 * gy + ((gy2 + 3) / 4) - ((gy2 + 99) / 100) + ((gy2 + 399) / 400) - 80 + gDayCount
        var jy = -1595 + 33 * (gDays / 12053)
        var daysLeft = gDays % 12053
        jy += 4 * (daysLeft / 1461)
        daysLeft %= 1461

        if (daysLeft > 365) {
            jy += (daysLeft - 1) / 365
            daysLeft = (daysLeft - 1) % 365
        }

        val jm: Int
        val jd: Int
        if (daysLeft < 186) {
            jm = 1 + (daysLeft / 31)
            jd = 1 + (daysLeft % 31)
        } else {
            jm = 7 + ((daysLeft - 186) / 30)
            jd = 1 + ((daysLeft - 186) % 30)
        }

        return Triple(jy, jm, jd)
    }

    /**
     * Format milliseconds remaining to HH:MM:SS in Persian digits.
     */
    fun formatRemainingTime(millis: Long): String {
        if (millis <= 0) return "آماده چرخش!"
        val seconds = (millis / 1000) % 60
        val minutes = (millis / (1000 * 60)) % 60
        val hours = (millis / (1000 * 60 * 60))

        val text = String.format("%02d:%02d:%02d", hours, minutes, seconds)
        return toPersianDigits(text)
    }
}
