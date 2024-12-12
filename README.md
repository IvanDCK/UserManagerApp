
# 👥 UserManager

## Contents
- [Introduction](#introduction)
- [Technologies](#technologies)
- [Installing](#installing)
- [Features](#features)

## Introduction
UserManager is an application made with modern Android techniques in Jetpack Compose Multiplatform targeting Android and iOS.
It's main purpose is to provide an user friendly interface and ready to use app for creating new users, updating their roles and deleting them.

## Technologies
- Written in [Kotlin](https://kotlinlang.org), using [Coroutines](https://github.com/Kotlin/kotlinx.coroutines) + [Flow](https://kotlinlang.org/api/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/) for asynchronous operations.
- Jetpack Libraries:
  - [Jetpack Compose Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/compose-multiplatform-getting-started.html): Android’s modern toolkit for declarative UI development.
  - Lifecycle: Observes Android lifecycles and manages UI states upon lifecycle changes.
  - ViewModel: Manages UI-related data and is lifecycle-aware, ensuring data survival through configuration changes.
  - Navigation: Facilitates screen navigation.
  - [Koin](https://insert-koin.io): For dependency injection.
  - [Ktor Client](https://ktor.io/docs/client-create-and-configure.html): For making network requests.
- Architecture
  - MVVM Architecture (View - Model - ViewModel)
- [Material-Components](https://github.com/material-components/material-components-android?tab=readme-ov-file): Material design components for building UI components.

## Installing

Clone this repository with:
```bash
git clone https://github.com/IvanDCK/UserManagerApp.git
```
In order use the app properly, a backend server is required to handle all operations.
You can find the backend server repository [here](https://github.com/AlejandroDCK/UserManager-Backend):

And clone it with:
```bash
git clone https://github.com/AlejandroDCK/UserManager-Backend.git
```
Start the backend server and start the app.

## Features
UserManager has the following features implemented in three different screens:

#### Login Screen
- Users can login with their email and password.
- Can choose to remember their credentials in order to log in automatically next time.
- If the user doesn't have an account, can navigate to the Sign up screen.

<img src="https://github.com/user-attachments/assets/70c7f31a-a9dc-4ed0-9fa9-7d7a759cac4f" width="300" height="700">
<img src="https://github.com/user-attachments/assets/52af17d1-0da9-4615-a1c7-4940f080f234" width="300" height="700">


#### Sign Up Screen
- New users can create an account with their names, surnames, email and password.
- If the user already have an account, can navigate to the Login screen.

<img src="https://github.com/user-attachments/assets/7ca443d8-61c9-4f47-b589-04457a9646ca" width="300" height="700">
<img src="https://github.com/user-attachments/assets/8f9faf8b-8552-45b0-af14-a0405d76779b" width="300" height="700">

#### List Screen
- Users can search by name or surname to find a specific user.
- Can filter the results by user roles and sort them by role importance.
- Users of specific roles can update other users roles or manage them.
- Logged user can update their own information.
- Users can logout from the app.

<img src="https://github.com/user-attachments/assets/2ef89c4d-7e8a-451a-aa54-3ea27df07409" width="300" height="700">
<img src="https://github.com/user-attachments/assets/5979595c-6dcb-4346-b647-2a7637e60ebe" width="300" height="700">



