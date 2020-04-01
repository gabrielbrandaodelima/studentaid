package br.com.ufop.studentaid.core.extensions


fun String.Companion.empty() = ""


fun String.capitalizeWords(): String {
    return if (isNotEmpty() && this[0].isLowerCase()) {

        val words = this.split(" ")
        val capWords = arrayListOf<String>()
        for (word in words) {
            if (word.isNotEmpty())
                capWords.add(word.substring(0, 1).toUpperCase() + word.substring(1))
        }
        var final = String.empty()
        capWords.forEach {
            final += it.plus(" ")
        }
        final
    } else this
}