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

            createTemperament(copyMap)
            createBit(copyMap)
            createCell(copyMap)
            createFamaly(copyMap)
            createPrivichki(copyMap)

            return copyMap
        }
        return copyMap
    }

    /**
     * Считаем деградацию
     */
    fun countDegradateNumber(value: HashMap<String, String>): HashMap<String, String> {

        val copyMap: HashMap<String, String> = HashMap(value)

        if (!copyMap.isEmpty()) {

            // 8 в 1, за каждую восмерку две единицы
            if (copyMap["Долг"] != "---") {
                val countEight: Int? = copyMap["Долг"]?.length
                if (countEight != null) {
                    for (i in 0..countEight - 1) {
                        if (copyMap["Характер"] == "---") {
                            copyMap["Характер"] = ""
                        }
                        copyMap["Характер"] += "11"
                    }
                    copyMap["Долг"] = "---"
                }
            }

            // 7 в 6, за каждую восмерку две единицы
            if (copyMap["Удача"] != "---") {
                val countSeven: Int? = copyMap["Удача"]?.length
                if (countSeven != null) {
                    for (i in 0..countSeven - 1) {
                        if (copyMap["Труд"] == "---") {
                            copyMap["Труд"] = ""
                        }
                        copyMap["Труд"] += "6"
                    }
                    copyMap["Удача"] = "---"
                }
            }

            createTemperament(copyMap)
            createBit(copyMap)
            createCell(copyMap)
            createFamaly(copyMap)
            createPrivichki(copyMap)
            return copyMap
        }
        return copyMap
    }

    /**
     * Расчет диссанансов и абвивалентности
     */
    fun countDissonansAndAbivolentnost(
        commonMatrix: HashMap<String, String>,
        transformMatrix: HashMap<String, String>,
        degradateMatrix: HashMap<String, String>
    ): MutableList<Int> {
        val result: MutableList<Int> = mutableListOf()


        //В секторе Логика стоит 5, 55, 555, 5555, 55555 (любой из показателей) + в секторе Память одна 9 или сектор пуст.
        if (!commonMatrix["Логика"].equals("---") && (commonMatrix["Память"].equals("9") || commonMatrix["Память"].equals(
                "---"
            ))
        ) {
            result.add(1)
        }

        //В секторе Здоровье стоит 44, 444, 4444, 44444 + в секторе Память одна 9 или сектор пуст
        if ((!commonMatrix["Здоровье"].equals("---") || !commonMatrix["Здоровье"].equals("4")) &&
            (commonMatrix["Память"].equals("9") || commonMatrix["Память"].equals("---"))
        ) {
            result.add(2)
        }

        //В секторе Долг стоит 88 или 888 + в секторе Память 9 или сектор пуст
        if (commonMatrix["Долг"].equals("88") || (commonMatrix["Долг"].equals("888")) &&
            (commonMatrix["Память"].equals("9") || commonMatrix["Память"].equals("---"))
        ) {
            result.add(3)
        }

        //В секторе Память 9 или сектор пуст + в секторе Логика 55, 555, 5555, 55555 + в секторе Семья 4, 5, 6, 7, 8, 9
        if ((!commonMatrix["Логика"].equals("---") || !commonMatrix["Логика"].equals("5")) &&
            (commonMatrix["Память"].equals("9") || commonMatrix["Память"].equals("---"))
        ) {
            //в секторе Семья 4, 5, 6, 7, 8, 9
            if (commonMatrix["Семья"] != "---") {
                if (commonMatrix["Семья"]!!.toInt() > 3 && commonMatrix["Семья"]!!.toInt() < 10) {
                    result.add(4)
                }
            }
        }

        //В секторе Быт 3, 4, 5, 6, 7, 8, 9 + в секторе Долг 88 или 888
        if (commonMatrix["Быт"] != "0") {
            if (commonMatrix["Быт"]!!.toInt() > 2 && (commonMatrix["Долг"].equals("88") || commonMatrix["Долг"].equals(
                    "888"
                ))
            ) {
                result.add(5)
            }
        }

        //В секторе Число судьбы 4 + в секторе Семья 3
        if (commonMatrix["Число судьбы"].equals("4") && commonMatrix["Семья"].equals("3")) {
            result.add(6)
        }

        //В секторе Цель 1, 2, 3, сектор пуст  или
        // скрытая цель (если в основной матрице Цель 4, 5, 6, 7, 8, 9 - смотрим трансформацию,
        // если там Цель 1, 2, 3 - это скрытая цель СЦ) + в секторе Семья 5, 6, 7, 8, 9
        if (!commonMatrix["Семья"].equals("0") && (commonMatrix["Семья"]!!.toInt() > 4)) {
            if (commonMatrix["Цель"].equals("0") || (commonMatrix["Цель"]!!.toInt() > 0 && commonMatrix["Цель"]!!.toInt() < 4)) {
                result.add(7)
            } else if (commonMatrix["Цель"]!!.toInt() > 3) {
                //смотрим трансформацию, если там Цель 1, 2, 3 - это скрытая цель СЦ
                if (transformMatrix["Цель"]!!.toInt() in 0..3) {
                    result.add(7)
                }
            }
        }

        //В секторе Цель 6, 7, 8, 9 + в секторе Семья 1, 2, 3 или сектор пуст
        if (commonMatrix["Цель"] != "---" && commonMatrix["Цель"]!!.toInt() > 5) {
            if (commonMatrix["Семья"].equals("0") || (commonMatrix["Семья"]!!.toInt() > 0 && commonMatrix["Семья"]!!.toInt() < 4)) {
                result.add(8)
            }
        }

        //В секторе Характер 1, 11 или сектор пуст + в секторе Быт 3, 4, 5, 6, 7, 8, 9
        if (commonMatrix["Характер"].equals("1") || commonMatrix["Характер"].equals("11") || commonMatrix["Характер"].equals(
                "---"
            )
        ) {
            if (commonMatrix["Быт"]!!.toInt() > 2) {
                result.add(9)
            }
        }

        //В секторе Энергия 2 или сектор пуст + в секторе Привычки 6, 7, 8, 9
        if (commonMatrix["Энергия"].equals("---") || commonMatrix["Энергия"].equals("2")) {
            if (commonMatrix["Привычки"]!!.toInt() > 5) {
                result.add(10)
            }
        }

        //В секторе Энергия 2 или сектор пуст + в секторе Цель 6, 7, 8, 9
        if (commonMatrix["Энергия"].equals("---") || commonMatrix["Энергия"].equals("2")) {
            if (commonMatrix["Цель"]!!.toInt() > 5) {
                result.add(11)
            }
        }

        //В секторе Энергия 2 или сектор пуст + в секторе Логика 55, 555, 5555, 55555
        if (commonMatrix["Энергия"].equals("---") || commonMatrix["Энергия"].equals("2")) {
            if (commonMatrix["Логика"] != "---" && commonMatrix["Логика"] != "5") {
                result.add(12)
            }
        }

        //В секторе Энергия 2 или сектор пуст + в секторе Память 99, 999, 9999, 99999
        if (commonMatrix["Энергия"].equals("---") || commonMatrix["Энергия"].equals("2")) {
            if (commonMatrix["Память"] != "---" && commonMatrix["Память"] != "9") {
                result.add(13)
            }
        }

        //В секторе Семья 1, 2, 3 или пуст + с секторе Быт 3, 4, 5, 6, 7, 8, 9
        if (commonMatrix["Семья"].equals("0") || (commonMatrix["Семья"]!!.toInt() > 0 && commonMatrix["Семья"]!!.toInt() < 4)) {
            if (commonMatrix["Быт"]!!.toInt() > 2) {
                result.add(14)
            }
        }

        //В секторе Цель 4 или 5 + в секторе Быт 1, 2 или пуст + в секторе Семья 1, 2, 3 или пуст
        if (commonMatrix["Цель"].equals("4") || commonMatrix["Цель"].equals("5")) {
            if (commonMatrix["Быт"].equals("1") || commonMatrix["Быт"].equals("2") || commonMatrix["Быт"].equals(
                    "0"
                )
            ) {
                if (commonMatrix["Семья"]!!.toInt() in 0..3) {
                    result.add(15)
                }
            }
        }

        //В секторе Логика 55, 555, 5555, 55555 + в секторе Интерес 33, 333, 3333, 33333
        if (!commonMatrix["Логика"].equals("---") && !commonMatrix["Логика"].equals("5")) {
            if (!commonMatrix["Интерес"].equals("---") && !commonMatrix["Интерес"].equals("3")) {
                result.add(16)
            }
        }

        //В секторе Логика 5, 55, 555, 5555, 55555 + в секторе Цель 1, 2, 3 или пуст
        if (!commonMatrix["Логика"].equals("---") && !commonMatrix["Логика"].equals("5")) {
            if (commonMatrix["Цель"]!!.toInt() in 0..3) {
                result.add(17)
            }
        }

        //В секторе Долг 88 или 888 + в секторе Цель 1, 2, 3 или пусто
        if (commonMatrix["Долг"].equals("88") && commonMatrix["Долг"].equals("888")) {
            if (commonMatrix["Цель"]!!.toInt() in 0..3) {
                result.add(18)
            }
        }

        //В секторе Труд 6, 66, 666, 6666, 66666 + в секторе Семья 1, 2, 3 или пусто
        if (!commonMatrix["Труд"].equals("---")) {
            if (commonMatrix["Семья"]!!.toInt() in 0..3) {
                result.add(19)
            }
        }

        //Число Судьбы 1 + в секторе Долг 88 или 888
        if (commonMatrix["Долг"].equals("88") || commonMatrix["Долг"].equals("888")) {
            if (commonMatrix["Число Судьбы"].equals("1")) {
                result.add(20)
            }
        }

        //В секторе Здоровье 44, 444, 4444, 44444 + в секторе Долг 88 или 888
        if (commonMatrix["Долг"].equals("88") && commonMatrix["Долг"].equals("888")) {
            if (!commonMatrix["Здоровье"].equals("---") && !commonMatrix["Здоровье"].equals("4")) {
                result.add(21)
            }
        }

        //В секторе Здоровье 4, 44, 444, 4444, 44444
        // + в секторе Память 9 или сектор пуст
        // + в секторе Долг 88 или 888
        // + В секторе Интерес 33, 333, 3333, 33333
        if (!commonMatrix["Здоровье"].equals("---")) {
            if (commonMatrix["Долг"].equals("88") || commonMatrix["Долг"].equals("888")) {
                if (commonMatrix["Память"].equals("9") || commonMatrix["Память"].equals("---")) {
                    if (!commonMatrix["Интерес"].equals("---") && commonMatrix["Интерес"].equals("3")) {
                        result.add(22)
                    }
                }
            }
        }

        //Число Судьбы 5 + в секторе Память 9 или сектор пуст
        if (commonMatrix["Число Судьбы"].equals("5")) {
            if (commonMatrix["Память"].equals("9") || commonMatrix["Память"].equals("---")) {
                result.add(23)
            }
        }

        //Число Судьбы 5 + в секторе Цель 1, 2, 3 или сектор пуст (Скрытая цель)
        if (commonMatrix["Число Судьбы"].equals("5")) {
            if (commonMatrix["Цель"]!!.toInt() in 0..3) {
                result.add(24)
            } else if (commonMatrix["Цель"]!!.toInt() > 3) {
                //смотрим трансформацию, если там Цель 1, 2, 3 - это скрытая цель СЦ
                if (transformMatrix["Цель"]!!.toInt() in 0..3) {
                    result.add(24)
                }
            }
        }

        //В секторе Темперамент 3, 4, 5, 6, 7, 8, 9 + в секторе Семья 1, 2, 3 или сектор пуст
        if (commonMatrix["Темперамент"]!!.toInt() > 2) {
            if (commonMatrix["Семья"]!!.toInt() in 0..3) {
                result.add(25)
            }
        }
//        ----------------------------Амбивалентность ----------------------------------------------

//        Интерес 3 или пуст + Логика 55, 555 и выше
        if ((commonMatrix["Интерес"].equals("---") || commonMatrix["Интерес"].equals("3")) &&
            (commonMatrix["Логика"] != "---" && commonMatrix["Логика"] != "5")
        ) {
            result.add(26)
        }

//        Интерес 3 или пуст + Быт 3, 4, 5 и выше
        if ((commonMatrix["Интерес"].equals("---") || commonMatrix["Интерес"].equals("3")) &&
            commonMatrix["Быт"]!!.toInt() > 2
        ) {
            result.add(27)
        }

//        Темперамент 1, 2 или пуст + Цель 1, 2, 3 или пуст
        if ((commonMatrix["Темперамент"]!!.toInt() in 1..2 || commonMatrix["Темперамент"].equals("---")) &&
            (commonMatrix["Цель"].equals("---") || commonMatrix["Цель"]!!.toInt() in 1..3)
        ) {
            result.add(28)
        }

//        Сектор Характер 1111 + Долг 8; или  Характер 11111 и более + Долг 8 или пуст
        if ((commonMatrix["Характер"].equals("1111") && commonMatrix["Долг"].equals("8")) ||
            (commonMatrix["Характер"]!!.length >= 5 && (commonMatrix["Долг"].equals("8") || commonMatrix["Долг"].equals(
                "---"
            )))
        ) {
            result.add(29)
        }

//        Темперамент 1/2/пуст + Энергия 2/пуст + Долг 8/пуст + Семья 1/2/3/пуст
        if ((commonMatrix["Темперамент"]!!.toInt() in 1..2 || commonMatrix["Темперамент"].equals("---")) &&
            (commonMatrix["Энергия"].equals("2") || commonMatrix["Энергия"].equals("---")) &&
            (commonMatrix["Долг"].equals("8") || commonMatrix["Долг"].equals("---")) &&
            (commonMatrix["Семья"]!!.toInt() in 0..3)
        ) {
            result.add(30)
        }

//        Темперамент 1/2/пуст + Цель 1/2/3/пуст (Скрытая Цель)
//        Долг 88/888 + Семья 4/5 и выше+ Цель 1/2/3/пуст (Скрытая Цель)
        if ((commonMatrix["Темперамент"]!!.toInt() in 1..2 || commonMatrix["Темперамент"].equals("---")) &&
            (commonMatrix["Цель"]!!.toInt() in 0..3 || transformMatrix["Цель"]!!.toInt() in 0..3)
        ) {
            result.add(31)
        } else if ((commonMatrix["Долг"].equals("88") || commonMatrix["Долг"].equals("888")) &&
            (commonMatrix["Семья"]!!.toInt() >= 4) &&
            (commonMatrix["Цель"]!!.toInt() in 0..3 || transformMatrix["Цель"]!!.toInt() in 0..3)
        ) {
            result.add(31)
        }
//        --------------------------------------------------------------------------------------------

//        Цель 1/2/3/пуст (Скрытая Цель) + Семья 1/2/3/пуст + Быт 3/4/5
        if (commonMatrix["Семья"]!!.toInt() in 0..3 && commonMatrix["Быт"]!!.toInt() in 3..5 &&
            (commonMatrix["Цель"]!!.toInt() in 0..3 || transformMatrix["Цель"]!!.toInt() in 0..3)
        ) {
            result.add(32)
        }

//        Цель 1/2/3/пуст (Скрытая Цель) + Семья 1/2/3/пуст + Здоровье 4 и выше + Труд 6 и выше
//        Цель 1/2/3/пуст (Скрытая Цель) + Быт 1/2/пуст + Здоровье 4 и выше + Труд 6 и выше
//        Быт 1/2/пуст + Семья 1/2/3/пуст + Здоровье 4 и выше + Труд 6 и выше
        if (commonMatrix["Семья"]!!.toInt() in 0..3 &&
            (!commonMatrix["Здоровье"].equals("---") && commonMatrix["Здоровье"]!!.toInt() >= 4) &&
            (!commonMatrix["Труд"].equals("---") && commonMatrix["Труд"]!!.toInt() >= 6) &&
            (commonMatrix["Цель"]!!.toInt() in 0..3 || transformMatrix["Цель"]!!.toInt() in 0..3)
        ) {
            result.add(33)
        } else if (
            commonMatrix["Быт"]!!.toInt() in 0..2 &&
            (!commonMatrix["Здоровье"].equals("---") && commonMatrix["Здоровье"]!!.toInt() >= 4) &&
            (!commonMatrix["Труд"].equals("---") && commonMatrix["Труд"]!!.toInt() >= 6) &&
            (commonMatrix["Цель"]!!.toInt() in 0..3 || transformMatrix["Цель"]!!.toInt() in 0..3)
        ) {
            result.add(33)
        } else if (
            commonMatrix["Быт"]!!.toInt() in 0..2 &&
            (!commonMatrix["Здоровье"].equals("---") && commonMatrix["Здоровье"]!!.toInt() >= 4) &&
            (!commonMatrix["Труд"].equals("---") && commonMatrix["Труд"]!!.toInt() >= 6) &&
            commonMatrix["Семья"]!!.toInt() in 0..3
        ) {
            result.add(33)
        }

//        --------------------------- Дисонансы + Амбивалентности -----------------------------------
//        Интерес 33 + Темперамент 2
        if (commonMatrix["Интерес"].equals("33") && commonMatrix["Темперамент"].equals("2")) {
            result.add(34)
        }

//        Долг 88 + Семья 2
//        Долг 888 + Семья 3
        if ((commonMatrix["Долг"].equals("88") && commonMatrix["Семья"].equals("2")) ||
            (commonMatrix["Долг"].equals("888") && commonMatrix["Семья"].equals("3"))
        ) {
            result.add(35)
        }

//        Логика 55 и выше + Память 9/пуст + Семья 4 и выше
        if ((!commonMatrix["Логика"].equals("---") && !commonMatrix["Логика"].equals("5")) &&
            (commonMatrix["Память"].equals("---") || commonMatrix["Память"].equals("9")) &&
            (commonMatrix["Семья"]!!.toInt() >= 4)
        ) {
            result.add(36)
        }
//        Семья 1/2/3/пуст + Быт 3, 4, 5 и выше
        if (commonMatrix["Семья"]!!.toInt() in 0..3 && commonMatrix["Быт"]!!.toInt() >= 3) {
            result.add(37)
        }

        return result
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
}

