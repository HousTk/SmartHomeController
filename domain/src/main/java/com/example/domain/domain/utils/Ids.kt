package com.example.domain.domain.utils

const val TYPE_OTHER = -1
const val TYPE_LEDCONTROLLER = 0
const val TYPE_LIGHTCONTROLLER = 1
const val TYPE_WATERCONTROLLER = 2
const val TYPE_WATERHEATER = 3
const val TYPE_SWITCH_LIGHTCONTROLLER = 4
const val TYPE_SWITCH_MOVE_LIGHTCONTROLLER = 5
const val TYPE_MOVE_LIGHTCONTROLLER = 6


// в TYPES_ARRAY заносятся типы устройств так, как будут отсортированы при показе, по убывающей
val TYPES_ARRAY = arrayOf(
    TYPE_LIGHTCONTROLLER,
    TYPE_LEDCONTROLLER,
    TYPE_WATERCONTROLLER
)

const val POWERBUTTON_ACTION_NONE = 0
const val POWERBUTTON_ACTION_CHANGESTATE = 1
const val POWERBUTTON_ACTION_TURNOFF = 2
const val POWERBUTTON_ACTION_TURNON = 3
