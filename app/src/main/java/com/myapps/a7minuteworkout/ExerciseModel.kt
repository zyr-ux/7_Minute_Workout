package com.myapps.a7minuteworkout

data class ExerciseModel(
    val id:Int,
    val name:String,
    val pic:Int,
    var isCompleted:Boolean,
    var isSelected:Boolean
)
