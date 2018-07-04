# 5_a_day_tracker_android

Here's how the user interact with the application.
1. User take a photo of their food.
2. The application detect the fruit/vegetables present in the photo.
3. User input the number of the fruit/vegetables detected in the photo.
4. User will be shown the number of fruits and vegetables they have intake by the end of the day through notification.

This application is based on the Android Tensorflow demo as shown in here: https://github.com/tensorflow/tensorflow/tree/master/tensorflow/examples/android


Technologies used: 
SQLite database to store user input
Tensorflow Inference Interface to carry out the food recognition (more can be found here: https://github.com/tensorflow/tensorflow/tree/master/tensorflow/examples/android)
Android Studio (which uses Gradle for build, Java for code) for the interface and business logic

Due to the size of the tensorflow model, it cannot be uploaded to github. 
The model can be found here separately:
https://github.com/martinwicke/tensorflow-tutorial/blob/master/tensorflow_inception_graph.pb


