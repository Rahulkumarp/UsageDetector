# UsageDetector

Usage Detector Library

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
    AppMetricExporter(this).startCollect()
    
    ```
