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
  
  > In your Activity / Fragment / On Clicks
  
  ```
    AppMetricExporter(this,"SCREEN_NAME","BUTTON TASK").startCollect("YOUR_FOLDER_NAME_IN_DOCUMENT")
    
    ```
