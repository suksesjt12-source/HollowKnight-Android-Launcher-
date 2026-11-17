# Probe Vulkan Mali-G57 — Panduan Build

Folder ini mengaktifkan build APK diagnostik untuk mengukur Vulkan pada ponsel Android. APK ini **bukan game Hollow Knight, bukan emulator, dan bukan backend Mali**. Fungsinya hanya mengumpulkan data perangkat untuk menentukan langkah riset berikutnya.

## Cara menjalankan dari GitHub

1. Buka tab **Actions** di repositori ini.
2. Pilih workflow **Build Mali Vulkan diagnostic APK**.
3. Tekan **Run workflow** dan pilih branch `research/mali-vulkan-probe`.
4. Buka run yang muncul. Tunggu sampai statusnya **Success**.
5. Di bagian **Artifacts**, unduh `nx2a-mali-vulkan-probe`.
6. Ekstrak ZIP artifact, lalu pasang `nx2a-probe.apk` di perangkat Android.
7. Buka aplikasi **NX2A Probe**, biarkan pemeriksaan berjalan sampai tulisan `DONE`, lalu geser layar untuk membaca seluruh hasil.
8. Kirimkan teks atau tangkapan layar bagian `device`, `features`, `extensions`, `formats`, `zero-copy`, dan bagian `cpuprobe`.

Build menggunakan Android NDK r27c. Workflow mengambil kode probe resmi dari `shirai91/nx2android` pada commit yang dipatok di `SOURCE_COMMIT.txt`. Tidak perlu mengunggah file NSP/XCI, `prod.keys`, firmware, atau data pribadi.

## Arti hasil

- `textureCompressionBC` dan daftar format BC1–BC7 menunjukkan dukungan kompresi tekstur blok.
- Ekstensi Vulkan dan fitur 1.2/1.3 membantu mengidentifikasi kesenjangan API/fitur.
- `zero-copy` menguji jalur berbagi memori CPU/GPU yang relevan untuk rancangan backend.
- `cpuprobe` mengukur asumsi CPU, alamat memori, dan izin eksekusi yang dipakai runtime native.

Hasil probe saja tidak menjamin game Switch dapat berjalan. Kompatibilitas juga bergantung pada implementasi layanan sistem, translasi grafis, format resource, sinkronisasi, dan perilaku driver.

## Sumber dan batasan

Probe berasal dari proyek upstream: https://github.com/shirai91/nx2android. Ikuti lisensi dan atribusi upstream saat menggunakan atau mendistribusikan hasil build. Workflow hanya membangun utilitas diagnostik; ia tidak memodifikasi driver Android.
