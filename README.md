# BA2023

Bachelorarbeit "Feedback Schreibaktivität mit einer Smartwatch".
- Thomas Good
- Valentin Kleisner 
## Inhaltsverzeichnis

- [Einleitung](#einleitung)
- [Features](#features)
- [Installation](#installation)
- [Usage](#usage)
- [Contributing](#contributing)
- [License](#license)

## Einleitung
Ziel der Bachelorarbeit ist eine Smartwatch App, welche Kinder mit ADHS bei Schreibarbeiten unterstützt. 
Die Applikation erkennt anhand des Beschleunigungssensors die aktuelle Position der Smartwatch und nutzt dies, um verschieden Zustände (Schreiben/ nicht Schreiben) darzustellen. Falls der Nutzer eine Minute abgelenkt ist, vibriert die Smartwatch und eine Schreiberinnerung erscheint.

Spezifikationen Smartwatch:
Samsung Galaxy Watch4 mit 40mm Durchmesser

Die Samsung Galaxy Watch basiert auf dem Android Betriebsystem WatchOS, weshalb die Programmierung in Android Studio erfolgte, mit Kotlin/Java als Programmiersprache.

## Features
1. Startbildschirm, der als Eintrittspunkt in die Applikation agiert. 
2. Zustandsbildschirme für Schreiben / Nachdenken und Abgelenkt gesteuert nach Auswertung des Beschleunigungssensor.
3. Konfigurationsbildschirm, um Schreibzeit anzupassen, die Schreibarbeit zu beenden / neustarten und die Statistik über vergangene Schreibarbeiten anzuschauen.
4. "Übung beendet"-bildschirm, der Sterne als Belohnungssystem nutzt und anzeigt, wie oft der Nutzer abgelenkt war.  

## Installation
1. [Android Studio](https://developer.android.com/studio) herunterladen und installieren.
2. BA2023 von Github in Android Studio importieren. "File" > "New" > "Project from Version Control"
3. Projekt in Emulator starten oder als physisches Device verbinden.

Anleitung für Emulator:
1. Neues Device hinzufügen: "DeviceManager" > "Virtual Device" > "Create Device". Für die Entwicklung wurde "WearOS Small Round API 30" verwendet.
2. Android Studio lädt nun den Emulator herunter. Dieser kann im "Device Manager" mit dem "Play" Icon gestartet werden. 
3. Mit dem grünen "Play"-Icon (Leiste oben mitte) kann nun die Applikation deployed werden.

Anleitung physisches Device zu verbinden.
Der Computer mit Android Studio und die Smartwatch müssen sich im gleichen Netzwerk befinden.
1. Die Smartwatch muss sich im "Developer" Modus befinden. Dies ist für die zwei Smartwatches, welche vom Institut bereitgestellt wurden bereits konfiguriert. Falls eine neue Smartwatch benutzt wird, kann Developer Modus gemäss diesem [Tutorial](https://developer.android.com/training/wearables/get-started/debugging) eingestellt werden. 
2. Smartwatch Einstellungen öffnen (Swipe von oben nach unten, klick auf das Zahnrad). 
3. Mit WLAN verbinden.
4. "Developer Options"(ganz unten in den Einstellungen) öffnen und "ADB-Debugging" und "WLAN Debugging" einstellen. (Falls nicht schon eingestellt).
5. "WLAN Debugging..." öffnen und auf "new Device" klicken. Nun erscheint ein Pairing Code.
6. In Android Studio "Device Manager" > "Physical" auswählen und Smartwatch über "Pair using Wifi" verbinden.*
7. Nun kann die die Applikation über den grünen "Play"-Knopf gestartet werden. Das App wird direkt geöffnet, falls die App ausversehen geschlossen wird, befindet sich das App bei den Smartwatch Apps(Swipe unten nach oben -> Symbol mit Android Logo).

* Die "Pair using Wifi" Option funktionierte bei der Entwicklung sehr schlecht. Falls dies nicht funktioniert, kann die Smartwatch über das Terminal verbunden werden.


## Usage

Explain how to use your project, including any command-line examples or code snippets. Provide clear instructions and explanations.

## Contributing

Explain how others can contribute to your project. Provide guidelines for submitting bug reports, feature requests, or pull requests. Mention any coding conventions or standards that contributors should follow.

## License

Specify the license under which your project is released. For example, "MIT License" or "Apache License 2.0". Include any license badges or acknowledgments.

## Acknowledgments

If there are any individuals or resources you want to acknowledge, mention them here.

## Contact

Provide your contact information or ways for users to reach out to you with questions or feedback.

