package com.grupo9.easyiot.model.device

import com.grupo9.easyiot.model.routines.Meta
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

object DeviceResultSerializer : KSerializer<DeviceResult> {
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("DeviceResult") {
        element<String>("id")
        element<String>("name")
        element<Type>("type")
        element<JsonElement>("state")
        element<Meta>("meta")
    }

    override fun serialize(encoder: Encoder, value: DeviceResult) {
        val composite = encoder.beginStructure(descriptor)
        composite.encodeStringElement(descriptor, 0, value.id)
        composite.encodeStringElement(descriptor, 1, value.name)
        composite.encodeSerializableElement(descriptor, 2, Type.serializer(), value.type)
       // composite.encodeSerializableElement(descriptor, 3, StateSerializer, value.state)
        //composite.encodeStringElement(descriptor, 4, Meta.serializer(), value.meta)
        composite.endStructure(descriptor)
    }

    override fun deserialize(decoder: Decoder): DeviceResult {
        val input = decoder as? JsonDecoder ?: throw SerializationException("Expected JsonDecoder")
        val jsonObject = input.decodeJsonElement().jsonObject

        val id = jsonObject["id"]!!.jsonPrimitive.content
        val name = jsonObject["name"]!!.jsonPrimitive.content
        val type = Json.decodeFromJsonElement(Type.serializer(), jsonObject["type"]!!)
        val meta = Json.decodeFromJsonElement(Meta.serializer(),jsonObject["meta"]!!)

        val stateElement = jsonObject["state"]!!
        val state = when (type.name) {
            "lamp" -> Json.decodeFromJsonElement(State.LampState.serializer(), stateElement)
            "refrigerator" -> Json.decodeFromJsonElement(State.RefrigeratorState.serializer(), stateElement)
            "blinds" -> Json.decodeFromJsonElement(State.BlindsState.serializer(), stateElement)
            "door" -> Json.decodeFromJsonElement(State.DoorState.serializer(), stateElement)
            "faucet" -> Json.decodeFromJsonElement(State.FaucetState.serializer(), stateElement)
            "vacuum" -> Json.decodeFromJsonElement(State.VacuumState.serializer(), stateElement)
            "speaker" -> Json.decodeFromJsonElement(State.SpeakerState.serializer(), stateElement)
            else -> throw SerializationException("Unknown type: ${type.name}")
        }

        return DeviceResult(id, name, type, state, meta)
    }
}