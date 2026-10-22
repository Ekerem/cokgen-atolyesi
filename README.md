# Çokgen Atölyesi

5. sınıf öğrencileri için çokgen oluşturma uygulaması. Doğru parçası sayısını seç, köşeleri sürükle; iç açılar, eşit kenarlar, paralel kenarlar ve köşegenler anında görünür.

*made by us*

## Nereden açılır

- **Web:** `https://ekerem.github.io/cokgen-atolyesi/`
- **Android (APK):** `https://github.com/Ekerem/cokgen-atolyesi/releases/latest/download/cokgen-atolyesi.apk`

APK, internet varken web sürümünü açar; böylece web'e gelen her değişiklik uygulamaya da gelir. Açılan son sürüm cihazda saklanır (web/sw.js), internet yokken o açılır.

## Yeni sürüm nasıl çıkarılır

1. `web/index.html` dosyasında değişikliği yap.
2. `web/index.html` içindeki `APP_VERSION` ile `web/version.json` içindeki `version` değerini aynı yeni sayıya yükselt (ör. `1.2.0` → `1.3.0`).
3. `main` dalına gönder. GitHub Actions web sayfasını yayınlar, APK'yı derler ve **Releases** bölümüne yükler.
4. Uygulamadaki **Güncellemeleri denetle** düğmesi yeni sürümü bulur.

APK'nın kendisinde (Android kodu) bir değişiklik olursa `version.json` içindeki `apkMin` değerini de yükselt; eski APK'lar "Yeni APK'yı indir" bağlantısını gösterir.

## Klasörler

| Klasör | İçerik |
|---|---|
| `web/` | Uygulamanın tamamı (tek HTML dosyası) ve `version.json` |
| `android/` | Web sayfasını gösteren küçük Android uygulaması |
| `.github/workflows/` | Otomatik yayın ve APK derleme |
| `config.json` | GitHub kullanıcı adı ve depo adı |

## İlk kurulumda bir kez

Deponun **Settings → Pages** sayfasında **Source** olarak **GitHub Actions** seç.

## Not

APK, depodaki `android/app/cokgen.keystore` anahtarıyla imzalanır; bu sayede yeni APK eskisinin üstüne kurulur. Okul içi dağıtım için yeterlidir; Play Store'a yüklemek için ayrı, gizli bir anahtar kullanılmalıdır.
