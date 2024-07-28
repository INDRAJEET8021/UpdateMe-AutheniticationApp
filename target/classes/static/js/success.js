   const openWeatherMapApiKey = '8a908775b7d154447531998246b957b9'; // Replace with your OpenWeatherMap API key

     function getWeatherData(latitude, longitude) {
         const url = `https://api.openweathermap.org/data/2.5/weather?lat=${latitude}&lon=${longitude}&units=metric&appid=${openWeatherMapApiKey}`;

         fetch(url)
             .then(response => response.json())
             .then(data => {
                 document.getElementById('weather-location').textContent = data.name;
                 document.getElementById('weather-temp').textContent = `${Math.round(data.main.temp)}°C`;
                 document.getElementById('weather-condition').textContent = data.weather[0].description.charAt(0).toUpperCase() + data.weather[0].description.slice(1);
                 document.getElementById('weather-wind').textContent = data.wind.speed;
                 document.getElementById('weather-humidity').textContent = data.main.humidity;
             })
             .catch(error => console.error('Error fetching weather data:', error));
     }

     function getLocation() {
         if (navigator.geolocation) {
             navigator.geolocation.getCurrentPosition(
                 position => {
                     const { latitude, longitude } = position.coords;
                     getWeatherData(latitude, longitude);
                 },
                 error => {
                     console.error('Error getting location:', error);
                     document.getElementById('weather-location').textContent = 'Unable to retrieve location';
                     document.getElementById('weather-temp').textContent = 'N/A';
                     document.getElementById('weather-condition').textContent = 'N/A';
                     document.getElementById('weather-wind').textContent = 'N/A';
                     document.getElementById('weather-humidity').textContent = 'N/A';
                 }
             );
         } else {
             console.error('Geolocation is not supported by this browser.');
             document.getElementById('weather-location').textContent = 'Geolocation not supported';
             document.getElementById('weather-temp').textContent = 'N/A';
             document.getElementById('weather-condition').textContent = 'N/A';
             document.getElementById('weather-wind').textContent = 'N/A';
             document.getElementById('weather-humidity').textContent = 'N/A';
         }
     }

     document.addEventListener('DOMContentLoaded', getLocation);

 function confirmLogout() {
     if (confirm("Are you sure you want to logout?")) {
         document.getElementById('logoutForm').submit();
     }
 }