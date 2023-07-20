package fr.florianmartin.databasetests.utils


fun String.shuffle(): String {
    val temp = this.toList().shuffled()
    return temp.joinToString("")
}