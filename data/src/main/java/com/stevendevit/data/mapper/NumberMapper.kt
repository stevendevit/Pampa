package com.stevendevit.data.mapper

import com.stevendevit.domain.mapper.Mapper

/**
 * Created by stevendevit on 05/01/2020.
 * tankadeveloper@gmail.com
 */
class NumberMapper : Mapper<String, Int>() {

    override lateinit var dictionary: HashMap<String, Int>

    private lateinit var numDictionary: HashMap<Int, Int>

    init {
        instance = this
    }

    override fun load() {

        numDictionary = HashMap()
        dictionary = HashMap()

        dictionary["uno"] = 1
        dictionary["dos"] = 2
        dictionary["tres"] = 3
        dictionary["cuatro"] = 4
        dictionary["cinco"] = 5
        dictionary["seis"] = 6
        dictionary["siete"] = 7
        dictionary["ocho"] = 8
        dictionary["nueve"] = 9
        dictionary["diez"] = 10
        dictionary["cero"] = 0

        numDictionary[1] = 1
        numDictionary[2] = 2
        numDictionary[3] = 3
        numDictionary[4] = 4
        numDictionary[5] = 5
        numDictionary[6] = 6
        numDictionary[7] = 7
        numDictionary[8] = 8
        numDictionary[9] = 9
        numDictionary[10] = 10
        numDictionary[0] = 0
    }

    override fun from(key: String): Int {

        val firstTry = dictionary.getOrElse(key, {
            return -1
        })

        if (firstTry != -1) {

            return firstTry
        }

        val secondTry = numDictionary.getOrElse(key.toIntOrNull() ?: 0, {
            return -1
        })

        return if (secondTry != 1) secondTry else 0
    }

    companion object {

        lateinit var instance: NumberMapper
            private set
    }
}