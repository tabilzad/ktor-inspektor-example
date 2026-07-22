package com.example.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// Descriptions on this type come from KDoc (`useKDocsForDescriptions = true`) rather than annotations.
/** Standard response payload. */
@Serializable
data class SampleResponse(
    /** An integer value. */
    val int: Int = 0,
    val string: String = "",
    val double: Double = 0.0,
    val objectRef: ObjectRef = ObjectRef(),
    val list: List<String> = emptyList(),
    /** Serialized under a custom JSON name via @SerialName. */
    @SerialName("objects")
    val setObjectRefs: Set<ObjectRef> = emptySet(),
    val map: Map<String, ObjectRef> = mapOf(),
    /** Current lifecycle status. */
    val status: OrderStatus = OrderStatus.PENDING,
) {
    companion object {
        fun fromRequest(request: SampleRequest): SampleResponse = SampleResponse(
            int = request.int,
            string = request.string,
            double = request.double,
            objectRef = request.objectRef,
            list = request.list,
            setObjectRefs = request.setObjectRefs,
            map = request.map,
        )
    }
}

/** A consistent error envelope returned by failing endpoints. */
@Serializable
data class SampleErrorResponse(
    /** Machine-readable error code. */
    val errorCode: Int,
    /** Human-readable error message. */
    val errorMessage: String,
)

/** Lifecycle status of an order — enums are rendered as a string schema with an `enum` list. */
@Serializable
enum class OrderStatus { PENDING, SHIPPED, DELIVERED, CANCELLED }
