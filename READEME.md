# Yoni Chat

## Chat with your favorite bot, anytime you want!
This project simulates a real bot server that answers predefined queries.

### Tech Stack

- Kotlin Coroutines & Flow
- Retrodit / OkOhttp
- Mock Web Server
- Dagger Hilt
- Android Navigation component
- Moshi

## Architecture in High-Level
The app's entry point is the MainActivity. This Activity contains the NavController that allows the user to navigate the application on the Navigation Graph. In addition, there is a main fragment 'ChatScreenFragment'. The project is aligned with the Android best practice architecture guideline.
** It is important to note that the server functions like a real server and runs locally on the device of the user under port 5433. **

https://developer.android.com/topic/architecture

### Package By Feature
Each feature’s architecture has three layers: a data layer, a domain layer and a UI layer.

![Flow](https://github.com/YoniGoli/MySportFeed/blob/main/assets/architectureDiagarm.png?raw=true)

The architecture follows a reactive programming model with the unidirectional data flow. With the data layer at the bottom, the key concepts are:

- Higher layers react to changes in lower layers.
- Events flow down.
- Data flows up.

