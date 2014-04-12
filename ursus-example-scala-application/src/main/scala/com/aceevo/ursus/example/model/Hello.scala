package com.aceevo.ursus.example.model

import com.fasterxml.jackson.annotation.JsonProperty

case class Hello(@JsonProperty val name: String) {

}
