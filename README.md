# SiteManager App

## Features

- Shows user data from two sources. One from local `json` file stored in the app and another from API call (remote). The two sources are shown in a `Tabbed View` format.

- Provides navigation to users details by passing user data.

- **Offline capability** for remote user data allows users to use the app even when there is no network.

- Provides the functionality to scan the `Bluetooth` devices and lists the devices detected.

- BLE devices are updated every second with the latest RSSI value.

- Ability to start and stop the scan.

## Demo


https://github.com/jmuthialu/SiteManager/assets/10758530/cb71f038-da1c-44d8-a273-12a40b5680c6

**Vid2**

https://github.com/jmuthialu/SiteManager/assets/10758530/2ba72159-aad8-47fc-8854-df35814a96ae




## Technologies used

- Jetpack Compose for declarative UI components.
- Retrofit and GSON for networking and decoding the objects.
- Room Database for persistence layer.
- Compose Navigation and routing.
- Hilt for Dependency Injection
- Kotlin language.
- Android Studio Flamingo | 2022.2.1 Patch 2

## Architecture

### Principles

Follows **SOLID** with emphasis on the below principles.
- Separation of Concerns. 
- Dependency Injection
- Interface oriented programming.

### Layered Architecture
From raw data to presentation, the data goes through 5 layers of separation which allows for separation of concerns and code reusability.

- **Data Layer:**  Data modeling and Database is housed in this layer.
- **Domain Layer:** This serves as an interface between data and UI. Repository, DAOs reside in this layer
- **View Model layer:** All screens (except for detail screen) has its own view models to drive the business logic and handle events. 
- **Presentation Layer:** Declarative UI that interacts with user.
- **Routing Layer:** One stop system for all things routing. `NavGraph` class serves the purpose of app-wide routing with the ability to scale for `Deeplinking` in future.


### User Module
Clear separation of the two sources of data (local and remote) right from data modelling, data handling and up until UI layer. This allows each data source and UI can scale or change independently without impacting other source.

**Entities**
- LocalUser
- RemoteUser

RemoteUser is persisted in a local ROOM database. Custom types such as `Address`, `Geo` and `Company` are persisted using `Converters`.

**Repository**

One repository is defined which has functionality to handle both sources of data and fetch the data as needed by UI layer.

**Presentation Layer**

Separate views and view models are created for local and remote user data. In general if a view is listing data it will use `LazyColumn` to make sure it shows only the data that is visible in the screen thereby making the list performant with reduced memory footprint. 

### Bluetooth Module

`BLEFacade` is the primary class that interfaces between Android Bluetooth and the viewModel. This class has functionality to start / stop scan and handle the BLE peripheral data. Since scanner runs every few milliseconds and Android does not provide a mechanism to return only unique peripherals, a custom solution is created to maintain list of unique peripherals. Also the solution takes care of updating the RSSI of the peripherals in the list.

`BLEListViewModel` fetches the data from `BLEFacade` every second. By design it does not update UI everytime `BLEFacade` updates its list which happens every few milliseconds. This makes the app performant and shows the refreshed data every second.

### Navigation Module

`AppFrameView` serves as the foundation for Bottom Navigation bar. Other FrameView such as `ListFrameView` and `DetailFrameView` provides the scaffolding needed to show the top bar and back button as needed in a reusbale way.    

`NavGraph` class provides the modular logic for app-wide navigation and `Sceen` provides the routing data.


