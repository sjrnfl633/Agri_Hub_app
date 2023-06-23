```git clone으로 프로젝트 다운로드```
https://github.com/Dong-ho23/Agri_Hub
​

# 의존성 추가

​

``` 
    implementation 'androidx.core:core-ktx:1.7.0'
    implementation 'androidx.appcompat:appcompat:1.6.0'
    implementation 'com.google.android.material:material:1.8.0'
    implementation 'androidx.constraintlayout:constraintlayout:2.1.4'
    implementation 'com.google.firebase:firebase-database-ktx:20.0.4'
    implementation 'com.android.volley:volley:1.2.1'
    testImplementation 'junit:junit:4.13.2'
    androidTestImplementation 'androidx.test.ext:junit:1.1.5'
    androidTestImplementation 'androidx.test.espresso:espresso-core:3.5.1'
    implementation "org.jetbrains.kotlin:kotlin-stdlib:1.4.32"
    implementation "androidx.recyclerview:recyclerview:1.0.0"
    implementation 'androidx.preference:preference:1.1.1'
    implementation 'androidx.legacy:legacy-support-v4:1.0.0'
    testImplementation 'junit:junit:4.+'

    //gson && retrofit
    implementation 'com.squareup.retrofit2:converter-gson:2.9.0'
    implementation 'com.google.code.gson:gson:2.8.6'
    implementation 'com.squareup.retrofit2:retrofit:2.9.0'
​
    implementation group: 'com.kakao.sdk', name: 'usermgmt', version: '1.27.0'
    implementation "com.kakao.sdk:v2-all:2.12.1" // 전체 모듈 설치, 2.11.0 버전부터 지원
    implementation "com.kakao.sdk:v2-user:2.12.1" // 카카오 로그인
​
    //MPAandroidChart
    implementation 'com.github.PhilJay:MPAndroidChart:v3.1.0'
    implementation 'com.google.android.gms:play-services-location:21.0.1'
    implementation 'io.socket:socket.io-client:0.8.3'
    implementation 'com.google.android.exoplayer:exoplayer-core:2.16.0'
    implementation 'com.google.android.exoplayer:exoplayer-ui:2.16.0'
    implementation 'com.github.bumptech.glide:glide:4.12.0'
    annotationProcessor 'com.github.bumptech.glide:compiler:4.12.0'
    implementation 'org.java-websocket:Java-WebSocket:1.5.1'
```

# 3. 카카오 라이센스 키 발급후 

        testInstrumentationRunner "androidx.test.runner.AndroidJUnitRunner"
        buildConfigField "String", "KAKAO_NATIVE_APP_KEY", properties['kakao_native_app_key']
        resValue "string", "kakao_oauth_host", properties['kakao_oauth_host']
​
# 4. localproperties에   gradle에 지정된 변수명으로 키 삽입

# 5. gradle sync로 모듈 설치후 실행
