package com.example.model

data class SampleResponse(
    val int: Int = 0,
    val string: String = "",
    val double: Double = 0.0,
    val objectRef: ObjectRef = ObjectRef(),
    val list: List<String> = emptyList(),
    val setObjectRefs: Set<ObjectRef> = emptySet(),
    val map: Map<String, ObjectRef> = mapOf()
) {
    companion object {
        fun fromRequest(request: SampleRequest): SampleResponse = SampleResponse(
            int = request.int,
            string = request.string,
            double = request.double,
            objectRef = request.objectRef,
            list = request.list,
            setObjectRefs = request.setObjectRefs,
            map = request.map
        )
    }
}

data class SampleErrorResponse(
    val errorCode: Int, val errorMessage: String
)
