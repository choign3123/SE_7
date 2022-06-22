package com.example.myclosetapp

import android.app.Application
import android.content.SharedPreferences

// 자동 로그인 관련 클래스

class AppData: Application() {
    // SharedPreferences의 전역적인 사용을 위해 싱글톤 패턴으로 클래스 객체 생성
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