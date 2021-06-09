# CirculeProgressTwoText
A subclass of {@link android.view.View} class for creating a custom circular progressBar with the possibility of two texts inside. Such as the value of progression ...


# How to use CirculeProgressTwoText ?

**1.** Add the JitPack repository to your build file**
Add it in your root ***build.gradle*** at the end of repositories:

   allprojects {
   		repositories {
   			...
   			maven { url 'https://jitpack.io' }
   		}
   	}

**2.** Add the dependency

   dependencies {
   	        implementation 'com.github.ADOUJEAN:CircleProgressTwoText:YOUR_VERSION_TAG'
   	}

**3.** Add this view in your activity layout  ***activity_main.xml***


    <ci.jjk.circleprogresstwotext.CircleProgressTwoText
        android:id="@+id/custom_progressBar"
        android:layout_width="200dp"
        android:layout_height="200dp"
        app:progress="35"
        app:max="100"
        app:min="0"
        app:progressText="Value"
        app:progressTextUnity="Unit value"
        app:progressbarColor="#FF3700B3"
        app:progressBarThickness="5dp"
       />


**4.** Now you can dance with the CirculeProgressTwoText as you want ! 'lol'
    ** Here is an example of use.
     Place this in your principle activity, in the block of an action. In my example I used a button

          val maxValue=100
          val minValue=1
          val progresVal=((Math.random() * (maxValue -minValue))-minValue).toFloat()
          customProgressBar.setProgressWithAnimationAndMax(progresVal,maxValue.toFloat())



That's it.


![ScreenShot](/photo_2021-06-09_11-58-12.jpg)

Thank you!