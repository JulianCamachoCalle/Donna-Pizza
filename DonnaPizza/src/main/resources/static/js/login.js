const registerButton = document.getElementById('register');
const loginButton = document.getElementById('login');
const contenedor = document.getElementById('contenedor');

// Eventos para cambiar entre registro e inicio de sesión
registerButton.addEventListener('click', () => {
    contenedor.classList.add('active');
});

loginButton.addEventListener('click', () => {
    contenedor.classList.remove('active');
});

// Función para manejar el registro de usuario
async function guardarUsuario() {
    const data = {
        nombre: document.getElementById("nombre").value,
        apellido: document.getElementById("apellido").value,
        username: document.getElementById("username").value,
        telefono: document.getElementById("telefono").value,
        direccion: document.getElementById("direccion").value,
        password: document.getElementById("password").value,
    };

    try {
        const response = await fetch("/auth/register", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(data),
        });

        if (response.ok) {
            Swal.fire({
                icon: 'success',
                title: 'Usuario registrado exitosamente',
                showConfirmButton: false,
                timer: 2500
            }).then(() => {
                window.location.href = "/inicioSesion";
            });
        } else {
            const errorText = await response.text();
            let errorData = {};
            try {
                errorData = JSON.parse(errorText); // Intentar parsear la respuesta del servidor
            } catch (e) {
                console.error("Error al parsear el JSON:", e);
                // Si no es JSON, mostrar un mensaje genérico
                errorData = { general: "Hubo un problema con la respuesta del servidor." };
            }
            console.log("Error recibido del servidor:", errorData); // Verifica qué error se recibe
            showErrors(errorData);
        }
    } catch (error) {
        console.error("Error:", error);
        Swal.fire({
            icon: 'error',
            title: 'Hubo un problema con el registro',
            text: 'Intenta nuevamente más tarde',
            confirmButtonText: 'Cerrar'
        });
    }
}

// Función para mostrar errores con SweetAlert
function showErrors(errors) {
    console.log("Errores a mostrar:", errors); // Verifica qué errores se están pasando

    // Crear un arreglo de errores
    const errorMessages = [];

    // Si existe el error general, lo agregamos
    if (errors.general) {
        errorMessages.push(errors.general);
    }

    // Si el servidor devuelve un mensaje de error en el campo 'token'
    if (errors.token) {
        errorMessages.push(errors.token);
    }

    // Mostrar los errores específicos si existen
    if (errors.nombre) {
        errorMessages.push(errors.nombre);
    }
    if (errors.apellido) {
        errorMessages.push(errors.apellido);
    }
    if (errors.username) {
        errorMessages.push(errors.username);
    }
    if (errors.telefono) {
        errorMessages.push(errors.telefono);
    }
    if (errors.direccion) {
        errorMessages.push(errors.direccion);
    }
    if (errors.password) {
        errorMessages.push(errors.password);
    }

    // Si existen errores, mostramos el SweetAlert
    if (errorMessages.length > 0) {
        Swal.fire({
            icon: 'error',
            title: 'Errores de registro',
            text: errorMessages.join('\n'), // Usamos join para separar los errores
            confirmButtonText: 'Cerrar'
        });
    } else {
        // Si no hay errores, mostramos un mensaje genérico
        Swal.fire({
            icon: 'warning',
            title: 'No se detectaron errores específicos',
            text: 'Por favor revisa los datos ingresados',
            confirmButtonText: 'Cerrar'
        });
    }
}

async function iniciarSesion() {
    const data = {
        username: document.getElementById("emaillogin").value,
        password: document.getElementById("contraseñalogin").value,
    };

    try {
        const response = await fetch("/auth/login", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(data),
        });

        if (response.ok) {
            const responseData = await response.json();

            // Almacenar el token en localStorage o cookies
            localStorage.setItem("token", responseData.token);

            // Mensaje de éxito y redirección
            Swal.fire({
                icon: 'success',
                title: 'Inicio de sesión exitoso',
                showConfirmButton: false,
                timer: 1500
            }).then(() => {
                // Redirige al usuario al dashboard
                window.location.href = "/dashboard";
            });
        } else {
            const errorText = await response.text();
            let errorData = {};
            try {
                errorData = JSON.parse(errorText);
            } catch (e) {
                console.error("Error al parsear el JSON:", e);
                errorData = { general: "Hubo un problema con la respuesta del servidor." };
            }
            mostrarErroresLogin(errorData);
        }
    } catch (error) {
        console.error("Error:", error);
        Swal.fire({
            icon: 'error',
            title: 'Hubo un problema con el inicio de sesión',
            text: 'Intenta nuevamente más tarde',
            confirmButtonText: 'Cerrar'
        });
    }
}

// Función para mostrar errores específicos de login usando SweetAlert
function mostrarErroresLogin(errors) {
    let errorMessage = '';
    if (errors.general) {
        errorMessage += errors.general + '\n';
    }
    if (errors.username) {
        errorMessage += errors.username + '\n';
    }
    if (errors.password) {
        errorMessage += errors.password + '\n';
    }

    Swal.fire({
        icon: 'error',
        title: 'Errores de inicio de sesión',
        text: errorMessage,
        confirmButtonText: 'Cerrar'
    });
}






