# Location-Tracker
A Kotlin Android app for real-time location tracking with user authentication. The app demonstrates MVVM architecture, Jetpack Compose UI, foreground location service, and Koin dependency injection.

# Features
  1. User Login: Email/password authentication.
  2. Location Tracking: Sends device location every 10 seconds to a backend API.
  3. Foreground Service: Location tracking continues even when the app is in background.
  4. Permissions & Settings: Checks for location permission and prompts user if GPS is disabled.
  5. MVVM Architecture: ViewModels manage UI state with StateFlow.
  6. Koin DI: All repositories, use cases, and services are injected via Koin.

# Structure
```
app/
 ├─ data/
 │   ├─ api/             # Data classes for API responses and API services
 │   ├─ repository/      # Repository implementations
 │   └─ service/         # Location Related Service
 ├─ domain/
 │   └─ usecase/         # UseCases like SendLocationUseCase
 ├─ presentation/
 │   ├─ login/           # LoginScreen + ViewModel
 │   ├─ main/            # MainScreen + ViewModel
 │   └─ navigation/      # NavRoutes + AppNavigation
 ├─ utils/               # utility fuctions
 │   
 └─ di/                  # Koin modules
```
# Dependencies
  1. Koin (Dependency Injection)
  2. Ktor Client (Networking)
  3. Compose Navigation
  4. Play Services (location)
