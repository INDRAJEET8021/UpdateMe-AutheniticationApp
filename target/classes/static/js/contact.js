
    document.getElementById('contactForm').addEventListener('submit', function (event) {
        event.preventDefault(); // Prevent traditional form submission

        var countdownElement = document.getElementById('countdown');
        var overlay = document.getElementById('overlay');
        var registerContainer = document.getElementById('registerContainer');
        var countdown = 3;

        // Show the overlay and apply blur effect to the form
        overlay.style.display = 'flex';
        registerContainer.classList.add('blur');

        // Update the countdown every second
        var interval = setInterval(function () {
            countdown--;
            countdownElement.textContent = countdown;
            if (countdown <= 0) {
                clearInterval(interval);
                window.location.href = '/success';
            }
        }, 1000);
    });
