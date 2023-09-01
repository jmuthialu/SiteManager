# SiteManager App

## Features

- Shows user data from two sources. One from local `json` file stored in the app and another from API call (remote). The two sources are shown in `Tabbed View` format.

- Provides navigation to users details.

- Offline capability added for remote users by storing the data in a local datbase.

- Provides the functionalty to scan the `Bluetooth` devices and lists the devices detected.

- BLE devices are updated every second with the latest RSSI value.

- Ability to start abd stop scan.


## Technology used

- Jetpack Compose for declarative UI components.
- Retrofit and GSON for networking and decoding the objects.
- Room Database for persistence layer.
- Compose Navigation and routing.
- Hilt for Dependency Injection
- Kotlin language.

