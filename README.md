# D&D Character Builder

An Android application for creating and managing Dungeons & Dragons characters.

The project is currently focused on building a solid offline-first character management experience while providing a foundation that can later be extended with online functionality.

## Overview

The goal of the application is to make it easy for players to create, view, and manage their D&D characters from an Android device.

The initial version focuses on the core character-management flow:

- Create characters
- View character details
- Edit existing characters
- Delete characters
- Track core character and combat statistics
- Manage character proficiencies and equipment

The application is being built incrementally, with the initial implementation using local storage before introducing a remote backend.

## Tech Stack

Current technologies include:

- Kotlin
- Jetpack Compose
- Material 3
- Room
- Kotlin Coroutines
- Flow
- ViewModel
- Navigation Compose

## Architecture

The project follows Android's recommended layered architecture, separating responsibilities between the UI and data layers.

The current structure includes:

- Composables for UI
- ViewModels for screen state and user interactions
- Repositories for accessing application data
- Room DAOs for local persistence
- Domain and data models where appropriate

Dependency injection is currently handled manually.

## Current Development Focus

The immediate goal is to complete the basic character-management experience.

## Planned UX Improvements

### Character Dashboard

The current home screen displays a list of characters.

Since many users are likely to have only one active character, the planned UX is to avoid requiring them to navigate through an unnecessary character list.

When the user has a single character, the home screen should instead act as a **character dashboard**, giving immediate access to important character information.

Possible dashboard content includes:

- Character name, class, race, and level
- Hit points
- Armor Class
- Ability scores
- Proficiencies
- Equipment
- Quick access to the full character sheet

Users with multiple characters should still have an easy way to switch between and manage their characters.

## Data and Backend

### Current Approach

Character data is currently stored locally using Room.

The application is intentionally offline-first during the initial development phase. This keeps the focus on the application model, UX, and core functionality without introducing backend complexity too early.

### Future Approach

A remote backend is planned for a later stage.

Potential backend functionality includes:

- Remote persistence
- User accounts
- Synchronization between devices
- Offline access
- Conflict handling where necessary

The repository layer should keep the UI and ViewModels independent of whether data originates from local storage, a remote service, or a combination of both.

For data that should remain available offline, Room can continue to act as the local data source and synchronize with the backend.

## Dependency Injection

Dependency injection is currently handled manually.

As the project grows, the plan is to migrate to Hilt to reduce boilerplate and simplify dependency management.

## Longer-Term Ideas

Possible future functionality includes:

- Character inventory management
- Spell management
- Conditions and status effects
- Dice rolling
- Rest management
- Character progression and leveling
- Cloud synchronization
- Sharing character sheets
- Player and Dungeon Master modes
- Campaign management