package com.example.myclosetapp

import android.app.Application
import android.content.SharedPreferences

// 이 부분 다시
// SharedPreferences의 전역적인 사용을 위해 새로운 클래스 싱글톤으로 생성
class AppData: Application() {
    companion object {
        lateinit var prefs: SharedPreferences
        lateinit var editor: SharedPreferences.Editor
    }

    override fun onCreate() {
        prefs = getSharedPreferences("autoLogin", MODE_PRIVATE)
        editor = prefs.edit()
        editor.clear()
        editor.apply()
        super.onCreate()
    }
}