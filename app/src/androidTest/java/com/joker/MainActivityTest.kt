package com.joker


import android.content.Intent
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.IdlingResource
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import com.joker.utils.EspressoIdlingResource
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class MainActivityTest {
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java, false, false)
    private var mIdlingResource: IdlingResource? = null

    @Before
    fun setup() {
        val intent = Intent()
        mActivityTestRule.launchActivity(intent)
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
    }

    @Test
    fun testTabClick() {
        mActivityTestRule.launchActivity(null)
        CoroutineScope(Dispatchers.Main).launch {
            delay(1000)
            testTab("Joke", 0)
            delay(1000)
            testTab("Favorite", 1)
            delay(1000)
            val appCompatImageButton = onView(
                allOf(
                    withId(R.id.share_in_item),
                    childAtPosition(
                        childAtPosition(
                            withClassName(`is`("androidx.cardview.widget.CardView")),
                            0
                        ),
                        1
                    ),
                    isDisplayed()
                )
            )
            appCompatImageButton.perform(click())
        }
    }

    private fun testTab(name: String, position: Int) {
        val tabView = onView(
            allOf(
                withContentDescription(name),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.tabs),
                        0
                    ),
                    position
                ),
                isDisplayed()
            )
        )
        tabView.perform(click())
    }



    @Test
    fun buttonsTest() {
        mActivityTestRule.launchActivity(null)

        CoroutineScope(Dispatchers.Main).launch {
            delay(1000)
            val favoriteButton = onView(
                allOf(
                    withId(R.id.favorite),
                    childAtPosition(
                        allOf(
                            withId(R.id.constraintLayout),
                            withParent(withId(R.id.view_pager))
                        ),
                        2
                    ),
                    isDisplayed()
                )
            )
            favoriteButton.perform(click())
            delay(1000)
            val addButton = onView(
                allOf(
                    withId(R.id.add),
                    childAtPosition(
                        allOf(
                            withId(R.id.constraintLayout),
                            withParent(withId(R.id.view_pager))
                        ),
                        1
                    ),
                    isDisplayed()
                )
            )
            addButton.perform(click())
        }


    }


    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }

    @After
    fun unregisterIdlingResource() {
        if (mIdlingResource != null) {
            IdlingRegistry.getInstance().unregister()
        }
    }
}
