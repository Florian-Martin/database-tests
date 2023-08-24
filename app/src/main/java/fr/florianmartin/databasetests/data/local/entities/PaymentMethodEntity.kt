package fr.florianmartin.databasetests.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "payment_method")
data class PaymentMethodEntity(
    @PrimaryKey
    val pmId: Int,
    val name: String,
)
