# UsageDetector

Usage Detector Library
- To Detect your Application usage you can use this library and get the all information related to CPU usage and memory usage.

> Step 1. Add the Jitpack repository to your build file

```gradle
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
  ```
  
 > Step 2. Add the dependency
 
 ```gradle
 dependencies {
	        implementation 'com.github.Rahulkumarp:UsageDetector:Tag'
	}
  ```
  
  > In your Activity / Fragment Step 1
  
  ```
      lateinit var appMetricExporter :  AppMetricExporter
   ```  

> In your Activity  onCreate() Step 2

  ```
      appMetricExporter = AppMetricExporter(this)
        appMetricExporter.createPathForFile("TYPE your Folder NAME")
   ```  


> In your Activity To check Usage on click Step 3

  ```
     appMetricExporter.startCollect("MAINACTIVITY", "check")
   ```   

> In your Activity To finish Usage Detection on click Step 4

  ```
     override fun onDestroy() {
        super.onDestroy()
        appMetricExporter.stopCollect()

    }
   ```   

