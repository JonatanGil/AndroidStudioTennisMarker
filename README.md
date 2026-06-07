# AndroidStudioTennisMarker 🎾

Aplicación Android para llevar el marcador de un partido de tenis en tiempo real. Desarrollada con **Jetpack Compose** y **Kotlin**.

## 🚀 Repositorio

[Ver proyecto en GitHub](https://github.com/JonatanGil/AndroidStudioTennisMarker)

---

## 🛠️ Tecnologías

- **Kotlin** — Lenguaje principal
- **Jetpack Compose** — UI declarativa
- **Android Studio** — Entorno de desarrollo
- **Material 3** — Componentes de diseño

---

## 📋 Funcionalidades

- Marcador completo para **2 jugadores** con puntuación oficial de tenis
- Conteo de **puntos** (0, 15, 30, 40), **games** y **sets**
- Sistema de **Deuce y Ventaja** — el jugador con ventaja se indica con subrayado en el marcador
- **Tie-break** automático al llegar a 6-6 en games (puntos de 1 en 1, gana quien llegue a 7 con 2 de diferencia)
- El partido termina al ganar **2 sets** — los botones se desactivan automáticamente al finalizar
- Botón de **Reset** para reiniciar el partido completo
- Pantalla bloqueada en **modo vertical**
- Colores diferenciados para cada jugador

---

## 🎮 Lógica de puntuación

| Situación | Comportamiento |
|-----------|----------------|
| 0 → 15 → 30 → 40 | Suma estándar de puntos |
| 40-40 | Se activa Deuce |
| Deuce + punto | El jugador marca Ventaja (subrayado) |
| Ventaja + punto | Gana el game |
| Ventaja rival + punto | Vuelve a Deuce |
| 6 games con ≥2 diferencia | Gana el set |
| 6-6 en games | Se activa Tie-break |
| Tie-break | Puntos de 1 en 1, gana con 7+ y 2 de diferencia |
| 2 sets ganados | Fin del partido |

---

## ⚙️ Instalación y uso

### Requisitos previos

- Android Studio Hedgehog o superior
- SDK de Android 26+
- Kotlin 1.9+

### Pasos

```bash
# Clonar el repositorio
git clone https://github.com/JonatanGil/AndroidStudioTennisMarker.git

# Abrir en Android Studio y ejecutar en emulador o dispositivo físico
```

---

## 📁 Estructura del proyecto

```
AndroidStudioTennisMarker/
├── app/
│   ├── src/
│   │   └── main/
│   │       ├── java/com/JJGGGG/clickcounter/
│   │       │   └── MainActivity.kt
│   │       └── res/
│   ├── build.gradle.kts
│   └── proguard-rules.pro
├── gradle/
├── build.gradle.kts
├── settings.gradle.kts
└── README.md
```

---

## 🤝 Contribuciones

Las contribuciones son bienvenidas. Si encuentras algún bug o tienes alguna mejora, abre un issue o un pull request.

---

## 📄 Licencia

Este proyecto está bajo la licencia MIT.

---

## 👤 Autor

**Jonathan Gil Galera**  
GitHub: [@JonatanGil](https://github.com/JonatanGil)
