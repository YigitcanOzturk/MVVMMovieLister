package com.yigitcan.mvvmmovielister

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class VisibilityTest {

    private val selectedMovieId: Int = 12
    private var viewIndex1: Int = 0
    private var viewIndex2: Int = 0
    private var appContext: Context = InstrumentationRegistry.getInstrumentation().targetContext
    private var textView:TextView = TextView(appContext)
    private var imageView:ImageView = ImageView(appContext)
    private var cardView:CardView = CardView(appContext)

    @Before
    fun setViewValues(){
        textView.visibility = View.VISIBLE
        imageView.visibility = View.VISIBLE
        cardView.visibility = View.VISIBLE
    }

    @Before
    fun control1 (){
        viewIndex1= if(selectedMovieId==0) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }

    @Before
    fun control2 (){
        viewIndex2= if(selectedMovieId==0) {
            View.GONE
        } else {
            View.VISIBLE
        }
    }

    @Test
    fun setVisibility(){
        textView.visibility = viewIndex1
        imageView.visibility = viewIndex2
        cardView.visibility = viewIndex2
        Assert.assertEquals(View.VISIBLE,imageView.visibility and cardView.visibility)
        Assert.assertEquals(View.GONE,textView.visibility)
    }
}