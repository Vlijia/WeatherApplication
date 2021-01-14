# WeatherApplication

> Aplicatie de vizualizare a datelor meteorologice principale ale unui Oras

## Interfata aplicatie
![](https://github.com/Vlijia/WeatherApplication/tree/main/src/main/resources/icons/weatherAppImg.JPG)

## Informatii generale

Aplicatia foloseste api-ul meteorologic pus la dispozitie de siteul https://openweathermap.org/ pentru
a obtine informatiile necesare.

Aplicatia permite vizualizarea urmatoarelor date meteorologice despre orasul ales:

- o scurta descriere a starii meteorologice
- presiunea atmosferica in hPa
- umiditatea din aer in procente
- viteza vantului in km/h
- temperatura in grade celsius
- o imagine reprezentativa (imaginile sunt luate de pe siteul https://openweathermap.org pe baza raspunsului json)

## Fisiere importante

- listele cu tari si orase sunt incarcate pe baza fisierului src/main/resources/input.txt
- aplicatia salveaza un istoric al datelor afisate in fisierul src/main/resources/displayHistory.txt
- diagramele UML se regasesc in folderul /src/main/resources/diagrams
