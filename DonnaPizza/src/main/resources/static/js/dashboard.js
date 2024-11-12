async function fetchUserData() {
    const token = localStorage.getItem("token");

    if (!token) {
        console.log("Token no encontrado");
        return;
    }

    try {
        const response = await fetch("/dashboard", {
            method: "GET",
            headers: {
                "Authorization": `Bearer ${token}`
            }
        });

        if (response.ok) {
            const data = await response.json();
            console.log("Datos del usuario:", data);
            updateDashboard(data);
        } else {
            console.log("Respuesta del servidor:", response.status);
            alert("No autorizado o error al obtener datos.");
        }
    } catch (error) {
        console.error("Error al obtener los datos:", error);
    }
}

function updateDashboard(userData) {
    document.getElementById("user-name").textContent = userData.username;
    document.getElementById("user-nombre").textContent = userData.nombre;
    document.getElementById("user-apellido").textContent = userData.apellido;
    document.getElementById("user-email").textContent = userData.username;
    document.getElementById("user-telefono").textContent = userData.telefono;
    document.getElementById("user-direccion").textContent = userData.direccion;
}

// Llamada para obtener datos cuando la página se carga
window.onload = fetchUserData;
