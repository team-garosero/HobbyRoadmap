package com.garosero.android.hobbyroadmap.main.helper

import android.content.Intent
import android.os.Bundle
import com.garosero.android.hobbyroadmap.data.CourseItem
import com.garosero.android.hobbyroadmap.data.RoadmapItem

class ActionConfig {
    companion object {
        val ACTION_MYLIST_TO_ROADMAP = "ACTION_MYLIST_TO_ROADMAP"
        val ACTION_HOME_TO_ROADMAP = "ACTION_HOME_TO_ROADMAP"
        val ACTION_ROADMAP_TO_TIL_WRITE = "ACTION_ROADMAP_TO_TIL_WRITE"

        val CATEGORY_ID = "CATEGORY_ID"
        val ROADMAP_ID = "ROADMAP_ID"

        val ROADMAP_ITEM = "ROADMAP_ITEM"
        val COURSE_ITEM = "COURSE_ITEM"
    }

    fun getRoadmapItem(intent: Intent): RoadmapItem? {
        return if (intent.hasExtra(ROADMAP_ITEM))  intent.getParcelableExtra(ROADMAP_ITEM) else null
    }

    fun getRoadmapItem(bundle: Bundle?): RoadmapItem? {
        return bundle?.get(ROADMAP_ITEM) as RoadmapItem?
    }

    fun getCourseItem(intent: Intent): CourseItem? {
        return if (intent.hasExtra(ACTION_ROADMAP_TO_TIL_WRITE)) intent.getSerializableExtra(COURSE_ITEM) as CourseItem? else null
    }

    fun getCourseItem(bundle: Bundle?): CourseItem? {
        return bundle?.getSerializable(COURSE_ITEM) as CourseItem?
    }

}