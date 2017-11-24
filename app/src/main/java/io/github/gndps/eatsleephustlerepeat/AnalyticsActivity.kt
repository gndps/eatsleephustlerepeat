package io.github.gndps.eatsleephustlerepeat

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView

import java.util.Date

import butterknife.BindView
import butterknife.ButterKnife

class AnalyticsActivity : AppCompatActivity() {

    internal var eatTime: TextView? = null
    internal var sleepTime: TextView? = null
    internal var workTime: TextView? = null
    internal var hustleTime: TextView? = null
    internal var breakTime: TextView? = null
    internal var date: Date? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_analytics)
        initViews()
        date = RealmUtil.getDateWithoutTime()
        updateTheHours()
    }

    private fun initViews() {
        eatTime = findViewById(R.id.tv_eat_time_spent)
        sleepTime = findViewById(R.id.tv_sleep_time_spent)
        workTime = findViewById(R.id.tv_work_time_spent)
        hustleTime = findViewById(R.id.tv_hustle_time_spent)
        breakTime = findViewById(R.id.tv_break_time_spent)
    }

    private fun updateTheHours() {
        eatTime?.text = RealmUtil.getHoursSpent("eat", date)?:"N/A"
        sleepTime?.text = RealmUtil.getHoursSpent("sleep", date)?:"N/A"
        workTime?.text = RealmUtil.getHoursSpent("work", date)?:"N/A"
        hustleTime?.text = RealmUtil.getHoursSpent("hustle", date)?:"N/A"
        breakTime?.text = RealmUtil.getHoursSpent("break", date)?:"N/A"
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val selected = super.onOptionsItemSelected(item)
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return selected
    }
}
