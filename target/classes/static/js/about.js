
    document.addEventListener("DOMContentLoaded", () => {
        const aboutTextElement = document.getElementById("about-text");
        const closeIcon = document.getElementById("close-icon");
        const aboutText = `My self INDRAJEET I am a fullstack web developer,
        I have created this project using Springboot technology using JAVA for backend  management and
        MySQL database for database managements and storage.Thymleaf for frontend controller.
        Javascript for scripting frontend. In this amazing project I am able to manage the
         (Authentication,Authorization,and API's fetching and responsivness).
         It was great experience while developing this project
           {THANK YOU!}.`;

        let index = 0;

        function typeText() {
            if (index < aboutText.length) {
                aboutTextElement.innerHTML += aboutText.charAt(index);
                index++;
                setTimeout(typeText, 50); // Adjust the typing speed here
            }
        }

        typeText();

        closeIcon.addEventListener("click", () => {
            window.location.href = "/success"; // Redirect to success page
        });
    });
