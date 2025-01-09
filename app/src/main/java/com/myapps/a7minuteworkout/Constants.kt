package com.myapps.a7minuteworkout

object Constants
{

    fun defaultExceriseList():ArrayList<ExerciseModel>
    {
        val exerciseList=ArrayList<ExerciseModel>()

        val abdominalcrunch=ExerciseModel(
            1,
            "Abdominal Crunch",
            R.drawable.ic_abdominal_crunch,
            false,
            false
        )
        exerciseList.add(abdominalcrunch)

        val highkneesrunninginplace=ExerciseModel(
            2,
            "High Knees Running in Place",
            R.drawable.ic_high_knees_running_in_place,
            false,
            false
        )
        exerciseList.add(highkneesrunninginplace)

        val jumpingjacks=ExerciseModel(
            3,
            "Jumping Jacks",
            R.drawable.ic_jumping_jacks,
            false,
            false
        )
        exerciseList.add(jumpingjacks)

        val lunge=ExerciseModel(
            4,
            "Lunge",
            R.drawable.ic_lunge,
            false,
            false
        )
        exerciseList.add(lunge)

        val plank=ExerciseModel(
            5,
            "Plank",
            R.drawable.ic_plank,
            false,
            false
        )
        exerciseList.add(plank)

        val pushup=ExerciseModel(
            6,
            "Push Up",
            R.drawable.ic_push_up,
            false,
            false
        )
        exerciseList.add(pushup)

        val pushupandrotation=ExerciseModel(
            7,
            "Push Up and Rotation",
            R.drawable.ic_push_up_and_rotation,
            false,
            false
        )
        exerciseList.add(pushupandrotation)

        val sideplank=ExerciseModel(
            8,
            "Side Plank",
            R.drawable.ic_side_plank,
            false,
            false,
        )
        exerciseList.add(sideplank)

        val squat=ExerciseModel(
            9,
            "Squat",
            R.drawable.ic_squat,
            false,
            false
        )
        exerciseList.add(squat)

        val stepupontochair=ExerciseModel(
            10,
            "Step up onto Chair",
            R.drawable.ic_step_up_onto_chair,
            false,
            false
        )
        exerciseList.add(stepupontochair)

        val tricepsdiponchair=ExerciseModel(
            11,
            "Triceps Dip on Chair",
            R.drawable.ic_triceps_dip_on_chair,
            false,
            false
        )
        exerciseList.add(tricepsdiponchair)

        val wallsit=ExerciseModel(
            12,
            "Wall Sit",
            R.drawable.ic_wall_sit,
            false,
            false
        )
        exerciseList.add(wallsit)

        return exerciseList
    }
}