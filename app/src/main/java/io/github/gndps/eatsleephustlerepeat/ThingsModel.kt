package io.github.gndps.eatsleephustlerepeat

import android.support.annotation.DrawableRes

/**
 * Created by Gundeep on 02/11/17.
 */

class ThingsModel(internal var thingType: ThingType? = null, @DrawableRes internal var selectedImage: Int = 0,
                  @DrawableRes internal var unselectedImage: Int = 0, internal var selected : Boolean = false) {

    companion object {
        fun create(thingType: ThingType?, lastSelectedThing: ThingType?) : ThingsModel? {
            var selected = false
            if(thingType == lastSelectedThing) {
                selected = true
            }
            when(thingType) {
                ThingType.EAT -> return ThingsModel(thingType, R.drawable.eat1, R.drawable.eat0, selected)
                ThingType.SLEEP -> return ThingsModel(thingType, R.drawable.sleep1, R.drawable.sleep0, selected)
                ThingType.WORK -> return ThingsModel(thingType, R.drawable.work1, R.drawable.work0, selected)
                ThingType.HUSTLE -> return ThingsModel(thingType, R.drawable.hustle1, R.drawable.hustle0, selected)
                ThingType.BREAK -> return ThingsModel(thingType, R.drawable.break1, R.drawable.break0, selected)
                else -> return null
            }
        }
    }
}
