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
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

object DeviceResultSerializer : KSerializer<DeviceResult> {
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("DeviceResult") {
        element<String>("id")
        element<String>("name")
        element<Type>("type")
        element<State>("state")
        element<Meta>("meta")
    }

    override fun serialize(encoder: Encoder, value: DeviceResult) {
        val composite = encoder.beginStructure(descriptor)
        composite.encodeStringElement(descriptor, 0, value.id)
        composite.encodeStringElement(descriptor, 1, value.name)
        composite.encodeSerializableElement(descriptor, 2, Type.serializer(), value.type)
        when(value.type.name){
            "lamp" -> composite.encodeSerializableElement(descriptor, 3, State.LampState.serializer(),
                value.state as State.LampState
            )
            "refrigerator" -> composite.encodeSerializableElement(descriptor, 3, State.RefrigeratorState.serializer(),
                value.state as State.RefrigeratorState
            )
            "blinds" -> composite.encodeSerializableElement(descriptor, 3, State.BlindsState.serializer(),
                value.state as State.BlindsState
            )
            "door" -> composite.encodeSerializableElement(descriptor, 3, State.DoorState.serializer(),
                value.state as State.DoorState
            )
            "faucet" -> composite.encodeSerializableElement(descriptor, 3, State.FaucetState.serializer(),
                value.state as State.FaucetState
            )
            "vacuum" -> composite.encodeSerializableElement(descriptor, 3, State.VacuumState.serializer(),
                value.state as State.VacuumState
            )
            "speaker" -> composite.encodeSerializableElement(descriptor, 3, State.SpeakerState.serializer(),
                value.state as State.SpeakerState
            )
            else -> throw SerializationException("Unknown type: ${value.type.name}")
        }
        composite.encodeSerializableElement(descriptor, 4, Meta.serializer(), value.meta)
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