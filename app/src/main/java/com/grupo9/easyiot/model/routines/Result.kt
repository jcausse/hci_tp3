package com.grupo9.easyiot.model.routines

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RoutineResult (

  @SerialName("id"      ) var id      : String,
  @SerialName("name"    ) var name    : String,
  @SerialName("actions" ) var actions : ArrayList<Actions> = arrayListOf()
  //@SerialName("meta"    ) var meta    : Meta?              = Meta()

)