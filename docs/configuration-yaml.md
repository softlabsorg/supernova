# Configuration: `configuration.yaml`

---

## ⚙️ Purpose

The `configuration.yaml` file is the main entry point for defining environment-specific settings used in Supernova.  
This file is automatically loaded and mapped to the `GlobalConfiguration` class.

You can define configuration for:

- Web Browser Automation
- Android App Testing
- iOS App Testing
- Database Connections

---

## 📁 File Location

The file should be placed under:

```
src/test/resources/configuration.yaml
```

---

## 🧩 YAML Structure & Examples

### 🌐 Web Configuration

```yaml
web:
  browser: chrome
  showBrowser: true
  implicitWait: 15
  pageLoadTimeout: 45
  scriptTimeout: 30
  deleteAllCookies: true
  maximize: true
  windowSize: 1920x1080
  clearBrowserCache: true
  disableNotifications: true
  disablePopupBlocking: true
  disableGpu: false
```

> 🔹 All fields are optional. If a field is missing, the default value from the `Web` class will be used.

---

### 📱 Android Configuration

```yaml
android:
  appName: app-google-debug.apk
  appPackage: com.google.mobile
  appActivity: .MainActivity
  firebase:
    url: https://google.firebaseio.com
    email: user@google.com
    password: firebasepass123
    apkPath: /path/to/firebase/apk-debug.apk
```

---

### 🍏 iOS Configuration

```yaml
ios:
  appName: app-google-ios.zip
  bundleId: com.google.ios
  firebase:
    url: https://google.firebaseio.com
    email: user@google.com
    password: firebasepass123
    apkPath: /path/to/firebase/apk-debug.apk
```

---

### 🛢️ Database Connections

```yaml
databases:
  - name: test_db
    url: jdbc:postgresql://localhost:5432/testdb
    username: test_user
    password: secret123
  - name: logs_db
    url: jdbc:mysql://localhost:3306/logs
    username: logger
    password: logpass
```

---

## ✅ Notes

- All fields are optional. If you omit a section or field, the default value from the Java class (`@Data`) will be used.
- You can combine multiple sections in one file as needed.

---

✅ With this setup, you can fully control how Supernova behaves per environment — from browser settings to database
access.