# Sign Me Up

Sign me up is a simple application to demonstrate a standard user sign up  
form with optional and required fields and a confirmation/welcome screen.

The application uses Mode-View-ViewModel (MVVM) architecture. View can have  
one to many relationship to view model.

Essentially, the app is divided into two use cases
- Sign up form
- Sign up confirmation

## Sign up form
Sign up form uses a fragment to display the UI and View binding to interact
with the UI. Sign Up fragment also have the following responsibility:
- GetOrCreate the view model instance
- Initialize views
- Add required callbacks/listeners
- Observe required states/events from view model.

Signup form also extends from Avatar Fragment which has the responsibility
of handling Avatar request including the intents, permission requests.  
This provides the ability to the user to capture the profile image from  
currently camera or provider such as Gallery in the future.

## Sign up confirmation
Once user signs up and is successful, the confirmation screen displays
all the entered information to show a populated profile. Currently, the
profile information is received by the view as a serialized object from
the source but this should be retrieved using data store.

## Orientation
Each of the views have a dedicated view for each of the orientation mode.
Since, view models are lifecycle aware and are preserved, we leverage the
capability to maintain the data across the device orientation.

## Events and States
The view model besides leveraging live data to communicate to view, uses
events and states to pass the any event or state to the view via live data.

Currently supported Events
- Success -> A sign up success event
- Error - > A sign up error event

We could potentially add more UI events to show progress/spinners or take
a specific action.

## Tools and Technologies
- Kotlin
- Android lifecycle ext
- Live Data
- Android Material
- Android Core Ktx

## Abstractness - Instability Index
| Abs   | Ins   |
|:------|:------|
| 0.5   | 0.5   |
| 0     | 0.667 |
| 0.25  | 0.5   |
| 0     | 0.333 |
| 0.333 | 0.667 |
| 0     | 1     |
| 0.2   | 0.75  |
| 0     | 0.2   |
| 0     | 1     |
