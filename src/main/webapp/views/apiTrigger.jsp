<!DOCTYPE html>
<html lang="en">
<link rel="icon" href="${pageContext.request.contextPath}/images/transparent_logo.ico" type="image/x-icon">

<head>
    <!-- Google tag (gtag.js) -->
    <script async src="https://www.googletagmanager.com/gtag/js?id=G-2DKXMGP7B2"></script>
    <script>
        window.dataLayer = window.dataLayer || [];
        function gtag() { dataLayer.push(arguments); }
        gtag('js', new Date());

        gtag('config', 'G-2DKXMGP7B2');
    </script>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>API Call Trigger</title>
</head>

<body>
    <h1>Api call trigger page</h1>
            <script>
        function callApi() {
            // Replace 'YOUR_API_URL' with the actual URL of your Spring Boot API
            const apiUrl = 'https://kpl2023.online/kpl/registration/api/registration/apiTrigger';
            // const apiUrl = 'http://localhost:5000/kpl/registration/api/apiTrigger';
            // Make the API call using XMLHttpRequest (or 'fetch' for modern browsers)
            const xhr = new XMLHttpRequest();
            xhr.open('GET', apiUrl, true);
            xhr.onreadystatechange = function() {
                if (xhr.readyState === 4 && xhr.status === 200) {
                    console.log('API call successful!');
                }
            };
            xhr.send();
        }

        function triggerApiCall() {
            const now = new Date();
            const elevenFiftyNinePmLocal = new Date(now);
            elevenFiftyNinePmLocal.setHours(18, 30, 0, 0);

            let timeUntilElevenFiftyNinePmLocal = elevenFiftyNinePmLocal.getTime() - now.getTime();

            // If the current time is already past 11:59 PM local time, schedule for the next day
            if (timeUntilElevenFiftyNinePmLocal < 0) {
                timeUntilElevenFiftyNinePmLocal += 24 * 60 * 60 * 1000; // Add 24 hours in milliseconds
            }

            setTimeout(function() {
                callApi();
                // Schedule the next API call for the next day at 11:59 PM local time
                triggerApiCall();
            }, timeUntilElevenFiftyNinePmLocal);
        }

        // Call the triggerApiCall function when the page loads
        window.onload = function() {
            callApi(); // Trigger the API call on page load
            triggerApiCall(); // Schedule the daily API call
        };
    </script>
    </script>
</body>

</html>