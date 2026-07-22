package com.example.model

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator

// Value classes are unwrapped to their underlying type, so this is documented simply as a `string`.
/** A type-safe user identifier. */
@JvmInline
@Serializable
value class UserId(val value: String)

// Sealed types become an OpenAPI `oneOf` with a discriminator; each concrete variant carries the
// discriminator (`type`) as a required, pinned property (matching what kotlinx.serialization writes
// on the wire). The discriminator name comes from @JsonClassDiscriminator / `polymorphicDiscriminator`.
/** A payment method. */
@OptIn(ExperimentalSerializationApi::class)
@Serializable
@JsonClassDiscriminator("type")
sealed interface PaymentMethod {

    @Serializable
    @SerialName("card")
    data class Card(val last4: String, val brand: String) : PaymentMethod

    @Serializable
    @SerialName("paypal")
    data class PayPal(val email: String) : PaymentMethod

    @Serializable
    @SerialName("cash")
    data object Cash : PaymentMethod
}

// Parameterized types are fully supported; the schema is named per instantiation (e.g. Page_Of_X).
/** A generic pagination envelope. */
@Serializable
data class Page<T>(
    val items: List<T>,
    val total: Int,
    val page: Int,
    val pageSize: Int,
)
