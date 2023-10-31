package com.zelianko.numerologic.services

import kotlin.math.abs

class CountNumberServices {

    fun countNumber(str: String): HashMap<String, String> {
        val hashMap: HashMap<String, String> = HashMap()
        if (str.isNotEmpty()) {
            val split = str.split("/")
            val day = split.get(0)
            val month = split.get(1)
            val years = split.get(2)


            hashMap["Доп.цифры"] = firstDopNumber(day, month, years)

            createAllMatrix(day, month, years, hashMap)

            createTemperament(hashMap)
            createBit(hashMap)
            createCell(hashMap)
            createFamaly(hashMap)
            createPrivichki(hashMap)
            createSudba(day, month, years, hashMap)
        }
        return hashMap;
    }

    /**
     * Считаем трансформацию
     */
    fun countTransformNumber(value: HashMap<String, String>): HashMap<String, String> {

        val copyMap: HashMap<String, String> = HashMap(value)

        if (!copyMap.isEmpty()) {
            if (copyMap["Характер"] != "---" && copyMap["Характер"]?.length!! > 2) {
                if (copyMap["Характер"]?.length!! % 2 == 0) {
                    //Кол-во весмерок для добавления
                    val countEight = (copyMap["Характер"]?.length!! - 2) / 2
                    var eight = copyMap["Долг"]
                    for (i in 0..countEight - 1) {
                        if (eight == "---") {
                            eight = ""
                        }
                        eight += "8"
                    }
                    copyMap["Долг"] = eight.toString()
                    copyMap["Характер"] = "11"
                } else {
                    //Кол-во весмерок для добавления
                    val countEight = (copyMap["Характер"]?.length!! - 1) / 2
                    var eight = copyMap["Долг"]
                    for (i in 0..countEight - 1) {
                        if (eight == "---") {
                            eight = ""
                        }
                        eight += "8"
                    }
                    copyMap["Характер"] = "1"
                    copyMap["Долг"] = eight.toString()
                }
            }

            var six = copyMap["Труд"]
            var seven = copyMap["Удача"]
            if (copyMap["Труд"] != "---") {
                for (i in 0..(six?.length?.minus(1) ?: 0)) {
                    if (seven == "---") {
                        seven = ""
                    }
                    seven += "7"
                }
                six = "---"
                copyMap["Труд"] = six
                copyMap["Удача"] = seven.toString()
            }
            return copyMap
        }
        return copyMap
    }
}

/**
 * Формирование первого доп числа
 */
private fun firstDopNumber(day: String, month: String, years: String): String {
    val result = StringBuilder()

    val daysArray = day.map { it.toString().toInt() }
    val monthArray = month.map { it.toString().toInt() }
    val yearsArray = years.map { it.toString().toInt() }

    val sumDay = daysArray.sum()
    val sumMonth = monthArray.sum()
    val sumYears = yearsArray.sum()

    val oneNumber = sumDay + sumMonth + sumYears
    var twoNumber = oneNumber.toString().map { it.toString().toInt() }.sum()

    val thirdNumber = abs(oneNumber - 2 * daysArray[0])

    var fourthNumber = thirdNumber.toString().map { it.toString().toInt() }.sum()

    if (oneNumber.toString().length == 1 && thirdNumber.toString().length == 1) {
        twoNumber = oneNumber
        fourthNumber = thirdNumber
    }

    result.append(oneNumber).append(",")
        .append(twoNumber).append(",")
        .append(thirdNumber).append(",")
        .append(fourthNumber)

    return result.toString()
}

/**
 *Используя все дополнительные числа и числа даты рождения (подчеркнуто), заполняем ими секторы матрицы
 *  с соответствующими значениями: все “1”, которые есть  в полученных числах, ставите в сектор “1”,
 *  все “2”, которые получились в доп числах и есть дате рождения в сектор “2” и тд.
 *  Если каких-то чисел просто нет, значит, сектор этого числа будет пуст.
 */
private fun createAllMatrix(
    day: String,
    month: String,
    years: String,
    hashMap: HashMap<String, String>
) {
    val list = ArrayList<Int>()

    val countNumber = HashMap<Int, Int>()

    countNumber[1] = 0
    countNumber[2] = 0
    countNumber[3] = 0
    countNumber[4] = 0
    countNumber[5] = 0
    countNumber[6] = 0
    countNumber[7] = 0
    countNumber[8] = 0
    countNumber[9] = 0

    val dopNumber = hashMap["Доп.цифры"]

    list.addAll(day.map { it.toString().toInt() })
    list.addAll(month.map { it.toString().toInt() })
    list.addAll(years.map { it.toString().toInt() })
    if (dopNumber != null) {

        val temp = dopNumber.split(",")
        for (s in temp) {
            list.addAll(s.map { it.toString().toInt() })
        }
    }

    for (i in list) {
        countNumber.computeIfPresent(i) { _, v -> v + 1 }
    }

    for ((key, value) in countNumber) {
        if (key == 1) {
            if (value == 0) {
                hashMap["Характер"] = "---"
            } else {
                hashMap["Характер"] = "1".repeat(value)
            }
        } else if (key == 2) {
            if (value == 0) {
                hashMap["Энергия"] = "---"
            } else {
                hashMap["Энергия"] = "2".repeat(value)
            }
        } else if (key == 3) {
            if (value == 0) {
                hashMap["Интерес"] = "---"
            } else {
                hashMap["Интерес"] = "3".repeat(value)
            }
        } else if (key == 4) {
            if (value == 0) {
                hashMap["Здоровье"] = "---"
            } else {
                hashMap["Здоровье"] = "4".repeat(value)
            }
        } else if (key == 5) {
            if (value == 0) {
                hashMap["Логика"] = "---"
            } else {
                hashMap["Логика"] = "5".repeat(value)
            }
        } else if (key == 6) {
            if (value == 0) {
                hashMap["Труд"] = "---"
            } else {
                hashMap["Труд"] = "6".repeat(value)
            }
        } else if (key == 7) {
            if (value == 0) {
                hashMap["Удача"] = "---"
            } else {
                hashMap["Удача"] = "7".repeat(value)
            }
        } else if (key == 8) {
            if (value == 0) {
                hashMap["Долг"] = "---"
            } else {
                hashMap["Долг"] = "8".repeat(value)
            }
        } else if (key == 9) {
            if (value == 0) {
                hashMap["Память"] = "---"
            } else {
                hashMap["Память"] = "9".repeat(value)
            }
        }
    }
}

/**
 * Темперамент
 */
private fun createTemperament(hashMap: HashMap<String, String>) {
    val tempString = hashMap["Удача"] + hashMap["Логика"] + hashMap["Интерес"]
    hashMap["Темперамент"] = tempString.replace("-", "").length.toString()
}

/**
 * Быт
 */
private fun createBit(hashMap: HashMap<String, String>) {
    val tempString = hashMap["Здоровье"] + hashMap["Логика"] + hashMap["Труд"]
    hashMap["Быт"] = tempString.replace("-", "").length.toString()
}

/**
 * Цель
 */
private fun createCell(hashMap: HashMap<String, String>) {
    val tempString = hashMap["Характер"] + hashMap["Здоровье"] + hashMap["Удача"]
    hashMap["Цель"] = tempString.replace("-", "").length.toString()
}

/**
 * Семья
 */
private fun createFamaly(hashMap: HashMap<String, String>) {
    val tempString = hashMap["Энергия"] + hashMap["Логика"] + hashMap["Долг"]
    hashMap["Семья"] = tempString.replace("-", "").length.toString()
}

/**
 * Привычки
 */
private fun createPrivichki(hashMap: HashMap<String, String>) {
    val tempString = hashMap["Интерес"] + hashMap["Труд"] + hashMap["Память"]
    hashMap["Привычки"] = tempString.replace("-", "").length.toString()
}

private fun createSudba(
    day: String,
    month: String,
    years: String,
    hashMap: HashMap<String, String>
) {

    val daysArray = day.map { it.toString().toInt() }
    val monthArray = month.map { it.toString().toInt() }
    val yearsArray = years.map { it.toString().toInt() }

    val sumDay = daysArray.sum()
    val sumMonth = monthArray.sum()
    val sumYears = yearsArray.sum()

    var oneNumber = sumDay + sumMonth + sumYears
    var twoNumber = oneNumber.toString().map { it.toString().toInt() }.sum()

    if (twoNumber != 11) {
        while (twoNumber.toString().length > 1) {
            twoNumber = twoNumber.toString().map { it.toString().toInt() }.sum()
            if (twoNumber == 11) {
                break
            }
        }
    }

    hashMap["Число судьбы"] = twoNumber.toString()
}

