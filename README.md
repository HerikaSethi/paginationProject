# paginationProject
This is a android project which shows a list of images along with the author names

Dependencies Used in gradle build(app level):
   //Paging
    implementation 'androidx.paging:paging-runtime-ktx:3.1.1'

    //Retrofit
    implementation 'com.squareup.retrofit2:converter-gson'
    implementation 'com.squareup.retrofit2:retrofit:2.9.0'
    implementation 'com.squareup.okhttp3:okhttp:4.9.0'
    implementation 'com.squareup.retrofit2:converter-gson:2.5.0'

    //material library
    implementation 'com.google.android.material:material:1.1.0'

    //glide library
    implementation 'com.github.bumptech.glide:glide:4.11.0'
    annotationProcessor 'com.github.bumptech.glide:compiler:4.11.0'


    implementation 'com.squareup.retrofit2:converter-scalars:2.6.4'
    implementation 'com.github.bumptech.glide:glide:3.8.0'

This project uses Retrofit to consume the Api consisting of a base url and end point v2/list , recycler view to display the list and converter to convert java objects to json representation.
